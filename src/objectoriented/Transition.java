package objectoriented;

public class Transition {

	private State targetState;
	private Action action;
	
	public Transition(State aState) {
		this(aState, null);
	}
	
	public Transition(State aState, Action action) {
		this.targetState = aState;
		this.action = action;
	}
	
	public State getTargetState() {
		return this.targetState;
	}
	
	public void action() {
		if(null != this.action) this.action.action();
	}
	
}