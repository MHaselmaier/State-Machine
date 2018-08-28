package clock.action;

public class EinstellenWeckzeit extends ClockAction {

	@Override
	public void action() {
		ClockAction.minute = ClockAction.display.getWatch().getWeckerMinute();
		ClockAction.stunde = ClockAction.display.getWatch().getWeckerStunde();
	}
	
}