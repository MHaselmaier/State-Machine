package clock.action;

public class ZeigeZeit extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.showUhrzeit();
	}
	
}