package framework;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExtendedFramework {
	
	private static State generateFromNode(Node node, Map<String, State> states) {
		State state = null;
		
		switch(node.getNodeName()) {
		case "state":
			state = generateState((Element)node);
			states.put(state.getName(), state);
			break;
		case "composed":
			state = generateComposedState((Element)node, states);
			states.put(state.getName(), state);
			break;
		case "parallel":
			state = generateParallelState((Element)node, states);
			states.put(state.getName(), state);
			break;
		}
		
		return state;
	}
	
	private static State generateState(Element element) {
		String name = element.getAttribute("name");
		String entry = element.getAttribute("entryaction");
		String exit = element.getAttribute("exitaction");
		
		Action entryAction = null;
		Action exitAction = null;
		try {
			if("" != entry) entryAction = (Action)Class.forName(entry).newInstance();
			if("" != exit) exitAction = (Action)Class.forName(exit).newInstance();
		} catch (Exception e) {
			System.err.println("Action of state " + name + " cannot be resolved!");
			return null;
		}
		
		return new State(name, entryAction, exitAction);
	}
	
	private static ComposedState generateComposedState(Element element, Map<String, State> states) {
		String name = element.getAttribute("name");
		String entry = element.getAttribute("entryaction");
		String exit = element.getAttribute("exitaction");
		String first = element.getAttribute("first");
		
		Action entryAction = null;
		Action exitAction = null;
		try {
			if("" != entry) entryAction = (Action)Class.forName(entry).newInstance();
			if("" != exit) exitAction = (Action)Class.forName(exit).newInstance();
		} catch (Exception e) {
			System.err.println("Action of state " + name + " cannot be resolved!");
			return null;
		}
		
		NodeList childs = element.getChildNodes();
		Map<String, State> subStates = new HashMap<String, State>();
		for(int i = 0; i < childs.getLength(); ++i) {
			if(childs.item(i).getNodeType() == Node.ELEMENT_NODE) {			
				State state = generateFromNode(childs.item(i), states);
				if(null == state) {
					System.err.println("Substate of " + name + " cannot be resolved!");
					return null;
				}
				
				subStates.put(state.getName(), state);
			}
		}
		
		State firstState = subStates.get(first);
		if(null == firstState) {
			System.err.println("First state of " + name + " cannot be resolved!");
			return null;
		}
		
		return new ComposedState(name, entryAction, exitAction, firstState, subStates);
	}
	
	private static ParallelState generateParallelState(Element element, Map<String, State> states) {
		String name = element.getAttribute("name");
		String entry = element.getAttribute("entryaction");
		String exit = element.getAttribute("exitaction");
		
		Action entryAction = null;
		Action exitAction = null;
		try {
			if("" != entry) entryAction = (Action)Class.forName(entry).newInstance();
			if("" != exit) exitAction = (Action)Class.forName(exit).newInstance();
		} catch (Exception e) {
			System.err.println("Action of state " + name + " cannot be resolved!");
			return null;
		}
		
		NodeList childs = element.getChildNodes();
		List<State> subStates = new ArrayList<State>();
		for(int i = 0; i < childs.getLength(); ++i) {
			if(childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
				State state = generateFromNode(childs.item(i), states);
				if(null == state) {
					System.err.println("Substate of " + name + " cannot be resolved!");
					return null;
				}
				
				subStates.add(state);
			}
		}
		
		return new ParallelState(name, entryAction, exitAction, subStates);
	}
	
	private static Map<String, State> generateStates(Document document) {
		Map<String, State> states = new HashMap<String, State>();
		
		NodeList stateList = document.getElementsByTagName("states").item(0).getChildNodes();
		for(int i = 0; i < stateList.getLength(); ++i) {
			if(stateList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				generateFromNode(stateList.item(i), states);
			}
		}
		
		return states;
	}
	
	private static void generateTransitions(Document document, Map<String, State> states) {
		NodeList transitionList = document.getElementsByTagName("transition");
		for(int tmp = 0; tmp < transitionList.getLength(); tmp++) {
			Node nNode = transitionList.item(tmp);
			if(nNode.getNodeType() == Node.ELEMENT_NODE) { 
				Element eElement = (Element) nNode;
				String source = eElement.getAttribute("source");
				String target = eElement.getAttribute("target");
				String event = eElement.getAttribute("event");
				String action = eElement.getAttribute("action");
				
				Action actionObject = null;
				try {
					if("" != action) actionObject = (Action)Class.forName(action).newInstance();
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("Action cannot be resolved!");
				}
				
				states.get(source).addTransition(new Event<String>(event), new Transition(states.get(target), actionObject));
			}
		}
	}
	
	public static Context generateContext(Document document) {
		Map<String, State> states = generateStates(document);
		generateTransitions(document, states);
		
		Element start = (Element) document.getElementsByTagName("startState").item(0);
		State startState = states.get(start.getAttribute("name"));
		
		List<State> endStates = new ArrayList<State>();
		NodeList endStatesList = document.getElementsByTagName("endState");
		for(int tmp = 0; tmp < endStatesList.getLength(); tmp++) {
			Node nNode = endStatesList.item(tmp);
			if(nNode.getNodeType() == Node.ELEMENT_NODE) { 
				Element eElement = (Element) nNode;
				String name = eElement.getAttribute("name");
				
				endStates.add(states.get(name));
			}
		}
		
		return new Context(startState, endStates);
	}
	
	public static Document readXML(String xml, String xsd) {
		Document document = null;
		try {
			DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = parser.parse(new File(xml));
			document.getDocumentElement().normalize();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load document.");
		}
		
		// Validate XML-File
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Source schemaFile = new StreamSource(new File(xsd));
			Schema schema = factory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			validator.validate(new DOMSource(document));
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed to validate document.");
		}
		
		return document;
	}
}