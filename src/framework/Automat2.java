package framework;

public class Automat2 {
	
	public static boolean run(Event<String>[] someInput) {
		Context fsm = Framework.generateContext(Framework.readXML("res/Automat2.xml", "res/AutomatSchema.xsd"));
		
		for(int i = 0; i < someInput.length; i++) {
			fsm.process(someInput[i]);
		}
		
		return fsm.isAtFinalState();
	}
	
}