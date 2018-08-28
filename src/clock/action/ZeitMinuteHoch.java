package clock.action;

public class ZeitMinuteHoch extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().increaseUhrMinute();
	}

}