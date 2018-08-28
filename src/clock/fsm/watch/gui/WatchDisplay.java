package clock.fsm.watch.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import clock.fsm.watch.model.Watch;
import clock.fsm.watch.model.WatchStateChangeListener;

public class WatchDisplay extends JFrame implements WatchStateChangeListener {
	private static final long serialVersionUID = -631034923132906745L;

	private enum Anzeige {
		UHRZEIT, WECKZEIT
	};

	private Anzeige modusanzeige = Anzeige.UHRZEIT;

	private DisplayPanel displayPanel = new DisplayPanel();
	private ButtonPanel buttonPanel = new ButtonPanel();
	private JLabel modus = null;

	private List<WatchButtonListener> listener = new ArrayList<WatchButtonListener>();
	private Watch watch = null;

	public WatchDisplay(Watch watch) {
		super("My Watch");

		this.watch = watch;
		this.watch.registerStateChangeListener(this);

		modus = new JLabel("Uhrzeit");
		modus.setFont(new Font("Serif", Font.BOLD, 25));
		JPanel modusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		modusPanel.add(modus);

		buttonPanel.getAButton().addActionListener((e) -> fireEvent("A"));
		buttonPanel.getBButton().addActionListener((e) -> fireEvent("B"));
		buttonPanel.getCButton().addActionListener((e) -> fireEvent("C"));
		buttonPanel.getDButton().addActionListener((e) -> fireEvent("D"));

		buttonPanel.getForwardButton().addActionListener((e) -> fireEvent("hoch"));
		buttonPanel.getBackButton().addActionListener((e) -> fireEvent("runter"));

		this.setLayout(new BorderLayout(15, 15));
		this.add(modusPanel, BorderLayout.NORTH);
		this.add(displayPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	public Watch getWatch() {
		return this.watch;
	}

	public void showUhrzeit() {
		this.modusanzeige = Anzeige.UHRZEIT;
		this.setText("Uhrzeit");
	}

	public void showWeckzeit() {
		this.modusanzeige = Anzeige.WECKZEIT;
		this.setText("Weckzeit");
	}

	public void setMinute(int value) {
		this.displayPanel.setMinute(value);
	}

	public void setStunde(int value) {
		this.displayPanel.setStunde(value);
	}

	public void setText(String title) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				modus.setText(title);
			}
		});

	}

	private synchronized void fireEvent(String event) {
		for (WatchButtonListener listener : this.listener) {
			listener.event(event);
		}
	}

	public synchronized void registerWatchButtonListener(WatchButtonListener listener) {
		this.listener.add(listener);
	}

	@Override
	public void stateChanged() {
		if(watch.getStunde() == watch.getWeckerStunde() && watch.getMinute() == watch.getWeckerMinute()) fireEvent("Weckzeit erreicht");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch (modusanzeige) {
				case WECKZEIT:
					setStunde(watch.getWeckerStunde());
					setMinute(watch.getWeckerMinute());
					break;
				case UHRZEIT:
					setStunde(watch.getStunde());
					setMinute(watch.getMinute());
					break;
				default:
					setStunde(0);
					setMinute(0);
					break;
				}
			}
		});

	}

}
