package framework;

import java.util.HashMap;
import java.util.Map;

public class ComposedState extends State {
	
	private final State firstState;
	private final Map<String, State> subStates;
	private State currState;
	
	public ComposedState(String aName, State firstState, Map<String, State> subStates) {
		this(aName, null, null, firstState, subStates);
	}
	
	public ComposedState(String aName, Action entry, Action exit, State firstState, Map<String, State> subStates) {
		super(aName, entry, exit);
		this.firstState = firstState;
		this.currState = firstState;
		this.subStates = subStates;
	}
	
	public Map<String, State> getSubstates() {
		Map<String, State> result = new HashMap<String, State>();
		
		for(State state: this.subStates.values()) {
			result.put(state.getName(), state);
			result.putAll(state.getSubstates());
		}
		
		return result;
	}
	
	public State process(Event<?> anEvent) {
		Transition transition = super.transitions.get(anEvent.get());
		if(null != transition) {
			this.currState.exit();
			exit();
			transition.action();
			this.currState = this.firstState;
			return transition.getTargetState();
		}
		
		State next = this.currState.process(anEvent);
		if(null != next) {
			if(this.subStates.containsKey(next.getName())) {
				this.currState = next;
			}
			else if (!this.currState.getSubstates().containsKey(next.getName())) {
				exit();
				this.currState = this.firstState;
				return next;
			}
		}
		
		return null;
	}
	
}