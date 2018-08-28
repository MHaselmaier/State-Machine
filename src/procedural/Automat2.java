package procedural;

public class Automat2 {
	
	public static boolean run(final String anInput) {
		entryActionS();
		String state = "s";
		
		for(int i = 0; i < anInput.length(); i++) {
			switch(state) {
			case "s":
				exitActionS();
				switch(anInput.charAt(i)) {
				case 'a':
					transitionActionToQ1FromS();
					entryActionQ1();
					state = "q1";
					break;
				case 'b':
					transitionActionToR1FromS();
					entryActionR1();
					state = "r1";
					break;
				}
				break;
			case "q1":
				exitActionQ1();
				switch(anInput.charAt(i)) {
				case 'a':
					transitionActionToQ1FromQ1();
					entryActionQ1();
					state = "q1";
					break;
				case 'b':
					transitionActionToQ2FromQ1();
					entryActionQ2();
					state = "q2";
					break;
				}
				break;
			case "q2":
				exitActionQ2();
				switch(anInput.charAt(i)) {
				case 'a':
					transitionActionToQ1FromQ2();
					entryActionQ1();
					state = "q1";
					break;
				case 'b':
					transitionActionToQ2FromQ2();
					entryActionQ2();
					state = "q2";
					break;
				}
				break;
			case "r1":
				exitActionR1();
				switch(anInput.charAt(i)) {
				case 'a':
					transitionActionToR2FromR1();
					entryActionR2();
					state = "r2";
					break;
				case 'b':
					transitionActionToR1FromR1();
					entryActionR1();
					state = "r1";
					break;
				}
				break;
			case "r2":
				exitActionR2();
				switch(anInput.charAt(i)) {
				case 'a':
					transitionActionToR2FromR2();
					entryActionR2();
					state = "r2";
					break;
				case 'b':
					transitionActionToR1FromR2();
					entryActionR1();
					state = "r1";
					break;
				}
				break;
			}
		}
		
		if("q1" != state && "r1" != state) {
			return false;
		}
		return true;
	}
	
	public static void entryActionS() {
		System.out.println("entry s");
	}
	
	private static void entryActionQ1() {
		System.out.println("entry q1");
	}
	
	private static void entryActionQ2() {
		System.out.println("entry q2");
	}
	
	private static void entryActionR1() {
		System.out.println("entry r1");
	}
	
	private static void entryActionR2() {
		System.out.println("entry r2");
	}
	
	private static void transitionActionToQ1FromS() {
		System.out.println("to q1 from s");
	}
	
	private static void transitionActionToR1FromS() {
		System.out.println("to r1 from s");
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
	
	private static void transitionActionToR1FromR1() {
		System.out.println("to r1 from r1");
	}
	
	private static void transitionActionToR2FromR1() {
		System.out.println("to r2 from r1");
	}
	
	private static void transitionActionToR1FromR2() {
		System.out.println("to r1 from r2");
	}
	
	private static void transitionActionToR2FromR2() {
		System.out.println("to r2 from r2");
	}
	
	public static void exitActionS() {
		System.out.println("exit s");
	}
	
	private static void exitActionQ1() {
		System.out.println("exit q1");
	}
	
	private static void exitActionQ2() {
		System.out.println("exit q2");
	}
	
	private static void exitActionR1() {
		System.out.println("exit r1");
	}
	
	private static void exitActionR2() {
		System.out.println("exit r2");
	}
	
}