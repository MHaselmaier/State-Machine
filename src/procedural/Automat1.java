package procedural;

public class Automat1 {
	
	public static boolean run(final String anInput) {
		String state = "q1";
		
		for(int i = 0; i < anInput.length(); i++) {
			switch(state) {
			case "q1":
				entryActionQ1();
				switch(anInput.charAt(i)) {
				case '1':
					transitionActionToQ2FromQ1();
					state = "q2";
					break;
				case '0':
					transitionActionToQ1FromQ1();
					state = "q1";
					break;
				}
				exitActionQ1();
				break;
			case "q2":
				entryActionQ2();
				switch(anInput.charAt(i)) {
				case '0':
					transitionActionToQ1FromQ2();
					state = "q1";
					break;
				case '1':
					transitionActionToQ2FromQ2();
					state = "q2";
					break;
				}
				exitActionQ2();
				break;
			}
		}
		
		if("q1" != state) {
			return false;
		}
		return true;
	}
	
	private static void entryActionQ1() {
		System.out.println("entry q1");
	}
	
	private static void entryActionQ2() {
		System.out.println("entry q2");
	}
	
	private static void transitionActionToQ1FromQ1() {
		System.out.println("to q1 from q1");
	}
	
	private static void transitionActionToQ2FromQ1() {
		System.out.println("to q2 from q1");
	}
	
	private static void transitionActionToQ1FromQ2() {
		System.out.println("to q1 from q2");
	}
	
	private static void transitionActionToQ2FromQ2() {
		System.out.println("to q2 from q2");
	}
	
	private static void exitActionQ1() {
		System.out.println("exit q1");
	}
	
	private static void exitActionQ2() {
		System.out.println("exit q2");
	}
	
}