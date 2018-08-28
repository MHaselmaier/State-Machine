package clock.fsm.watch.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel
{
  private static final long serialVersionUID = 1060386597109405266L;
  
  private JButton aButton = new JButton("A");
  private JButton bButton = new JButton("B");
  private JButton cButton = new JButton("C");
  private JButton dButton = new JButton("D");
  
  private JButton forwardButton = new JButton(">>");
  private JButton backButton = new JButton("<<");

  public ButtonPanel()
  {
    super();
    JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5 ) );
    row1.add( this.aButton );
    row1.add( this.bButton );
    row1.add( this.cButton );
    row1.add( this.dButton );
    
    JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5 ) );
    row2.add( this.backButton );
    row2.add( this.forwardButton );
    
    this.setLayout(new GridLayout(2,1));
    this.add(row1);
    this.add(row2);
  }
  
  JButton getAButton()
  {
    return this.aButton;
  }
  
  JButton getBButton()
  {
    return this.bButton;
  }
  
  JButton getCButton()
  {
    return this.cButton;
  }
  
  JButton getDButton()
  {
    return this.dButton;
  }
  
  JButton getForwardButton()
  {
    return this.forwardButton;
  }
  
  JButton getBackButton()
  {
    return this.backButton;
  }
}
