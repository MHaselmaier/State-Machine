package objectoriented;

import java.util.HashMap;
import java.util.Map;

public class State {
	
	private String name;
	private Action entry;
	private Action exit;
	private Map<Object, Transition> transitions;
	
	public State(String aName) {
		this(aName, null, null);
	}
	
	public State(String aName, Action entry, Action exit) {
		this.name = aName;
		this.entry = entry;
		this.exit = exit;
		this.transitions = new HashMap<Object, Transition>();
	}
	
	public void addTransition(Event<?> anEvent, Transition aTransition) {
		this.transitions.put(anEvent.get(), aTransition);
	}
	
	public void entry() {
		if(null != this.entry) this.entry.action();
	}
	
	public void exit() {
		if(null != this.exit) this.exit.action();
	}
	
	public State process(Event<?> anEvent) {
		Transition transition = this.transitions.get(anEvent.get());
		if(null == transition) return null;
		transition.action();
		
		return transition.getTargetState();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return "State: " + this.name;
	}
	
	@Override
	public boolean equals(Object anOther) {
		if(this == anOther) return true;
		if(null == anOther) return false;
		if(!(anOther instanceof State)) return false;
		
		State other = (State)anOther;
		if(!this.name.equals(other.name)) return false;
		if(!this.transitions.equals(other.transitions)) return false;
		
		return true;
	}
	
}