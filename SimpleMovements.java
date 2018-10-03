/**
* The purpose of this class is to shorten/simplify the Movement class
*/

public class SimpleMovements
{
   /**
   * This allows a piece to move up or down till they hit another piece
   */

   public boolean[][] upAndDown(String _team, String _otherTeam, boolean[][] _disabledTiles, 
      TileButton[][] _allTiles, int _row, int  _col)
   {
      for(int col = _col - 1; col >= 0; col--)//enables all of the buttons on the left of the rook
      {
         if(_allTiles[_row][col].getTeam().equals(_team))
         {
            break;
         }
         else if(_allTiles[_row][col].getTeam().equals(_otherTeam))
         {
            _disabledTiles[_row][col] = false;
            break;
         }
         else
         {
            _disabledTiles[_row][col] = false;
         }
      }
      
      for(int col = _col + 1; col < _disabledTiles.length; col++)//enables all of the buttons on the right of the rook
      {
         if(_allTiles[_row][col].getTeam().equals(_team))
         {
            break;
         }
         else if(_allTiles[_row][col].getTeam().equals(_otherTeam))
         {
            _disabledTiles[_row][col] = false;
            break;
         }
         else
         {
            _disabledTiles[_row][col] = false;
         }
      }
   
      return _disabledTiles;
   }
   
   /**
   * This allows a piece to move left or right till they hit another piece
   */
   
   public boolean[][] leftAndRight(String _team, String _otherTeam, boolean[][] _disabledTiles, 
      TileButton[][] _allTiles, int _row, int  _col)
   {
      for(int row = _row - 1; row >= 0; row--)
      {
         if(_allTiles[row][_col].getTeam().equals(_team))
         {
            break;
         }
         else if(_allTiles[row][_col].getTeam().equals(_otherTeam))
         {
            _disabledTiles[row][_col] = false;
            break;
         }
         else
         {
            _disabledTiles[row][_col] = false;
         }
      }
      
      for(int row = _row + 1; row < _disabledTiles.length; row++)
      {
         if(_allTiles[row][_col].getTeam().equals(_team))
         {
            break;
         }
         else if(_allTiles[row][_col].getTeam().equals(_otherTeam))
         {
            _disabledTiles[row][_col] = false;
            break;
         }
         else
         {
            _disabledTiles[row][_col] = false;
         }
      }
   
      return _disabledTiles;
   }
   
   /**
   * allows a piece to move to it's bottom, right tile till it hits another piece or the edge of the board
   */
   
   public boolean[][] bottomRight(String _team, String _otherTeam, boolean[][] _disabledTiles, 
      TileButton[][] _allTiles, int _row, int  _col)
   {
      _col++;
      _row++;
      while(_col < _disabledTiles.length && _row < _disabledTiles.length)//bottom right
      {
         if(_allTiles[_row][_col].getTeam().equals(_team))
            break;
         else if(_allTiles[_row][_col].getTeam().equals(_otherTeam))
         {
            _disabledTiles[_row][_col] = false;
            break;
         }
         else
         {
            _disabledTiles[_row][_col] = false;
         }
         
         _col++;
         _row++;
      }
         
      return _disabledTiles;
   }
   
   /**
   * allows a piece to move to it's top, left tile till it hits another piece or the edge of the board
   */
   
   public boolean[][] topLeft(String _team, String _otherTeam, boolean[][] _disabledTiles, 
      TileButton[][] _allTiles, int _row, int  _col)
   {
      _col--;
      _row--;
      while(_col >= 0 && _row >= 0)//top left
      {
         if(_allTiles[_row][_col].getTeam().equals(_team))
            break;
         else if(_allTiles[_row][_col].getTeam().equals(_otherTeam))
         {
            _disabledTiles[_row][_col] = false;
            break;
         }
         else
         {
            _disabledTiles[_row][_col] = false;
         }
         
         _col--;
         _row--;
      }
         
      return _disabledTiles;
   }
   
   public boolean[][] bottomLeft(String _team, String _otherTeam, boolean[][] _disabledTiles, 
      TileButton[][] _allTiles, int _row, int  _col)
   {
      _col--;
      _row++;
      while(_col >= 0 && _row < _disabledTiles.length)//top right
      {
         if(_allTiles[_row][_col].getTeam().equals(_team))
            break;
         else if(_allTiles[_row][_col].getTeam().equals(_otherTeam))
         {
            _disabledTiles[_row][_col] = false;
            break;
         }
         else
         {
            _disabledTiles[_row][_col] = false;
         }
         
         _col--;
         _row++;
      }
         
      return _disabledTiles;
   }
   
   public boolean[][] topRight(String _team, String _otherTeam, boolean[][] _disabledTiles, 
      TileButton[][] _allTiles, int _row, int  _col)
   {
      _col++;
      _row--;
      while(_col < _disabledTiles.length && _row >= 0)//top right
      {
         if(_allTiles[_row][_col].getTeam().equals(_team))
            break;
         else if(_allTiles[_row][_col].getTeam().equals(_otherTeam))
         {
            _disabledTiles[_row][_col] = false;
            break;
         }
         else
         {
            _disabledTiles[_row][_col] = false;
         }
         
         _col++;
         _row--;
      }
         
      return _disabledTiles;
   }
   
   /**
   * Moves to the surrounding tiles by 1 (up, up right, right, down right, down, down left, left, up left)
   */
   
   public boolean[][] surroundingTiles(String _team, String _otherTeam, boolean[][] _disabledTiles, 
      TileButton[][] _allTiles, int _row, int  _col)
   {
      int row = _row;
      int col = _col;
      
      //tiles enabled
      row--;
      if(_row != 0)//top
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            _disabledTiles[row][col] = false;
         }
      }
      col++;
      if(_col != 7 && _row != 0)//top right
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            _disabledTiles[row][col] = false;
         }
      }
      row = _row;
      if(_col != 7)//right
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            _disabledTiles[row][col] = false;
         }
      }
      row++;//bottom right
      if(_row != 7 && _col != 7)
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            _disabledTiles[row][col] = false;
         }
      }
      col = _col;
      if(_row != 7)//bottom
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            _disabledTiles[row][col] = false;
         }
      }
      col--;
      if(_row != 7 && _col != 0)//bottom left
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            _disabledTiles[row][col] = false;
         }
      }
      row--;
      if(_col != 0)//left
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            _disabledTiles[row][col] = false;
         }
      }
      row--;
      if(_row != 0 && _col != 0)//up left
      {
         if(!_allTiles[row][col].getTeam().equals(_team))
         {
            _disabledTiles[row][col] = false;
         }
      }
      
      return _disabledTiles;
   }
   
   /**
   * Disables all of the tiles in the 2d array
   */
   
   public boolean[][] disableAll(boolean[][] _disabledTiles)
   {
      for(int row = 0; row < _disabledTiles.length; row++)
      {
         for(int col = 0; col < _disabledTiles[0].length; col++)
         {
            _disabledTiles[row][col] = true;
         }
      }
      
      return _disabledTiles;
   }
}