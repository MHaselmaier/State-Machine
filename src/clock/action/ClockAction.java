package clock.action;

import clock.fsm.watch.gui.WatchDisplay;
import framework.Action;

public abstract class ClockAction implements Action {
	
	protected static WatchDisplay display;
	protected static int minute, stunde;
	
	public static final void setDisplay(WatchDisplay display) {
		ClockAction.display = display;
	}
	
}