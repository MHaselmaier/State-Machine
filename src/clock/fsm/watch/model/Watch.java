package clock.fsm.watch.model;

import java.awt.Toolkit;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Watch
{
   // Obvserver Pattern-Code
   private List<WatchStateChangeListener> listeners = new ArrayList<>();
   
   // Threads für die Uhrzeit und das Weckerklingeln
   private Thread workerThread = null;
   private Thread beepThread = null;

   // Uhrzeit
   volatile private int minute = 0;
   volatile private int stunde = 0;

   // Weckzeit
   volatile private int alarmMinute = 0;
   volatile private int alarmStunde = 0;
   volatile boolean  isBeeping = false;

   public Watch()
   {
      super();

      // Initialisiere Uhr mit aktueller Zeit
      LocalTime time = LocalTime.now();
      this.minute = time.getMinute();
      this.stunde = time.getHour();
   }
   
   public boolean isBeeping()
   {
      return this.isBeeping;
   }
   
   public int getMinute()
   {
      return this.minute;
   }

   public int getStunde()
   {
      return this.stunde;
   }

   public void setUhrMinute(int minute)
   {
      assert minute >= 0 && minute < 60;

      this.minute = minute;
      this.notifyListener();
   }
   
   public void increaseUhrMinute()
   {
      this.minute = (this.minute +1)%60;
      this.notifyListener();
   }
   
   public void decreaseUhrMinute()
   {
      this.minute = (this.minute - 1 + 60)%60;
      this.notifyListener();
   }

   public void setUhrStunde(int stunde)
   {
      assert stunde >= 0 && stunde < 24;

      this.stunde = stunde;
      this.notifyListener();
   }
   
   public void increaseUhrStunde()
   {
      this.stunde = (this.stunde +1)%24;
      this.notifyListener();
   }
   
   public void decreaseUhrStunde()
   {
      this.stunde = (this.stunde - 1 + 24)%24;
      this.notifyListener();
   }
   
   public int getWeckerMinute()
   {
      return this.alarmMinute;
   }

   public int getWeckerStunde()
   {
      return this.alarmStunde;
   }

   public void setWeckerMinute(int minute)
   {
      assert minute >= 0 && minute < 60;

      this.alarmMinute = minute;
      this.notifyListener();
   }
   
   public void increaseWeckerMinute()
   {
      this.alarmMinute = (this.alarmMinute + 1)%60;
      this.notifyListener();
   }
   
   public void decreaseWeckerMinute()
   {
      this.alarmMinute = (this.alarmMinute - 1 + 60)%60;
      this.notifyListener();
   }

   public void setWeckerStunde(int stunde)
   {
      assert stunde >= 0 && stunde < 24;

      this.alarmStunde = stunde;
      this.notifyListener();
   }

   public void increaseWeckerStunde()
   {
      this.alarmStunde = (this.alarmStunde + 1)%24;
      this.notifyListener();
   }
   
   public void decreaseWeckerStunde()
   {
      this.alarmStunde = (this.alarmStunde - 1 + 24)%24;
      this.notifyListener();
   }

   public void startUhr()
   {
      // Ignoriere start, falls Thread noch läuft
      if (this.workerThread != null && this.workerThread.isAlive())
         return;

      // Erzeuge und starte Thread für die Uhrzeit
      this.workerThread = new Thread( new Runnable()
      {
         @Override
         public void run()
         {
            try
            {
               // Pattern-Code
               Watch.this.notifyListener();
               while (Watch.this.workerThread.isInterrupted() == false)
               {
                  // Speedtime
                  TimeUnit.SECONDS.sleep(1); // 60 in real life

                  minute++;
                  if (minute == 60)
                  {
                     minute = 0;
                     stunde++;
                  }

                  if (stunde == 24)
                  {
                     stunde = 0;
                  }

                  // Weckzeit erreicht
                  if (stunde == alarmStunde && minute == alarmMinute)
                  {
                     Watch.this.startBeep();
                  }

                  // Pattern-Code
                  Watch.this.notifyListener();
               }
            }
            catch (InterruptedException exce)
            {
            }
         }
      });
      this.workerThread.start();
   }

   public void stopUhr()
   {
      if (workerThread != null)
      {
         this.workerThread.interrupt();
      }
   }


   public void startBeep()
   {
      // Ignoriere start, falls Thread noch läuft
      if (this.beepThread != null && this.beepThread.isAlive())
         return;

      // Erzeuge und starte Thread für das Wecksignal
      this.beepThread = new Thread(new Runnable()
      {

         @Override
         public void run()
         {
            isBeeping = true;
            notifyListener();
            try
            {
               while (Watch.this.beepThread.isInterrupted() == false)
               {
                  TimeUnit.SECONDS.sleep(1); 
                  Toolkit.getDefaultToolkit().beep();
               }
            }
            catch (InterruptedException exce)
            {
            }
         }
      });
      this.beepThread.start();
   }

   public void stopBeep()
   {
      if (beepThread != null)
      {
         isBeeping = false;
         this.beepThread.interrupt();
         notifyListener();
      }
   }

   // Pattern-Code
   public synchronized void registerStateChangeListener(WatchStateChangeListener listener)
   {
      if (listener != null)
      {
         this.listeners.add(listener);
      }
   }

   // Pattern-Code
   public synchronized void deregisterStateChangeListener(WatchStateChangeListener listener)
   {
      if (listener != null)
      {
         this.listeners.remove(listener);
      }
   }

   // Pattern-Code
   private synchronized void notifyListener()
   {
      for (WatchStateChangeListener l : this.listeners)
      {
         l.stateChanged();
      }
   }
}
