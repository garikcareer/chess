import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Client extends Thread
{
   boolean opponentFound = false;//game won't start till this turns true
   String team = null;
   String currentTeam = null;
   String username = null;

   Socket socket = null;
   boolean connected = false;
   Pieces pieces = new Pieces();
   boolean isWinner=false;
   BufferedReader br = null;
   PrintWriter pw = null;
   String ongoingMessage = null;
   
   TileButton[][] allTiles = null;

   /**
   * This will start the client socket and connect with a server
   * @param _allTiles The main method will pass the reference to all 64 TileButtons to this class; that way
   we will be able to modify the board from this class
   */
   public Client(TileButton[][] _allTiles, String _username)
   {
      allTiles = _allTiles;
      username = _username;
   }  
   
   public void run()
   {
      try
      {
         socket = new Socket("localhost", 41287);
         connected = true;
         
         pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
         pw.println(username);
         pw.flush();
         System.out.println("Sent " + username);
         
         System.out.println("Waiting for response...");
         br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         int teamNum = Integer.parseInt(br.readLine());
         if(teamNum == 1)
            team = "white";
         else if(teamNum == 2)
            team = "black";
         System.out.println("Got a response... Team: " + team);
         opponentFound = true;
         currentTeam = "white";
         
         ClientListener clientThread = new ClientListener();
         clientThread.start();
      }
      catch(IOException ex1)
      {
         System.out.println(ex1.getMessage());
      }
   }
   
   public boolean opponentFound()
   {
      return opponentFound;
   }
   
   public String getTeam()
   {
      return team;
   }
   
   public void sendMovementString(String _movementString)
   {
      pw.println(_movementString);
      pw.flush();
      
      switchCurrentTeam();
   }
   
   /**
   * If a player sends this method to the opposing player, it means the opposing player has won by checkmate
   */
   
   public void sendCheckmate()
   {
      pw.println("Checkmate");
      pw.flush();
      
      switchCurrentTeam();
   }
   
   public boolean wonByCheckmate()
   {
      return isWinner;
   } 
   
   public void updateBoard(String _movementString)
   {
      System.out.println("Received: " + _movementString);
      String[] splitString = _movementString.split(" ");
      String opponentTeam = splitString[0];
      String opponentPiece = splitString[1];
      String lastPosition = splitString[2];
      String nextPosition = splitString[3];
      
      /*if the movement string has a 5 strings in it, it means it has a '+' at the end which represents a check*/
      int lastPositionRow = 0;
      int lastPositionCol = 0;
      int nextPositionRow = 0;
      int nextPositionCol = 0;
      
      for(int row = 0; row < allTiles.length; row++)
      {
         for(int col = 0; col < allTiles[0].length; col++)
         {
            if(allTiles[row][col].getPosition().equals(lastPosition))
            {
               lastPositionRow = row;
               lastPositionCol = col;
            }
            else if(allTiles[row][col].getPosition().equals(nextPosition))
            {
               nextPositionRow = row;
               nextPositionCol = col;
            }
         }
      }
      
      pieces.removePiece(allTiles[lastPositionRow][lastPositionCol]);//remove picture of piece from last tile
      pieces.setAPiece(opponentPiece, opponentTeam, allTiles[nextPositionRow][nextPositionCol]);//adds picture of piece to clicked button
      allTiles[lastPositionRow][lastPositionCol].removePieceInfo();//removes piece info from last tile
      allTiles[nextPositionRow][nextPositionCol].setPieceInfo(opponentPiece, opponentTeam);//adds piece info to next/clicked tile
      
         
      /*Checks if the opponent's king moved left/right by 2 tiles... if they did this, it means they're castling their
      king. This updates their rook piece*/
      if(opponentPiece.equals("king"))
      {
         if((lastPositionCol - nextPositionCol) == 2)//checks if user is castling to the left side of the board
         {
            pieces.removePiece(allTiles[0][0]);//removes Rook from left side of board (of the opponme
            int col = nextPositionCol;
            col++;
            if(opponentTeam.equals("white"))
               pieces.setWhiteRook(allTiles[0][col]);
            else if(opponentTeam.equals("black"))
               pieces.setBlackRook(allTiles[0][col]);
            allTiles[0][0].removePieceInfo();//removes piece info from last tile
            allTiles[0][col].setPieceInfo("rook", opponentTeam);//adds piece info to next/clicked tile
         }
         else if((nextPositionCol - lastPositionCol) == 2)//checks if user is castling to the right side of the board
         {
            pieces.removePiece(allTiles[0][7]);//removes Rook from left side of board
            int col = nextPositionCol;
            col--;
            if(opponentTeam.equals("white"))
               pieces.setWhiteRook(allTiles[0][col]);
            else if(opponentTeam.equals("black"))
               pieces.setBlackRook(allTiles[0][col]);
            allTiles[0][7].removePieceInfo();//removes piece info from last tile
            allTiles[0][col].setPieceInfo("rook", opponentTeam);//adds piece info to next/clicked tile
         }
      }
      
      switchCurrentTeam();
   }
   
   public void setCurrentTeam(String _currentTeam)
   {
      currentTeam = _currentTeam;
   }
   
   public String getCurrentTeam()
   {
      return currentTeam;
   }
   
   public void switchCurrentTeam()
   {
      if(currentTeam.equals("white"))
         currentTeam = "black";
      else if(currentTeam.equals("black"))
         currentTeam = "white";
   }
   
   public void sendMessage(String _message)
   {
      pw.println(_message);
      pw.flush();
   }
   
   public String getMessage()
   {
      if(ongoingMessage != null)
      {  
         String clientMessage=ongoingMessage;
         ongoingMessage=null;
         return clientMessage;
      }
      return "";
   }
   
   public class ClientListener extends Thread//always listens for a message from the server
   {
      public void run()
      {
         try
         {
            boolean connected = true;
            String message = null;
            while(connected)
            {
               if((message = br.readLine()) != null)//gets a message which contains the opponent's movement string
               {
                  /*If the message contains the string: "disconnected", it means the opponent has left the game.
                  If the message contains the character, '$', at the beginning of the string, it means the opponent
                  has sent a message through the chatbox. Any other message is assumed to be a movement string (a 
                  string that tells what the opponent did on their turn)*/
                  if(message.length() > 0)
                  {
                     if(message.equals("disconnected"))
                     {
                        JOptionPane.showMessageDialog(null, "Your opponent has left the match", 
                           "Disconnected", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                     }
                     else if(message.charAt(0) == '$') 
                     {
                     ///ongoingMessage = message.substring(1);
                        ongoingMessage = message.substring(1);
                        System.out.println("Got chat message: " + ongoingMessage);
                     }
                     else if(message.equals("Checkmate"))
                     {
                        JOptionPane.showMessageDialog(null, "You have won by checkmate", "Checkmate", JOptionPane.OK_CANCEL_OPTION);
                        isWinner = true;
                     }
                     else
                        updateBoard(message);
                  }
               }
            }
         }
         catch(IOException ex1)
         {
            System.out.println(ex1.getMessage());
         }
      }
   }
}