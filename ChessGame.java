import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* Purpose: This class is what starts the game.  First, it will conncect to the server, and then a waiting screen 
will appear. Once the server sends back that another client has been found, the waiting screen will close
and the chess board will appear with all of the pieces on it. 

This class will wait till the user clicks on a button and then go to the ActionListener nested class and that class
will handle the actionEvent. 

This class also has a thread which always keep on going as long the program is up. This thread will keep calling
a method from the Client's class to see who's turn it is. 

(add names here)
@author Jonathon Brandt 
@see TileButtons
@see Pieces
@see CheckKing
@see Movements
@see SimpleMovements
@see StartingPieces
@see WaitingScreen
@see Client
@see Server
*/

public class ChessGame extends JFrame implements ActionListener
{
   private boolean myTurn = false;
   private boolean check = false;
   
   private String username = null;

   private int currentTeam = -1; //0 for white, 1 for black; this shows which team the user is on
   private int currentTeamTurn = -1; 
   private int opponentTeam = -1; // This shows what team the opponent is on

   private TileButton[][] allTiles = new TileButton[8][8];
   private JPanel boardPanel = new JPanel();
   private Pieces pieces = new Pieces();
   private StartingPieces startingPieces = new StartingPieces();
   
   /*when it is the player's turn, their clickId will start at 0. When that player clicks on a piece he/she
   wants to move, we will assign the row and the column of that piece here. Once we get that, the clickId
   will turn to 1, which means the next tile the user clicks will be the tile he/she wants to move the 
   piece to. Once the user does that, the clickId will revert back to 0 and then the program will create
   create a movement string, and then we will use a method from the Client class to send that movement string 
   to the server; once the server get that movement string, it will sent the movement string to the opposing 
   player, and that opposing player's Client class will read the movement string and update the piece on 
   their board*/
   private int pressedPieceRow;
   private int pressedPieceCol;
   private String pressedPieceTeam;
   private String pressedPiece;
   private int clickId = 0; //0 = click a piece; 1 = click a tile you want to move the piece to
   
   private String movementString = "";//This is the message that will be sent to the server to let it know what the player did
   
   //the objects we will be using in the class
   private Client client = null;
   private Movements movement = new Movements();
   private CheckKing checkKing = new CheckKing();
   
   //Chat values
   public JTextField chatField=new JTextField(10);
   public JTextArea chatAreaBox=new JTextArea(5,10);
   public JButton chatSend=new JButton("Send");
   public JScrollPane chatArea=new JScrollPane(chatAreaBox);
   
   //Turn values
   public JLabel turnLabel=new JLabel("Enemy's turn!",SwingConstants.CENTER);
   
