import java.util.*;
import javax.swing.*;

/**
* Purpose: This method holds all of the rules of movement. This will enable all of the buttons each piece can move to and
disable all of the buttons the piece cannot move to
*/
public class Movements
{
   SimpleMovements simpleMovements = new SimpleMovements();

   public boolean[][] pawnPressed(TileButton[][] _allTiles, int _row, int _col, String _team)
   {
      String otherTeam = getOtherTeam(_team);
         
      boolean[][] disabledTiles = new boolean[8][8];
      
      disabledTiles = simpleMovements.disableAll(disabledTiles);
      
      int row = _row;
      int col = _col;
      
      row--;
      if(row >= 0)
      {
         if(_allTiles[row][col].getTeam().equals("none"))
         {
            disabledTiles[row][col] = false;
         }
            
         col = _col;
         if(col > 0)
         {
            col--;
            if(_allTiles[row][col].getTeam().equals(otherTeam))
            {
               disabledTiles[row][col] = false;
            }
         }
         
         col = _col;
         col++;
         if(col < disabledTiles.length)
         {
            if(_allTiles[row][col].getTeam().equals(otherTeam))
            {
               disabledTiles[row][col] = false;
            }
         }
      }
      
      col = _col;
      row--;
      if(_row == 6)
      {
         if(_allTiles[row][col].getTeam().equals("none"))
         {
            disabledTiles[row][col] = false;
         }
      }
      
      disabledTiles[_row][_col] = false;
      
      return disabledTiles;
   }
   
   /*
   * Determine which tiles the king can move to
   */
   
