package clock.action;

public class ZeitStundeHoch extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().increaseUhrStunde();
	}

}