   public ChessGame()
   {
      String ipAddress = JOptionPane.showInputDialog("IP address: ");
   
      /*asks the user for the username; if the user closes the window without typing anything, they
      will be assigned to the "Anonymous" username*/
      username = JOptionPane.showInputDialog("Username: ");
      if(username == null || username.equals(""))
         username = "Anonymous";
   
      //starts the client, connects with the server
      client = new Client(allTiles, username);
      client.start();
      
      boolean foundOpponent = false;
      //until an opponent has been fouund, the GUI of the waiting screen will stay on the screen
      WaitingScreen screen = new WaitingScreen();
         
      while(!client.opponentFound )//loops over and over again till an opponent has been found
      {
         try
         {
            Thread.sleep(250);//rechecks every quarter of a second
         }
         catch(InterruptedException ex1)
         {
            System.out.println(ex1.getMessage());
         }
      }
      
      screen.dispose();//once an opponent has been found, the waiting screen will close
      
      /*Determine whether the player is white or black.
      This code must be placed here because I need to know if the player is playing as white or black when setting
      up the board.*/
      
      if(client.getTeam().equals("white"))
      {
         currentTeam = 0;
         opponentTeam = 1;
      }
      else if(client.getTeam().equals("black"))
      {
         currentTeam = 1;
         opponentTeam = 0;
      }
   
      //sets the size and the location of the board
      setSize(500, 630);
      setLocationRelativeTo(null);
      setLayout(new BorderLayout());
      
      boardPanel.setLayout(new GridLayout(0, 8));
      int colorId = 1;//this is simply used to set the color of each tile (light tile, dark tile, light tile, etc...)
      
      //SETS ALL OF THE BOARD TILES
      String colLetters = "ABCDEFGH";
      String rowNumbers = "12345678";
      for(int row = 0; row < allTiles.length; row++)
      {
         if(colorId == 0)
            colorId = 1;
         else
            colorId = 0; 
            
         for(int col = 0; col < allTiles[0].length; col++)
         {
            allTiles[row][col] = new TileButton(colorId);//sets the color of the tile
            boardPanel.add(allTiles[row][col]);
            allTiles[row][col].addActionListener(new ButtonListener(row, col));
            allTiles[row][col].setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            //sets the tile positions for all of the tiles for the white team. (Black team's board is flipped)
            if(currentTeam == 0)
            {
               int _row = allTiles.length - row;//first row starts at 8 and it downs down by 1 after every row
               allTiles[row][col].setPosition(colLetters.charAt(col) + Integer.toString(_row)); 
            }
            else if(currentTeam == 1)//sets the tile position for the black team
            {
               int _row = row;
               _row++;//first row starts at 1 (index starts at 0 which is why I added 1)
               int colIndex = allTiles.length - col;
               colIndex--;
               allTiles[row][col].setPosition(colLetters.charAt(colIndex) + Integer.toString(_row));
            }
            
            //switches the colorId from 0 to 1 or from 1 to 0 so that the board will be: light, dark, light, dark etc...
            if(colorId == 0)
               colorId = 1;
            else
               colorId = 0;
         }
      }
      add(boardPanel,BorderLayout.CENTER);
      
      //Chat
      JPanel chatBox=new JPanel();
      chatBox.setLayout(new BorderLayout());
      chatAreaBox.setEditable(false);
      chatSend.addActionListener(this);
      chatBox.add(chatArea,BorderLayout.CENTER);
      chatBox.add(chatField,BorderLayout.SOUTH);
      chatBox.add(chatSend,BorderLayout.EAST);
      add(chatBox,BorderLayout.SOUTH);
      
      //Player turn screen
      JPanel playerTurn=new JPanel();
      playerTurn.add(turnLabel);
      add(playerTurn,BorderLayout.NORTH);
      
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      //sets up the board depending on whether they are playing as white or black
      startingPieces.setUpPieces(currentTeam, allTiles);
      enable(currentTeam);//white goes first so the white pieces will be enabled for player 1 right away
      
      //This nested thread is always checking who's turn it is
      Thread thread = 
         new Thread(){
            public void run()
            {
               if(currentTeam == currentTeamTurn)
               {
                  setMyTurn(true);
                  turnLabel.setText("Your turn!");
               }
               while(true)//thread never ends
               {
                  String messageFromServer=client.getMessage();
                  if(messageFromServer.length() > 0)
                  {
                     chatAreaBox.append(messageFromServer + "\n");
                  }
                  
                  if(!getMyTurn())//keeps checking only if it's the opponent's turn
                  {
                     if(client.wonByCheckmate())
                     {
                        turnLabel.setText(username + " has won by checkmate");
                     }
                     
                     //calls the Client method to find out whose turn it is
                     String team = client.getCurrentTeam();
                     if(client.getCurrentTeam().equals("white"))
                        currentTeamTurn = 0;
                     else if(client.getCurrentTeam().equals("black"))
                        currentTeamTurn = 1;
                     
                     if(currentTeam == currentTeamTurn )
                     {
                        enable(currentTeamTurn);
                        myTurn = true;
                        turnLabel.setText(username + "'s turn");
                        //For debugging purposes. This checks whether there is a threat at the beginning of the turn
                        if(checkKing.kingIsVulnerable(allTiles, team) == true)
                        {
                           turnLabel.setText("There is a check");
                           System.out.println("There is a threat detected");
                        }
                        else
                           System.out.println("There is no threat detected");
                           
                        //Checks if there is a checkmate.
                        if(checkKing.isCheckmate(allTiles, currentTeam))
                        {
                           //if there is a checkmate, the player loses. 
                           //sends a message to the opponent that they have won by checkmate
                           client.sendCheckmate();
                           enable(-1);
                           turnLabel.setText("Checkmate! Enemy won!");
                           JOptionPane.showMessageDialog(null, "Your opponent has won by checkmate", "Checkmate", JOptionPane.OK_CANCEL_OPTION);
                        }
                        else
                           System.out.print("There is no checkmate this turn");//for debugging purposes
                     }
                     else
                     {
                        enable(-1);//if it is not the player's turn, the entire board is disabled
                        turnLabel.setText("Enemy's turn!");
                     }
                  }
                     
                  try
                  {
                     Thread.sleep(100);//checks every 1/10 of a second
                  }
                  catch(InterruptedException ex1)
                  {
                     System.out.println(ex1.getMessage());
                  }
               }
            }};
      thread.start();
   }

