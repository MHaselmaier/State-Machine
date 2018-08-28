package clock.action;

public class EinstellenAbbrechenZeit extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().startUhr();
		ClockAction.display.getWatch().setUhrMinute(ClockAction.minute);
		ClockAction.display.getWatch().setUhrStunde(ClockAction.stunde);
	}

}