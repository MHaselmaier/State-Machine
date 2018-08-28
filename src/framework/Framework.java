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

public class Framework {
	private static Map<String, State> generateStates(Document document) {
		Map<String, State> states = new HashMap<String, State>();
		NodeList stateList = document.getElementsByTagName("state");
		for(int tmp = 0; tmp < stateList.getLength(); tmp++) {
			Node nNode = stateList.item(tmp);
			if(nNode.getNodeType() == Node.ELEMENT_NODE) { 
				Element eElement = (Element) nNode;
				String name = eElement.getAttribute("name");
				String entry = eElement.getAttribute("entryaction");
				String exit = eElement.getAttribute("exitaction");
				
				Action entryObject = null;
				Action exitObject = null;
				try {
					if("" != entry) entryObject = (Action)Class.forName(entry).newInstance();
					if("" != exit) exitObject = (Action)Class.forName(exit).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Action of state " + name + " cannot be resolved!");
				}
				
				states.put(name, new State(name, entryObject, exitObject));
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