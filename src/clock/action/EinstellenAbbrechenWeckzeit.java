package clock.action;

public class EinstellenAbbrechenWeckzeit extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().setWeckerMinute(ClockAction.minute);
		ClockAction.display.getWatch().setWeckerStunde(ClockAction.stunde);
	}

}