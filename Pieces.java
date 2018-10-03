import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
* The purpose of this class is to create/set the picture of each pieces on a tile/button. This is NOT where we keep
track of all of the pieces.
*/

public class Pieces
{
   public void setWhiteKing(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/whiteKing.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/whiteKing.png"));
   }
   
   public void setWhiteRook(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/whiteRook.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/whiteRook.png"));
   }
   
   public void setWhiteBishop(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/whiteBishop.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/whiteBishop.png"));
   }
   
   public void setWhiteKnight(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/whiteKnight.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/whiteKnight.png"));
   }
   
   public void setWhiteQueen(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/whiteQueen.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/whiteQueen.png"));
   }
   
   public void setWhitePawn(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/whitePawn.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/whitePawn.png"));
   }
   
   public void setBlackKing(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/blackKing.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/blackKing.png"));
   }
   
   public void setBlackRook(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/blackRook.png"));
       _button.setDisabledIcon(new ImageIcon("ChessPieces/blackRook.png"));
   }
   
   public void setBlackBishop(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/blackBishop.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/blackBishop.png"));
   }
   
   public void setBlackKnight(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/blackKnight.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/blackKnight.png"));
   }
   
   public void setBlackQueen(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/blackQueen.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/blackQueen.png"));
   }
   
   public void setBlackPawn(JButton _button)
   {
      _button.setIcon(new ImageIcon("ChessPieces/blackPawn.png"));
      _button.setDisabledIcon(new ImageIcon("ChessPieces/blackPawn.png"));
   }
   
   public void setAPiece(String _piece, String _team, JButton _button)
   {
      if(_team.equals("white"))
      {
         if(_piece.equals("king"))
            setWhiteKing(_button);
         else if(_piece.equals("queen"))
            setWhiteQueen(_button);
         else if(_piece.equals("bishop"))
            setWhiteBishop(_button);
         else if(_piece.equals("knight"))
            setWhiteKnight(_button);
         else if(_piece.equals("rook"))
            setWhiteRook(_button);
         else if(_piece.equals("pawn"))
            setWhitePawn(_button);
      }
      else if(_team.equals("black"))
      {
         if(_piece.equals("king"))
            setBlackKing(_button);
         else if(_piece.equals("queen"))
            setBlackQueen(_button);
         else if(_piece.equals("bishop"))
            setBlackBishop(_button);
         else if(_piece.equals("knight"))
            setBlackKnight(_button);
         else if(_piece.equals("rook"))
            setBlackRook(_button);
         else if(_piece.equals("pawn"))
            setBlackPawn(_button);
      }
   }
   
   public void removePiece(JButton _button)
   {
      _button.setIcon(null);
      _button.setDisabledIcon(null);
   }
}