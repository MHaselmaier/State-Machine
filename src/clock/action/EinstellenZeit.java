package clock.action;

public class EinstellenZeit extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().stopUhr();
		ClockAction.minute = ClockAction.display.getWatch().getMinute();
		ClockAction.stunde = ClockAction.display.getWatch().getStunde();
	}
	
}