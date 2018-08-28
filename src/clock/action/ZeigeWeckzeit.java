package clock.action;

public class ZeigeWeckzeit extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.showWeckzeit();
	}

}