package clock._driver;


import javax.swing.SwingUtilities;

import clock.fsm.watch.gui.WatchButtonListener;
import clock.fsm.watch.gui.WatchDisplay;
import clock.fsm.watch.model.Watch;


public class Driver
{   
   private static class ButtonEventHandler implements WatchButtonListener
   {
      private WatchDisplay display;
      
      public ButtonEventHandler(WatchDisplay display)
      {
         this.display = display;
      }
      
      @Override
      public void event(String event)
      {
         System.out.println( event );
         switch( event )
         {
            case "A":
               display.showWeckzeit();
               break;
            case "B":
               display.showUhrzeit();
               break;
            case "C":
               display.getWatch().startBeep();
               break;
            case "D":
               display.getWatch().stopBeep();
               break;
            default:  
                break;
         }      
      }
   }
   
   
  public static void main(String[] args)
  {
    Watch watch = new Watch();
    watch.startUhr();

    // Zeige GUI
    SwingUtilities.invokeLater(new Runnable() {
       public void run() {
          WatchDisplay display = new WatchDisplay(watch);
          display.setLocationRelativeTo(null);
          display.registerWatchButtonListener( new ButtonEventHandler(display)  );
       }
   });

  }

}
