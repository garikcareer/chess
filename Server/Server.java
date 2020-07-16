import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
* Purpose: to handle of all of the chess matches. This will start a server socket, and then hold a list of all
of the sockets that are connected with the server. And it will assign each player to play a game against another
player. This server is responsible for passing a message from one client to it's opposing client

@see TileButtons
@see Pieces
@see CheckKing
@see Movements
@see SimpleMovements
@see StartingPieces
@see WaitingScreen
@see Client
@see ChessGame
*/

public class Server
{
   ArrayList<Integer> gameIdList = new ArrayList<Integer>();
   ArrayList<Socket> socketList = new ArrayList<Socket>();
   private int gameId = 0;
   private int numOfPlayers = 0;
   
   
   public JMenuBar jmb=new JMenuBar();
   public JMenu file=new JMenu("File");
   public JMenu settings=new JMenu("Settings");
   public JMenuItem serverInformation=new JMenuItem("Server Infomation");
   public JMenuItem startServer=new JMenuItem("Start Server");
   public JMenuItem stopServer=new JMenuItem("Stop Server");
   public JMenuItem exit=new JMenuItem("Exit");
   
   
   //Chat log
   public JTextArea jta=new JTextArea(10,10); 
   public JLabel chatLogLabel=new JLabel("Chat log: ");
   public JScrollPane chatLogText = new JScrollPane (jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
   
   /**
   * Sets up the serversocket and waits for a connection
   */
   
   public Server()
   {
      ServerSocket server = null;
      Socket socket = null;
      
      JFrame frame=new JFrame();
      frame.setTitle("Chess Server");
      frame.setSize(500,500);
      frame.setLocationRelativeTo(null);
      frame.setLayout(new BorderLayout());
      
      //Add chat log
      JPanel chatLog=new JPanel();
      chatLog.setLayout(new BorderLayout());
      jta.setEditable(false);
      chatLog.add(chatLogLabel,BorderLayout.NORTH);
      chatLog.add(chatLogText,BorderLayout.CENTER);
      frame.add(chatLog);  
      
      frame.setVisible(true);
      frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
      
      try
      {
         server = new ServerSocket(41287);
         System.out.println("Server set up");
         
         
         //this loop will last as long the program is up. This loop is always waiting for a new connection
         while(true)
         {
            int playerNum = 0;//1 = white, 2 = black
            socket = server.accept();
            ServerThread thread = null;
            //if a game has 0 players assigned to that game, a new Id will be assinged to that game and it will 
            //be noted that the socket is the 1st player to claim that ID which mean that player will go first (play as white)
            if(numOfPlayers == 0) 
            {
               gameIdList.add(gameId);
               numOfPlayers++;
               playerNum = 1;
               thread = new ServerThread(socket, gameId, playerNum);
            }
            else if(numOfPlayers == 1)
            {
               gameIdList.add(gameId);
               numOfPlayers = 0;
               playerNum = 2;
               thread = new ServerThread(socket, gameId, playerNum);
               
               newGameId();
            }
            socketList.add(socket);
            thread.start();//assigns a thread to each client
         }
      }
      catch(IOException ex1)
      {
         System.out.println(ex1.getMessage());
      }
   
   
   }

   public static void main(String[] args)
   {
      new Server();
   }
   
   /**
   * Changes the game id. This happens when 2 clients has been assigned to a gameId
   */
   public void newGameId()
   {
      gameId++;
   }
   
   
   /**
   * Each of this thread handles a single client/socket
   */
   public class ServerThread extends Thread
   {
      Socket socket;
      int currentGameId;
      int playerNum; //1 = white, 2 = black
      String username;
      int opponentIndex = -1;//the opponent's index on the arraylist
      
      /**
      * Setss the socket, gameId and playerNum of the thread
      */
      
      public ServerThread(Socket _socket, int _currentGameId, int _playerNum)
      {
         socket = _socket;
         currentGameId = _currentGameId;
         playerNum = _playerNum;
      }
      
      /**
      * Will run as long the client is connected to the server
      */
      
      public void run()
      {
         PrintWriter pw = null;
         BufferedReader br = null;
         
         try
         {
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            username = br.readLine();//sets the username of that player
            jta.append(username + " connected to server.");
            
            System.out.println("ID: " + currentGameId);
            System.out.println("Player number: " + playerNum + "\n");
            
            int num = 0;
            while(num != 2)//game doesn't start till there are 2 players with the same gameId
            {
               num = 0;
               for(Integer id : gameIdList)
               {
                  if(id == currentGameId)
                  {
                     num++;
                  }
               }
               sleep(500);//waits every half a second to check the number of sockets with the same gameId
            }
            
            //gets the opponent's index on the arraylist
            num = 0;
            for(int i = 0; i < gameIdList.size(); i++)
            {
               if(gameIdList.get(i) == currentGameId)
               {
                  num++;
                  if(playerNum != num)//we don't want the index of the current socket, we want the index of the opponent's socket
                  {
                     opponentIndex = i;
                     break;
                  }
               }
            }
            
            //game starts
            pw.println(playerNum);//informs the clients whether they are playing as white or black
            pw.flush();
            pw = new PrintWriter(new OutputStreamWriter(socketList.get(opponentIndex).getOutputStream()));//writes to the opponent's socket
            
            
            /*board sets up, game starts. White will make their move and send a message to
            the server; the message will contain a string which explains what the player did on their turn
            EX: pawn e2 e4. The server will take that message and send it to the opponent */
            
            boolean gameOver = false;
            String message = null;
            while(!gameOver)
            {
               if((message = br.readLine()) != null)//receives a message of the movement from the client
               {
                  pw.println(message);
                  pw.flush();
                  System.out.println("message: " + message);
               }
               else//this is what happens if there is not a valid message
               {
                  System.out.println("GAME IS OVER");
                  gameOver = true;
               }
            }
            
            pw.println("disconnected");//this is a special keyword, the client will reconize this string and end the game
            pw.flush();
            
            /*If a client closed the program while searching for an opponent, this will increase the gameID by 1 so 
            nobody will be player 2 of that game*/
            if(opponentIndex == -1)
            {
               newGameId();
            }
         }
         catch(IOException ex1)
         {
            System.out.println(ex1.getMessage());
         }
         catch(InterruptedException ex2)
         {
            System.out.println(ex2.getMessage());
         }
      }
   }
}

//need to add code for disconnected player and subtract number of players in server.
// jta.append(username + " disconnected from server.");