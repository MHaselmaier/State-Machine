package clock;

import javax.swing.SwingUtilities;

import clock.action.ClockAction;
import clock.fsm.watch.gui.WatchButtonListener;
import clock.fsm.watch.gui.WatchDisplay;
import clock.fsm.watch.model.Watch;
import framework.Context;
import framework.Event;
import framework.ExtendedFramework;

public class DigitalUhr implements WatchButtonListener {
	
	private Context clock;
	
	public DigitalUhr() {
		this.clock = ExtendedFramework.generateContext(ExtendedFramework.readXML("res/clock.xml", "res/ErweiterterAutomatSchema.xsd"));
		
		Watch watch = new Watch();
		watch.startUhr();
		
		// Zeige GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WatchDisplay display = new WatchDisplay(watch);
				ClockAction.setDisplay(display);
				display.setLocationRelativeTo(null);
				display.registerWatchButtonListener(DigitalUhr.this);
			}
		});
	}

	@Override
	public void event(String input) {
		Event<String> event = new Event<String>(input);
		
		this.clock.process(event);
	}
	
	public static void main(String[] args) {
		new DigitalUhr();
	}

}