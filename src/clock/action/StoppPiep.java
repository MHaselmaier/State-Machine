package clock.action;

public class StoppPiep extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().stopBeep();
	}

}