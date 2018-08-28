package objectoriented;

import java.util.ArrayList;
import java.util.List;

public class Automat2 {

	public static boolean run(Event<Character>[] someInput) {
		State s = new State("s", () -> System.out.println("entry s"),
								 () -> System.out.println("exit s"));
		State q1 = new State("q1", () -> System.out.println("entry q1"),
				 				   () -> System.out.println("exit q1"));
		State q2 = new State("q2", () -> System.out.println("entry q2"),
				 				   () -> System.out.println("exit q2"));
		State r1 = new State("r1", () -> System.out.println("entry r1"),
				 				   () -> System.out.println("exit r1"));
		State r2 = new State("r2", () -> System.out.println("entry r2"),
				 				   () -> System.out.println("exit r2"));
		
		Transition toq1froms = new Transition(q1, () -> System.out.println("to q1 from s"));
		Transition tor1froms = new Transition(r1, () -> System.out.println("to r1 from s"));
		Transition toq1fromq1 = new Transition(q1, () -> System.out.println("to q1 from q1"));
		Transition toq2fromq1 = new Transition(q2, () -> System.out.println("to q2 from q1"));
		Transition toq1fromq2 = new Transition(q1, () -> System.out.println("to q1 from q2"));
		Transition toq2fromq2 = new Transition(q2, () -> System.out.println("to q2 from q2"));
		Transition tor1fromr1 = new Transition(r1, () -> System.out.println("to r1 from r1"));
		Transition tor2fromr1 = new Transition(r2, () -> System.out.println("to r2 from r1"));
		Transition tor1fromr2 = new Transition(r1, () -> System.out.println("to r1 from r2"));
		Transition tor2fromr2 = new Transition(r2, () -> System.out.println("to r2 from r2"));
		
		Event<Character> a = new Event<Character>('a');
		Event<Character> b = new Event<Character>('b');
		
		s.addTransition(a, toq1froms);
		s.addTransition(b, tor1froms);
		q1.addTransition(a, toq1fromq1);
		q1.addTransition(b, toq2fromq1);
		q2.addTransition(a, toq1fromq2);
		q2.addTransition(b, toq2fromq2);
		r1.addTransition(a, tor2fromr1);
		r1.addTransition(b, tor1fromr1);
		r2.addTransition(a, tor2fromr2);
		r2.addTransition(b, tor1fromr2);
		
		List<State> finalStates = new ArrayList<State>();
		finalStates.add(q1);
		finalStates.add(r1);
		Context fsm = new Context(s, finalStates);
		
		for(int i = 0; i < someInput.length; i++) {
			fsm.process(someInput[i]);
		}
		
		return fsm.isAtFinalState();
	}
	
}