   public boolean[][] kingPressed(TileButton[][] _allTiles, int _row, int _col, String _team)
   {
      
      String otherTeam = getOtherTeam(_team);
   
      boolean[][] disabledTiles = new boolean[8][8];
      
      disabledTiles = simpleMovements.disableAll(disabledTiles);
      disabledTiles = simpleMovements.surroundingTiles(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
       
      int row = _row;
      int col = _col;
      
      //if the player is able to castle his king, then the tiles/buttons will be enabled
      if(!_allTiles[_row][_col].getHasMoved())
      {
         if(!_allTiles[_row][0].getHasMoved())//checks if the rook at the left edge of the board has moved before
         {
            boolean ableToCastle = true;
            for(int i = 1; i < _col; i++)//checks whether the tiles between the king and the rook are empty or not
            {
               if(!_allTiles[_row][i].getTeam().equals("none"))
               {
                  ableToCastle = false;
                  break;
               }
            }
            
            if(ableToCastle)
            {
               if(_col == 3)
               {
                  disabledTiles[_row][1] = false;
               }
               else if(_col == 4)
               {
                  disabledTiles[_row][2] = false;
               }
            }
         }
         if(!_allTiles[_row][7].getHasMoved())//checks the rook on the right side of the board
         {
            boolean ableToCastle = true;
            col = _col;
            col++;
            for(; col < 7; col++)//checks whether the tiles between the king and the rook are empty or not
            {
               if(!_allTiles[_row][col].getTeam().equals("none"))
               {
                  ableToCastle = false;
                  break;
               }
            }
            
            if(ableToCastle)
            {
               if(_col == 3)
               {
                  disabledTiles[_row][5] = false;
               }
               else if(_col == 4)
               {
                  disabledTiles[_row][6] = false;
               }
            }
            
         }
      }
      
      disabledTiles[_row][_col] = false;
      
      return disabledTiles;
   }
   
   public boolean[][] rookPressed(TileButton[][] _allTiles, int _row, int _col, String _team)
   {
      
      String otherTeam = getOtherTeam(_team);
         
      boolean[][] disabledTiles = new boolean[8][8];
      
      disabledTiles = simpleMovements.disableAll(disabledTiles);
      
      disabledTiles = simpleMovements.leftAndRight(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      disabledTiles = simpleMovements.upAndDown(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      
      disabledTiles[_row][_col] = false;
      
      return disabledTiles;
   }
   
   public boolean[][] bishopPressed(TileButton[][] _allTiles, int _row, int _col, String _team)
   {
      
      String otherTeam = getOtherTeam(_team);
         
      boolean[][] disabledTiles = new boolean[8][8];
      
      disabledTiles = simpleMovements.disableAll(disabledTiles);
      
      disabledTiles = simpleMovements.bottomRight(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      disabledTiles = simpleMovements.topLeft(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      disabledTiles = simpleMovements.bottomLeft(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      disabledTiles = simpleMovements.topRight(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      
      disabledTiles[_row][_col] = false;
      
      return disabledTiles;
   }
   
   public boolean[][] queenPressed(TileButton[][] _allTiles, int _row, int _col, String _team)
   {
      
      String otherTeam = getOtherTeam(_team);
         
      boolean[][] disabledTiles = new boolean[8][8];
      
      disabledTiles = simpleMovements.disableAll(disabledTiles);
      
      disabledTiles = simpleMovements.leftAndRight(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      disabledTiles = simpleMovements.upAndDown(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      disabledTiles = simpleMovements.bottomRight(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      disabledTiles = simpleMovements.topLeft(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      disabledTiles = simpleMovements.bottomLeft(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      disabledTiles = simpleMovements.topRight(_team, otherTeam, disabledTiles, _allTiles, _row, _col);
      
      disabledTiles[_row][_col] = false;
      
      return disabledTiles;
   }
   
   public boolean[][] knightPressed(TileButton[][] _allTiles, int _row, int _col, String _team)
   {
      
      String otherTeam = getOtherTeam(_team);
         
      boolean[][] disabledTiles = new boolean[8][8];
      
      disabledTiles = simpleMovements.disableAll(disabledTiles);
      
      //has 8 positions we need to check
      //up 2, left 1
      int row = _row;
      int col = _col;
      row = row - 2;
      col--;
      if(row >= 0 && col >= 0)
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            disabledTiles[row][col] = false;
         }
      }
      
      //up 2, right 1
      col = _col;
      col++;
      if(row >= 0 && col < disabledTiles.length)
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            disabledTiles[row][col] = false;
         }
      }
         
      //up 1, right 2
      row = _row;
      col = _col;
      row--;
      col = col + 2;
      if(row >= 0 && col < disabledTiles.length)
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            disabledTiles[row][col] = false;
         }
      }
         
      //down 1, right 2
      row = _row;
      row++;
      if(row < disabledTiles.length && col < disabledTiles.length)
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            disabledTiles[row][col] = false;
         }
      }
         
      //down 2, right 1
      row = _row;
      col = _col;
      row = row + 2;
      col++;
      if(row < disabledTiles.length && col < disabledTiles.length)
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            disabledTiles[row][col] = false;
         }
      }
         
      //down 2, left 1
      col = _col;
      col--;
      if(row < disabledTiles.length && col >= 0)
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            disabledTiles[row][col] = false;
         }
      }
         
      //down 1, left 2
      row = _row;
      col = _col;
      row++;
      col = col - 2;
      if(row < disabledTiles.length && col >= 0)
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            disabledTiles[row][col] = false;
         }
      }
      
      //up 1, left 2
      row = _row;
      row--;
      if(row >= 0 && col >= 0)
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            disabledTiles[row][col] = false;
         }
      }
      
      disabledTiles[_row][_col] = false;
      
      return disabledTiles;
   }
   
   public String getOtherTeam(String _team)
   {
      if(_team.equals("white"))
         return "black";
      else
         return "white";
   }
   
   /**
   * Used to switch a piece at one location to another piece (in this program, this is only used
   to switch the pawn to another piece when it reaches the end of the board)
   */
   
   public String choosePiece()
   {
      String[] options = new String[] {"Queen", "Rook", "Knight", "Bishop"};
      
      int response = JOptionPane.showOptionDialog(null, 
         "Choose a piece you want to replace the pawn with: ", "Choose a piece",
         JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
         null, options, options[0]);
         
      if(response == 0)
         return "queen";
      else if(response == 1)
         return "rook";
      else if(response == 2)
         return "knight";
      else
         return "bishop";
   }
   
   /**
   * This method is ONLY for the CheckKing class. This is used to check if the opponent's pawn is threatening
   the current team's king.
   */
   
   public boolean[][] opponentPawnPressed(TileButton[][] _allTiles, int _row, int _col, String _team)
   {
      String otherTeam = getOtherTeam(_team);
         
      boolean[][] disabledTiles = new boolean[8][8];
      
      disabledTiles = simpleMovements.disableAll(disabledTiles);
      
      int row = _row;
      int col = _col;
      
      row++;
      if(row <= 7)
      {
         if(_allTiles[row][col].getTeam().equals("none"))
         {
            disabledTiles[row][col] = false;
         }
            
         col = _col;
         if(col > 0)
         {
            col--;
            if(_allTiles[row][col].getTeam().equals(otherTeam))
            {
               disabledTiles[row][col] = false;
            }
         }
         
         col = _col;
         col++;
         if(col < disabledTiles.length)
         {
            if(_allTiles[row][col].getTeam().equals(otherTeam))
            {
               disabledTiles[row][col] = false;
            }
         }
      }
      
      disabledTiles[_row][_col] = false;
      
      return disabledTiles;
   
   }
   
   public boolean[][] buttonPressed(TileButton[][] _allTiles, int _row, int _col, int _id)
   {
      //gets the team that owns the piece on a tile
      String currentTeamTurn;
      if(_allTiles[_row][_col].getTeam().equals("white"))
      {
         currentTeamTurn = "white";
      }
      else
      {
         currentTeamTurn = "black";
      }
      
      if(_allTiles[_row][_col].getPiece().equals("king"))
         return kingPressed(_allTiles, _row, _col, currentTeamTurn);
      else if(_allTiles[_row][_col].getPiece().equals("queen"))
         return queenPressed(_allTiles, _row, _col, currentTeamTurn);
      else if(_allTiles[_row][_col].getPiece().equals("bishop"))
         return bishopPressed(_allTiles, _row, _col, currentTeamTurn);
      else if(_allTiles[_row][_col].getPiece().equals("knight"))
         return knightPressed(_allTiles, _row, _col, currentTeamTurn);
      else if(_allTiles[_row][_col].getPiece().equals("rook"))
         return rookPressed(_allTiles, _row, _col, currentTeamTurn);
      else
      {
         if(_id == 0)
            return pawnPressed(_allTiles, _row, _col, currentTeamTurn);
         else
            return opponentPawnPressed(_allTiles, _row, _col, currentTeamTurn);
      }
   } 
}