package objectoriented;

public class Automat1 {
	
	public static boolean run(Event<Integer>[] someInput) {
		State q1 = new State("q1", () -> System.out.println("entry q1"), () -> System.out.println("exit q1"));
		State q2 = new State("q2", () -> System.out.println("entry q2"), () -> System.out.println("exit q2"));
		
		Transition toq1fromq1 = new Transition(q1, () -> System.out.println("to q1 from q1"));
		Transition toq2fromq1 = new Transition(q2, () -> System.out.println("to q2 from q1"));
		Transition toq1fromq2 = new Transition(q1, () -> System.out.println("to q1 from q2"));
		Transition toq2fromq2 = new Transition(q2, () -> System.out.println("to q2 from q2"));
		
		Event<Integer> zero = new Event<Integer>(0);
		Event<Integer> one = new Event<Integer>(1);
		
		q1.addTransition(zero, toq1fromq1);
		q1.addTransition(one, toq2fromq1);
		
		q2.addTransition(zero, toq1fromq2);
		q2.addTransition(one, toq2fromq2);
		
		Context fsm = new Context(q1, q1);
		
		for(int i = 0; i < someInput.length; i++) {
			fsm.process(someInput[i]);
		}
		
		return fsm.isAtFinalState();
	}
	
}