package clock.action;

public class WeckzeitMinuteHoch extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().increaseWeckerMinute();
	}

}