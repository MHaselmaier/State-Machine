package framework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParallelState extends State {
	
	private final List<State> subStates;
	
	public ParallelState(String aName, List<State> subStates) {
		this(aName, null, null, subStates);
	}
	
	public ParallelState(String aName, Action entry, Action exit, List<State> subStates) {
		super(aName, entry, exit);
		this.subStates = subStates;
	}
	
	public Map<String, State> getSubstates() {
		Map<String, State> result = new HashMap<String, State>();
		
		for(State state: this.subStates) {
			result.put(state.getName(), state);
			result.putAll(state.getSubstates());
		}
		
		return result;
	}
	
	public State process(Event<?> anEvent) {
		Transition transition = super.transitions.get(anEvent.get());
		if(null != transition) {
			for(State s: this.subStates) {
				s.exit();
			}
			exit();
			transition.action();
			return transition.getTargetState();
		}
		
		for(int i = 0; i < this.subStates.size(); ++i) {
			State next = this.subStates.get(i).process(anEvent);
			if(null != next) {
				if(!this.subStates.contains(next) && !this.subStates.get(i).getSubstates().containsKey(next.getName())) {
					return next;
				}
				else {
					next.entry();
				}
			}
		}
		
		return null;
	}
	
}