   public void actionPerformed(ActionEvent ae)
   {
      String message= chatField.getText();
      if(message.length() > 1)
      {
         client.sendMessage("$" + username + ": " + message + "\n");
         chatAreaBox.append(username + ": " + message + "\n");
         chatField.setText("");
      }
   }
   
   public static void main(String[] args)
   {
      new ChessGame();
   }
   
   /**
   * Purpose: handles all of the movements.
   */
   
   public class ButtonListener implements ActionListener
   {
      //row and column of the button that was clicked
      private int row;
      private int col;
      
      //current team and piece of the button that was clicked
      private String piece;
      private String team;
      
      public ButtonListener(int _row, int _col)
      {
         row = _row;
         col = _col;
      }
   
      public void actionPerformed(ActionEvent ae)
      {
         piece = allTiles[row][col].getPiece();
         team = allTiles[row][col].getTeam();
         
         if(clickId == 0)//This is when a player presses the piece he/she wants to move
         {
            pressedPieceRow = row;
            pressedPieceCol = col;
            pressedPiece = allTiles[row][col].getPiece();
            pressedPieceTeam = allTiles[row][col].getTeam();
            
            //enables all of the button the current piece can move to
            boolean[][] allPossibleMoves = movement.buttonPressed(allTiles, row, col, 0);
            //disables all of the illegal moves (moves that threatens the king)
            allPossibleMoves = checkKing.removeIllegalMoves(allTiles, allPossibleMoves, currentTeamTurn, row, col);
            allPossibleMoves[row][col] = false;
            enable(allPossibleMoves);
            
            clickId = 1;
            
            /*changes the background color of the selected tile slightly so it is easier for the player to 
            keep track of which piece he/she is moving*/
            if(allTiles[row][col].getBackgroundColor() == 0)
            {
               allTiles[row][col].setSelectedLightBackground(); 
            }
            else if(allTiles[row][col].getBackgroundColor() == 1)
            {
               allTiles[row][col].setSelectedDarkBackground();
            }
         }
         else if(clickId == 1)//This is when the player presses the tile he/she wants to move the piece to
         {
            //if the player reclicked the same tile, then it will still be their turn
            if(pressedPieceRow != row || pressedPieceCol != col)
            {
               pieces.removePiece(allTiles[pressedPieceRow][pressedPieceCol]);//remove picture of piece from last tile
               pieces.setAPiece(pressedPiece, pressedPieceTeam, allTiles[row][col]);//adds picture of piece to clicked button
               allTiles[pressedPieceRow][pressedPieceCol].removePieceInfo();//removes piece info from last tile
               allTiles[row][col].setPieceInfo(pressedPiece, pressedPieceTeam);//adds piece info to next/clicked tile
            
               /*if the piece that moved was a pawn and if that pawn has reached the end of the board, the
               player will be able to switch that pawn with some other piece*/
               if(pressedPiece.equals("pawn"))
               {
                  if(row == 0)
                  {
                     String newPiece = movement.choosePiece();
                     pieces.setAPiece(newPiece, pressedPieceTeam, allTiles[row][col]);
                     allTiles[row][col].setPieceInfo(newPiece, pressedPieceTeam);
                     pressedPiece = newPiece;
                  }
               }
            
               //string of team, piece, last position and next position
               movementString += pressedPieceTeam + " ";//team
               movementString += pressedPiece + " ";//piece
               movementString += allTiles[pressedPieceRow][pressedPieceCol].getPosition() + " ";//last position
               movementString += allTiles[row][col].getPosition();//next position
               
               allTiles[pressedPieceRow][pressedPieceCol].hasMoved();//prevents king from castling if it has moved before
                        
               /*if the king moved right/left by 2, it means the player is castling the king, which means the rook has to 
               move too*/
               if(pressedPiece.equals("king"))
               {
                  if((pressedPieceCol - col) == 2)//checks if user is castling to the left side of the board
                  {
                     pieces.removePiece(allTiles[row][0]);//removes Rook from left side of board
                     int _col = col;
                     _col++;
                     if(pressedPieceTeam.equals("white"))
                        pieces.setWhiteRook(allTiles[row][_col]);
                     else if(pressedPieceTeam.equals("black"))
                        pieces.setBlackRook(allTiles[row][_col]);
                     allTiles[row][0].removePieceInfo();//removes piece info from last tile
                     allTiles[row][_col].setPieceInfo("rook", pressedPieceTeam);//adds piece info to next/clicked tile
                  }
                  else if((col - pressedPieceCol) == 2)//checks if user is castling to the right side of the board
                  {
                     pieces.removePiece(allTiles[row][7]);//removes Rook from left side of board
                     int _col = col;
                     _col--;
                     if(pressedPieceTeam.equals("white"))
                        pieces.setWhiteRook(allTiles[row][_col]);
                     else if(pressedPieceTeam.equals("black"))
                        pieces.setBlackRook(allTiles[row][_col]);
                     allTiles[row][7].removePieceInfo();//removes piece info from last tile
                     allTiles[row][_col].setPieceInfo("rook", pressedPieceTeam);//adds piece info to next/clicked tile
                  }
               }
            
               client.sendMovementString(movementString);//sends movementString to server
               System.out.println("Sent: " + movementString);
               movementString = "";
               
               enable(currentTeamTurn);
               setMyTurn(false);
               check = false;
            }
            else//if the player reclicks on the same button, it will still be their turn
            {
               enable(currentTeam);
            }
            
            //returns the tile's background color back to normal
            if(allTiles[pressedPieceRow][pressedPieceCol].getBackgroundColor() == 0)//if the background tile is light...
            {
               allTiles[pressedPieceRow][pressedPieceCol].setLightBackground(); 
            }
            else if(allTiles[pressedPieceRow][pressedPieceCol].getBackgroundColor() == 1)
            {
               allTiles[pressedPieceRow][pressedPieceCol].setDarkBackground();
            }
            
            clickId = 0;
            
            pressedPiece = null;
            pressedPieceTeam = null;
            pressedPieceRow = -1;
            pressedPieceCol = -1;
         }
      }
   }
   
