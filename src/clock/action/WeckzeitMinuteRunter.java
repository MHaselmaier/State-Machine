package clock.action;

public class WeckzeitMinuteRunter extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().decreaseWeckerMinute();
	}

}