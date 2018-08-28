package clock.action;

public class WeckzeitStundeRunter extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().decreaseWeckerStunde();
	}

}