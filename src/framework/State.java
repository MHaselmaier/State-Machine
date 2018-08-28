package framework;

import java.util.HashMap;
import java.util.Map;

public class State {
	
	protected String name;
	protected Action entry;
	protected Action exit;
	protected Map<Object, Transition> transitions = new HashMap<Object, Transition>();
	
	public State(String aName) {
		this(aName, null, null);
	}
	
	public State(String aName, Action entry, Action exit) {
		this.name = aName;
		this.entry = entry;
		this.exit = exit;
	}
	
	public Map<String, State> getSubstates() {
		return new HashMap<String, State>();
	}
	
	public void addTransition(Event<?> anEvent, Transition aTransition) {
		transitions.put(anEvent.get(), aTransition);
	}
	
	public void entry() {
		if(null != this.entry) this.entry.action();
	}
	
	public void exit() {
		if(null != this.exit) this.exit.action();
	}
	
	public State process(Event<?> anEvent) {
		Transition transition = transitions.get(anEvent.get());
		if(null == transition) return null;
		exit();
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
		
		return true;
	}
	
}