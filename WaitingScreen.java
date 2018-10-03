import javax.swing.*;

public class WaitingScreen extends JFrame
{
   public WaitingScreen()
   {
      setSize(300, 150);
      setLocationRelativeTo(null);
      setTitle("Searching...");
      
      JLabel message = new JLabel("Searching for an opponent...");
      add(message);
   
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);  
   }
}