package clock.action;

public class ZeitStundeRunter extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().decreaseUhrStunde();
	}

}