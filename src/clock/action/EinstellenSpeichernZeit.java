package clock.action;

public class EinstellenSpeichernZeit extends ClockAction {

	@Override
	public void action() {
		ClockAction.display.getWatch().startUhr();
	}
	
}