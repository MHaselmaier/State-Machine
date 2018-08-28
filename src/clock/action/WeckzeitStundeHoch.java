package clock.action;

public class WeckzeitStundeHoch extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().increaseWeckerStunde();
	}

}