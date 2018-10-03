public class StartingPieces
{
   Pieces pieces = new Pieces();

   public void setUpPieces(int _teamId, TileButton[][] __allTiles)
   {
      if(_teamId == 0)//sets up pieces if you're player one (white)
      {
         setWhiteStartingPieces(__allTiles);
      }
      else//sets up pieces if you're player two (black)
      {
         setBlackStartingPieces(__allTiles);
      }
   }
   
   public void setWhiteStartingPieces(TileButton[][] _allTiles)
   {
      //sets up white pieces
      pieces.setWhiteKing(_allTiles[7][4]);
      _allTiles[7][4].setPiece("king");
      _allTiles[7][4].setTeam("white");
      pieces.setWhiteQueen(_allTiles[7][3]);
      _allTiles[7][3].setPiece("queen");
      _allTiles[7][3].setTeam("white");
      pieces.setWhiteBishop(_allTiles[7][2]);
      _allTiles[7][2].setPiece("bishop");
      _allTiles[7][2].setTeam("white");
      pieces.setWhiteBishop(_allTiles[7][5]);
      _allTiles[7][5].setPiece("bishop");
      _allTiles[7][5].setTeam("white");
      pieces.setWhiteKnight(_allTiles[7][1]);
      _allTiles[7][1].setPiece("knight");
      _allTiles[7][1].setTeam("white");
      pieces.setWhiteKnight(_allTiles[7][6]);
      _allTiles[7][6].setPiece("knight");
      _allTiles[7][6].setTeam("white");
      pieces.setWhiteRook(_allTiles[7][0]);
      _allTiles[7][0].setPiece("rook");
      _allTiles[7][0].setTeam("white");
      pieces.setWhiteRook(_allTiles[7][7]);
      _allTiles[7][7].setPiece("rook");
      _allTiles[7][7].setTeam("white");
      for(int i = 0; i < _allTiles[0].length; i++)//sets all 8 white pawns
      {
         pieces.setWhitePawn(_allTiles[6][i]);
         _allTiles[6][i].setPiece("pawn");
         _allTiles[6][i].setTeam("white");
      }
      
      //sets up black pieces
      pieces.setBlackKing(_allTiles[0][4]);
      _allTiles[0][4].setPiece("king");
      _allTiles[0][4].setTeam("black");
      pieces.setBlackQueen(_allTiles[0][3]);
      _allTiles[0][3].setPiece("queen");
      _allTiles[0][3].setTeam("black");
      pieces.setBlackBishop(_allTiles[0][2]);
      _allTiles[0][2].setPiece("bishop");
      _allTiles[0][2].setTeam("black");
      pieces.setBlackBishop(_allTiles[0][5]);
      _allTiles[0][5].setPiece("bishop");
      _allTiles[0][5].setTeam("black");
      pieces.setBlackKnight(_allTiles[0][1]);
      _allTiles[0][1].setPiece("knight");
      _allTiles[0][1].setTeam("black");
      pieces.setBlackKnight(_allTiles[0][6]);
      _allTiles[0][6].setPiece("knight");
      _allTiles[0][6].setTeam("black");
      pieces.setBlackRook(_allTiles[0][0]);
      _allTiles[0][0].setPiece("rook");
      _allTiles[0][0].setTeam("black");
      pieces.setBlackRook(_allTiles[0][7]);
      _allTiles[0][7].setPiece("rook");
      _allTiles[0][7].setTeam("black");
      for(int i = 0; i < _allTiles[0].length; i++)//sets all 8 white pawns
      {
         pieces.setBlackPawn(_allTiles[1][i]);
         _allTiles[1][i].setPiece("pawn");
         _allTiles[1][i].setTeam("black");
      }
   }
   
   public void setBlackStartingPieces(TileButton[][] _allTiles)
   {
      //sets up black pieces
      pieces.setBlackKing(_allTiles[7][3]);
      _allTiles[7][3].setPiece("king");
      _allTiles[7][3].setTeam("black");
      pieces.setBlackQueen(_allTiles[7][4]);
      _allTiles[7][4].setPiece("queen");
      _allTiles[7][4].setTeam("black");
      pieces.setBlackBishop(_allTiles[7][2]);
      _allTiles[7][2].setPiece("bishop");
      _allTiles[7][2].setTeam("black");
      pieces.setBlackBishop(_allTiles[7][5]);
      _allTiles[7][5].setPiece("bishop");
      _allTiles[7][5].setTeam("black");
      pieces.setBlackKnight(_allTiles[7][1]);
      _allTiles[7][1].setPiece("knight");
      _allTiles[7][1].setTeam("black");
      pieces.setBlackKnight(_allTiles[7][6]);
      _allTiles[7][6].setPiece("knight");
      _allTiles[7][6].setTeam("black");
      pieces.setBlackRook(_allTiles[7][0]);
      _allTiles[7][0].setPiece("rook");
      _allTiles[7][0].setTeam("black");
      pieces.setBlackRook(_allTiles[7][7]);
      _allTiles[7][7].setPiece("rook");
      _allTiles[7][7].setTeam("black");
      for(int i = 0; i < _allTiles[0].length; i++)//sets all 8 white pawns
      {
         pieces.setBlackPawn(_allTiles[6][i]);
         _allTiles[6][i].setPiece("pawn");
         _allTiles[6][i].setTeam("black");
      }
      
      //sets up black pieces
      pieces.setWhiteKing(_allTiles[0][3]);
      _allTiles[0][3].setPiece("king");
      _allTiles[0][3].setTeam("white");
      pieces.setWhiteQueen(_allTiles[0][4]);
      _allTiles[0][4].setPiece("queen");
      _allTiles[0][4].setTeam("white");
      pieces.setWhiteBishop(_allTiles[0][2]);
      _allTiles[0][2].setPiece("bishop");
      _allTiles[0][2].setTeam("white");
      pieces.setWhiteBishop(_allTiles[0][5]);
      _allTiles[0][5].setPiece("bishop");
      _allTiles[0][5].setTeam("white");
      pieces.setWhiteKnight(_allTiles[0][1]);
      _allTiles[0][1].setPiece("knight");
      _allTiles[0][1].setTeam("white");
      pieces.setWhiteKnight(_allTiles[0][6]);
      _allTiles[0][6].setPiece("knight");
      _allTiles[0][6].setTeam("white");
      pieces.setWhiteRook(_allTiles[0][0]);
      _allTiles[0][0].setPiece("rook");
      _allTiles[0][0].setTeam("white");
      pieces.setWhiteRook(_allTiles[0][7]);
      _allTiles[0][7].setPiece("rook");
      _allTiles[0][7].setTeam("white");
      for(int i = 0; i < _allTiles[0].length; i++)//sets all 8 white pawns
      {
         pieces.setWhitePawn(_allTiles[1][i]);
         _allTiles[1][i].setPiece("pawn");
         _allTiles[1][i].setTeam("white");
      }
   }
}