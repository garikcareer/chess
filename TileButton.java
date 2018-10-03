import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
* Purpose: This keeps track of each of the 64 tilebutton (the main method will contain a 8x8 2D Array which will hold 64 
TileButtons.) This will determine whether a piece is on a tile or not, and if it is, it will determine whether it is 
player 1's piece or player 2's piece
*/

public class TileButton extends JButton
{
   private String piece = "none";//could be: none, king, queen, knight, bishop, rook, or pawn
   private String team = "none"; //could be: none, white, or black
   private String position = null;//the position of the tile (letters represents columns, numbers represent rows (ex: "A4")
   private boolean hasMoved = false;
   private int backgroundColor = 0;// 0 = light tile, 1 = dark tile

   /**
   * This is a constructor. This sets the TileButton
   * @param _colorId The color of the background of the tile (can be light or dark)
   */
   public TileButton(int _colorId)
   {
      if(_colorId == 0)
         setLightBackground();
      else
         setDarkBackground();
   }
   
   /**
   * Sets the piece which is on the tile button
   * @param _piece A piece which is on the tile
   */
   
   public void setPiece(String _piece)
   {
      piece = _piece;
   }
   
   /**
   * Gets the piece which is on the tile
   * @return the piece which is on the tile
   */
   
   public String getPiece()
   {
      return piece;
   }
   
   /**
   * Sets the team of the piece which is on the tile
   * @param _team the team of the piece which is on the tile
   */
   
   public void setTeam(String _team)
   {
      team = _team;
   }
   
   /**
   * Gets the team of the piece which is on the tile
   * @return the team of the piece which is on the tile
   */
   
   public String getTeam()
   {
      return team;
   }
   
   /**
   * Sets the background color of a light tile
   */
   
   public void setLightBackground()
   {
      Color lightTan = new Color(247, 218, 198);
      setBackground(lightTan);
      setOpaque(true);
      setBorderPainted(false);
      
      backgroundColor = 0;//0 = light background
   }
   
   /**
   * Sets the background color of a dark tile
   */
   
   public void setDarkBackground()
   {
      Color darkTan = new Color(163, 71, 5);
      setBackground(darkTan);
      setOpaque(true);
      setBorderPainted(false);
      
      backgroundColor = 1;//1 = dark background
   }
   
   /**
   * Sets the background color of the light tile that was clicked
   */
   
   public void setSelectedLightBackground()
   {
      Color lightTan = new Color(242, 229, 191);
      setBackground(lightTan);
      setOpaque(true);
      setBorderPainted(false);
   }
   
   /**
   * Sets the background color of the dark tile that was clicked
   */
   
   public void setSelectedDarkBackground()
   {
      Color darkTan = new Color(189, 95, 28);
      setBackground(darkTan);
      setOpaque(true);
      setBorderPainted(false);
   }
   
   /**
   * Gets whether the background color of a tile should be light or dark
   * @return whether the background color is light or dark
   */
   
   public int getBackgroundColor()
   {
      return backgroundColor;
   }
   
   /**
   * Removes the piece infomation (team & piece) from the tilebutton
   */
   
   public void removePieceInfo()
   {
      //FOR DEBUGGIN PURPOSES ONLY... THIS ONLY WORKS FOR PLAYER 1'S TEAM
      setPiece("none");
      setTeam("none");
   }
   
   /**
   * Sets the piece information on the tilebutton
   */
   
   public void setPieceInfo(String _piece, String _team)
   {
      setPiece(_piece);
      setTeam(_team);
   }
   
   /**
   * Sets the position of the tile
   * @param _position the position of the tile
   */
   
   public void setPosition(String _position)
   {
      position = _position;
      //setText(position); //You can uncomment this code, it will make it easier to keep track of all of the buttons
   }
   
   /**
   * Gets the position of the tile
   * @return the position of the tile
   */
   
   public String getPosition()
   {
      return position;
   }
   
   /**
   * Sets the hasMoved to true
   */
   
   public void hasMoved()
   {
      hasMoved = true;
   }
   
   /**
   * Gets whether the piece at the tile has moved
   * @return whether the piece at the tile has moved or not
   */
   
   public boolean getHasMoved()
   {
      return hasMoved;
   }  
   
   /**
   * Gets the row of
   */
}