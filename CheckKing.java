public class CheckKing
{
   /**
   * This looks through all of the moves a piece can move to, and removes any of the moves that puts the king
   in danger
   */
   
   public boolean[][] removeIllegalMoves(TileButton[][] _allTiles, boolean[][] _disabledTiles, int _playerTeamId, 
      int _pressedRow, int _pressedCol)
   {
      for(int row = 0; row < _disabledTiles.length; row++)
      {
         for(int col = 0; col < _disabledTiles[0].length; col++)
         {
            
            if(_disabledTiles[row][col] == false)
            {
               /*removes the piece info from the pressed button and adds it to the other tile to test if
               the king will be vulnerable if the piece moved here. (only the info is moved, not the piece 
               picture)*/
               
               String piece = _allTiles[_pressedRow][_pressedCol].getPiece();
               String team = _allTiles[_pressedRow][_pressedCol].getTeam();
               
               _allTiles[_pressedRow][_pressedCol].removePieceInfo();
               
               String pieceAtTile = _allTiles[row][col].getPiece();
               String teamAtTile = _allTiles[row][col].getTeam();
               
               _allTiles[row][col].setPieceInfo(piece, team);
               
               if(isIllegalMove(_allTiles, getTeam(_playerTeamId)) == true)
               {
                  _allTiles[_pressedRow][_pressedCol].removePieceInfo();
                  _disabledTiles[row][col] = true;
               }
               
               if(!teamAtTile.equals("none"))
               {
                  _allTiles[row][col].setPieceInfo(pieceAtTile, teamAtTile);
               }
               else
                  _allTiles[row][col].removePieceInfo();
               _allTiles[_pressedRow][_pressedCol].setPieceInfo(piece, team);
            }
         }
      }
      
      return _disabledTiles;
   }
   
   /**
   * This checks a single move white can make. If this move threatens the player's king, then it is an illegal move;
   this will return false if it is an illegal move.
   */
   
   public boolean isIllegalMove(TileButton[][] _allTiles, String _team)
   {
      return kingIsVulnerable(_allTiles, _team);//if the king is vulerable, then this is an illegal move
   }
   
   /**
   * Checks every single one of the opponent's chess pieces and checks whether the king is under attack by this piece or not
   */
   
   public boolean kingIsVulnerable(TileButton[][] _allTiles, String _team)
   {
      String opponentTeam = getOpponentTeam(_team);
      
      /*checks every single tile on the board. If the tile contains a opposing player's piece, then this will
      check if this piece can attack the current player's king. If it can, this method will return false*/ 
      for(int row = 0; row < _allTiles.length; row++)
      {
         for(int col = 0; col < _allTiles[0].length; col++)
         {
            //checks if the opposing player owns a piece at this tile
            if(_allTiles[row][col].getTeam().equals(opponentTeam))
            {
               if(pieceIsThreat(_allTiles, _team, row, col) == true)
                  return true;
            }
         }
      }
      
      return false;
   }
   
   public boolean pieceIsThreat(TileButton[][] _allTiles, String _team, int _row, int _col)
   {
      Movements movements = new Movements();
      int kingRow = getKingRow(_allTiles, _team);
      int kingCol = getKingCol(_allTiles, _team);
      
      //all of the tiles the opponent piece can attack from their position
      boolean[][] attackedTiles = movements.buttonPressed(_allTiles, _row, _col, 1);
      boolean threat = false;
      
      //checks if one of the tiles the opponent piece can attack is the current player's king
      for(int row = 0; row < _allTiles.length; row++)
      {
         for(int col = 0; col < _allTiles[0].length; col++)
         {
            //if the opponent is attacking a tile that contains the current player's king, then that piece is a threat
            if(attackedTiles[row][col] == false && row == kingRow && col == kingCol)
            {
               threat = true;
               return threat;
            }
         }
      }
      
      return threat;
   }
   
   public int getKingRow(TileButton[][] _allTiles, String _team)
   {
      for(int row = 0; row < _allTiles.length; row++)
      {
         for(int col = 0; col < _allTiles[0].length; col++)
         {
            if(_allTiles[row][col].getTeam().equals(_team))
            {
               if(_allTiles[row][col].getPiece().equals("king"))
                  return row;
            }
         }
      }
      
      return -1;//this will never be used
   }
   
   public int getKingCol(TileButton[][] _allTiles, String _team)
   {
      for(int row = 0; row < _allTiles.length; row++)
      {
         for(int col = 0; col < _allTiles[0].length; col++)
         {
            if(_allTiles[row][col].getTeam().equals(_team))
            {
               if(_allTiles[row][col].getPiece().equals("king"))
                  return col;
            }
         }
      }
      
      return -1;//will never be used
   }
   
   public String getTeam(int _teamId)
   {
      if(_teamId == 0)
         return "white";
      else
         return "black";
   }
   
   public String getOpponentTeam(String _team)
   {
      if(_team.equals("white"))
         return "black";
      else
         return "white";
   }
      
   /**
   * Checks if there is a checkmate or not
   * @return returns true if there is a checkmate
   */
   public boolean isCheckmate(TileButton[][] _allTiles, int _playerTeamId)
   {
      String team = getTeam(_playerTeamId);
      Movements movements = new Movements();
      boolean isCheckmate = false;
      
      boolean[][] disabledTiles;
      int possibleMoves = 0;
      for(int row = 0; row < _allTiles.length; row++)
      {
         for(int col = 0; col < _allTiles[0].length; col++)
         {
            if(_allTiles[row][col].getTeam().equals(team))
            {
               disabledTiles = movements.buttonPressed(_allTiles, row, col, 0);
               disabledTiles = removeIllegalMoves(_allTiles, disabledTiles, _playerTeamId, row, col);
               disabledTiles[row][col] = true;
               possibleMoves += numOfPossibleMoves(disabledTiles);
               if(possibleMoves > 0)
                  break;
            }
         }
         if(possibleMoves > 0)
            break;
      }
      
      if(possibleMoves == 0)
         isCheckmate = true;
      
      return isCheckmate;
   }
   
   public int numOfPossibleMoves(boolean[][] _disabledTiles)
   {
      int num = 0;
      for(int row = 0; row < _disabledTiles.length; row++)
      {
         for(int col = 0; col < _disabledTiles[0].length; col++)
         {
            if(_disabledTiles[row][col] == false)
               num++;
         }
      }
      
      return num;
   }
}