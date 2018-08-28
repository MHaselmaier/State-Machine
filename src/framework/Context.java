package framework;

import java.util.ArrayList;
import java.util.List;

public class Context {
	
	private State currentState;
	private List<State> finalStates;
	
	public Context(State aStartState, State aFinalState) {
		aStartState.entry();
		this.currentState = aStartState;
		this.finalStates = new ArrayList<State>();
		this.finalStates.add(aFinalState);
	}
	
	public Context(State aStartState, List<State> someFinalStates) {
		aStartState.entry();
		this.currentState = aStartState;
		this.finalStates = someFinalStates;
	}
	
	public String getStateName() {
		return this.currentState.toString();
	}
	
	private void setState(State aState) {
		this.currentState = aState;
		this.currentState.entry();
	}
	
	public void process(Event<?> anEvent) {
		State next = this.currentState.process(anEvent);
		if(null != next) {
			setState(next);
		}
	}
	
	public boolean isAtFinalState() {
		if(0 == this.finalStates.size()) return true;
		
		for(State s: this.finalStates) {
			if(this.currentState.equals(s)) return true;
		}
		return false;
	}
	
}