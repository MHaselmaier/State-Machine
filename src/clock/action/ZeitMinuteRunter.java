package clock.action;

public class ZeitMinuteRunter extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().decreaseUhrMinute();
	}

}