   /*
   * Disables some or all of the buttons on the chess board
   * @param _id Tells the method what kind of buttons it should disable and/or enable
   */
   public void enable(int _id)// -1 to disable all, 0 to disable non-black owned buttons, 1 to disable non-white owned buttons
   {
      for(int row = 0; row < allTiles.length; row++)
      {
         for(int col = 0; col < allTiles[0].length; col++)
         {
            if(_id == -1)
               allTiles[row][col].setEnabled(false);
            else if(_id == 0)
            {
               if(!allTiles[row][col].getTeam().equals("white"))//disables all of the non-white buttons
               {
                  allTiles[row][col].setEnabled(false);
                  //System.out.println("Row: " + row + ", Col: " + col + " is disabled");
               }
               else
               {
                  allTiles[row][col].setEnabled(true);
                  //System.out.println("Row: " + row + ", Col: " + col + " is enabled");
               }
            }
            else if(_id == 1)
            {
               if(!allTiles[row][col].getTeam().equals("black"))//disables all of the non-black buttons
               {
                  allTiles[row][col].setEnabled(false);
                  //System.out.println("Row: " + row + ", Col: " + col + " is disabled");
               }
               else
               {
                  allTiles[row][col].setEnabled(true);
                  //System.out.println("Row: " + row + ", Col: " + col + " is enabled");
               }
            }
         }
      }
   }
   
   /*
   * Will disable some of the buttons on the board
   * @param _allTiles The 2d array that tells which buttons should be enabled and which should be disabled (true = disabled, false = enabled)
   */
   public void enable(boolean[][] _allTiles)
   {
      for(int row = 0; row < _allTiles.length; row++)
      {
         for(int col = 0; col < _allTiles[0].length; col++)
         {
            if(_allTiles[row][col] == true)
               allTiles[row][col].setEnabled(false);
            else
               allTiles[row][col].setEnabled(true);
         }
      }
   }
   
   /**
   * Sets the myTurn boolean. This method is neccessary because the nested threaded class cannot change
   the value of myTurn inside the class, it has to use this method to change the value.
   * @param _myTurn True if it is the user's turn, false if it is not
   */
   
   public void setMyTurn(boolean _myTurn)
   {
      myTurn = _myTurn;
   }
   
   /**
   * Gets whether it is the user's turn or not
   * @return whether it is the user's turn or not
   */
   
   public boolean getMyTurn()
   {
      return myTurn;
   }
}