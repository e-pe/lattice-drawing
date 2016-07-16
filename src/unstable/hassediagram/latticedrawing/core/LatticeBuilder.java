package unstable.hassediagram.latticedrawing.core;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

/**
 * This class creates a new lattice object.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 *
 */
class LatticeBuilder {
	private static String fromTag = "from";
	private static String nameTag = "name";
	private static String minimalTag = "minimal";
	private static String maximalTag = "maximal";
	private static String markableTag = "markable";
	
	/**
	 * Creates a new lattice object by consuming the internal xml format.
	 * 
	 * @param xml is the internal xml format.
	 * @return A new lattice object.
	 */	
	public static Lattice createLattice(String xml){
		LatticeElementStorage srcElements = restoreLatticeElements(xml);
		
		if(srcElements != null){
			Lattice lattice = new Lattice();
			
			LatticeElement maxElement = null;
			for(int i = 0; i < srcElements.size(); i++){
				LatticeElement element = srcElements.get(i);
				
				if(element.getMaximal()){
					maxElement = element;
					break;
				}
			}
			
			//builds a lattice structure from top to bottom beginning with the maximal element
			LatticeBuilder.buildTopDownLattice(srcElements, lattice.getElements(), maxElement);
						
			//ajusts all lattice element positions
			lattice.adjust();
			
			return lattice;
		}
		
		return null;
	}
	
	/**
	  * Restores lattice elements from an internal xml structure.
	 * 
	 * @param xml is an internal xml format required for creating lattice elements on the fly. 
	 * @return A storage with all defined elements in the xml.
	 */
	private static LatticeElementStorage restoreLatticeElements(String xml){
		try {
			
			//creates a temporary collection for all lattice elements
			LatticeElementStorage elements = new LatticeElementStorage();
			//defines classes for reading xml 
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new InputSource(new StringReader(xml)));
			//normalizes the text representation
            doc.getDocumentElement().normalize();
            
            NodeList elementsTag = doc.getElementsByTagName("element");
            
            for(int i=0; i < elementsTag.getLength(); i++){
            		Element e = (Element)elementsTag.item(i);
    		
            		//creates a new lattice element
            		LatticeElement element = new LatticeElement();
            		element.setName(e.getAttribute(nameTag));
            		element.setMinimal(Boolean.parseBoolean(e.getAttribute(minimalTag)));
            		element.setMaximal(Boolean.parseBoolean(e.getAttribute(maximalTag)));
            		element.setMarkable(Boolean.parseBoolean(e.getAttribute(markableTag)));
            		
            		//creates a new collection for storing dependent lattice elements
            		ArrayList<String> dependents = new ArrayList<String>();
            		NodeList dependentsTag = e.getElementsByTagName("dependent");
            		
            		for(int j=0;j < dependentsTag.getLength(); j++){
            			Element d = (Element)dependentsTag.item(j); 
            			dependents.add(d.getAttribute(fromTag));
            		}

            		element.setDependents(dependents);
            		
            		elements.add(element);
            	}
            
            return elements;

		}catch(Exception e){
			
		}
		
		return null;
	}
	
	/**
	 * Builds a TopDown lattice structure(linked list).Each parent knows its children.
	 * 
	 * @param srcElements is a storage containing all defined lattice elements.
	 * @param dstElements is a collection of the next child level.
	 * @param startElement is first element of the list direction (top -> bottom).
	 */
	public static void buildTopDownLattice(LatticeElementStorage srcElements, 
		LatticeElementCollection dstElements, LatticeElement startElement){
			
					
		dstElements.add(startElement);
		
		for(int i = 0; i < srcElements.size(); i++){			
			LatticeElement element = srcElements.get(i);

			//gets a temporal array list with lattice element dependents 
			ArrayList<String> dependents = element.getDependents();
			for(int j = 0; j < dependents.size(); j++){
			    String name = dependents.get(j);
				
			    if(name.equals(startElement.getName()))
			    	buildTopDownLattice(srcElements, startElement.getElements(), element);	
			}
		}
	}
	
	/**
	 * Builds a DownTop lattice structure. Each child knows its parents.
	 * 
	 * @param srcElements is a storage containing all defined lattice elements.
	 * @param dstElements is a collection of the next parent level.
	 * @param startElement is the first element of the linked list direction (bottom -> top)
	 */
	public static void buildDownTopLattice(LatticeElementStorage srcElements, 
		LatticeElementCollection dstElements, LatticeElement startElement){
		
		if(!startElement.getMinimal() && !dstElements.contains(startElement))
			dstElements.add(startElement);
					
		//gets a temporal array list with lattice element dependents 
		ArrayList<String> dependents = startElement.getDependents();
		for(int j = 0; j < dependents.size(); j++){
			String name = dependents.get(j);
			    
			buildDownTopLattice(srcElements, startElement.getParentElements(), srcElements.find(name));
		}
	}
}
