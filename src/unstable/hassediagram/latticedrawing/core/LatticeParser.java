package unstable.hassediagram.latticedrawing.core;

import java.util.regex.*;
import unstable.hassediagram.latticedrawing.utils.*;

/**
 * This class parses a simple lattice structure to a rich internal xml format. 
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 *
 */
class LatticeParser {
	private String structure;
	private String interpretationRegex = "interpretation\\(\\s*([0-9]|[1-9][0-9]),\\s*";
	private String markingRegex = "relation\\(test\\(_\\),\\s*\\[([01,\\s]*)\\]\\)";
	private String relationRegex = "relation\\(\\<=\\(_,_\\),\\s*\\[([01,\\s]*)\\]\\)"; 
	
	
	/**
	 * Constructor
	 * @param structure is the mathematical format used by the specified mathematical generator. 
	 */
	public LatticeParser(String structure){
		this.structure = structure;
	}
	
	/**
	 * Generates an internal xml format. 
	 * <pre>
	 * {@code
	 * <lattice xmlns="http://www.example.org/Lattice">		 
	 *		<element name="a" minimal="false" maximal="false" markable="false">
	 *			<dependent from="1" />
	 *		</element>
	 *		<element name="b" minimal="false" maximal="false" markable="false">
	 *			<dependent from="1" />
	 *		</element>
	 *		<element name="1" minimal="false" maximal="true" markable="false" />
	 *		<element name="0" minimal="true" maximal="false" markable="false">
	 *			<dependent from="a" />
	 *			<dependent from="b" />
	 *			<dependent from="1" />
	 *		</element>
	 * </lattice>
	 * }
	 * </pre>
	 * @return The internal xml format. 
	 */
	public String parse(){
		Integer size = this.getLatticeSize();
		
		RelationMatrix matrix = new RelationMatrix(
				this.getLatticeStructure(), size, size);
		
		RelationMatrix markingMatrix = new RelationMatrix(
				this.getLatticeMarkingStructure(), 1, size);
				
		Relation relation = this.createHasseRelation(matrix);
				
		//creates the lattice xml tag
		String xml = "<lattice>";	
		if(!matrix.isEmpty()){
			for(int i=0; i<size; i++)
				//gets the position of the current row and creates a new lattice element tag
				xml += this.createLatticeElementTag(i, matrix, markingMatrix, relation);
		}		
		xml += "</lattice>";
		
		return xml;
	}
	
	/**
	 * Gets the count of lattice elements.
	 * 
	 * @return Size of the partitial order.   
	 */
	private Integer getLatticeSize(){
		//compiles regex
		Pattern pattern = Pattern.compile(this.interpretationRegex);
		//gets the matcher for the target string
		Matcher matcher = pattern.matcher(this.structure);
		
		//finds all the matches
		if(matcher.find())
			return Integer.parseInt(matcher.group(1));
				
		return 0;
	}
	
	/**
	 * Gets the definition structure of the current lattice.
	 * 
	 * @return A string containing 0 or 1 for describing the partitial order. 
	 */
	private String getLatticeStructure(){
		//compiles regex
		Pattern pattern = Pattern.compile(this.relationRegex);
		//gets the matcher for the target string
		Matcher matcher = pattern.matcher(this.structure);
		
		//finds all the matches
		if(matcher.find())
			return matcher.group(1)
				.replaceAll(",", "")
				.replaceAll(" ", "")
				.replaceAll("\n", "")
				.replaceAll("\\b\\s{2,}\\b", "").trim();
								   

		return "";
	}
	
	/**
	 * Gets the marking structure of the current lattice.
	 * 
	 * @return Structure consisting of 0 or 1 describing which element should be marked.
	 */
	private String getLatticeMarkingStructure(){
		//compiles regex
		Pattern pattern = Pattern.compile(this.markingRegex);
		//gets the matcher for the target string
		Matcher matcher = pattern.matcher(this.structure);
		
		//finds all the matches
		if(matcher.find())
			return matcher.group(1)
				.replaceAll(",", "")
				.replaceAll(" ", "")
				.replaceAll("\n", "")
				.replaceAll("\\b\\s{2,}\\b", "").trim();
		
		return "";
	}
	
	/**
	 * Creates a hasse relation to remove the transitive closure by using following steps.
	 * <pre>
	 * {@code
	 * 		|a b c d
	 *    a |1 1 1 1
	 *    b |0 1 0 1
	 *    c |0 0 1 1
	 *    d |0 0 0 1 
	 *   
	 *    R = [(a, a), (a, b), (a, c), (a, d), (b, b), (b, d), (c, c), (c, d), (d, d)]
	 *    
	 *    S = (R - I) = [(a, b), (a, c), (a, d), (b, d), (c, d)]
	 *    
	 *    T = S;S = [(a, d)]
	 *    
	 *    X = S - T = S - (S;S) = [(a, b), (a, c), (b, d), (c, d)] 
	 *     
	 * }
	 * </pre>
	 * @param matrix contains the partitial order.
	 * @return Relation matrix without transitive closure.
	 */
	private Relation createHasseRelation(RelationMatrix matrix){
		Relation r = Relation.parse(matrix);
		Relation s = r.minusIdentity();
		
		return s.difference(s.multiplication(s));
	}
	
	/**
	 * Generates a lattice element tag described in the lattice.xml file.
	 * <pre>
	 * {@code
	 * 	<element name="a" minimal="false" maximal="false" markable="false">
     *		<dependent from="1" />
	 *	</element>
	 * }
	 * </pre>
	 * @param elementIndex is the index of the element.
	 * @param elementDef is the definition for the element. 
	 * @param elementMarking is the descriptin of elements which should be marked.
	 * @param relation contains dependencies between elements without transitive closure.
	 * @return A new lattice element tag.
	 */
	private String createLatticeElementTag(Integer elementIndex, RelationMatrix elementDef, RelationMatrix elementMarking, Relation relation){			
		String xml = "";		
		Boolean[] definition = elementDef.getMatrix()[elementIndex];
		
		xml += "<element name=\""+ (char)(97 + elementIndex) +"\" " +
				"minimal=\""+ this.isMinimalElement(definition).toString() +"\" " +
						"maximal=\""+ this.isMaximalElement(definition).toString() +"\" " +
								"markable=\""+ elementMarking.getEntry(0, elementIndex).toString() +"\">";
		
		xml += this.createLatticeElementDependentTag(elementIndex, definition, relation);
		
		xml +="</element>";
		
		return xml;
	}
	
	/**
	 * Generates a lattice element dependent tag described in the lattice.xml file.
	 * <pre>
	 * {@code
	 *	<dependent from="a" />
	 *	<dependent from="b" />
	 *	<dependent from="1" />
	 * }
	 * </pre>
	 * @param elementIndex is the index of the element.
	 * @param elementDef is the definition for the element. 
	 * @param relation contains dependencies between elements without transitive closure 
	 * @return Xml dependent tags. 
	 */
	private String createLatticeElementDependentTag(Integer elementIndex, Boolean[] elementDef, Relation relation){
		String xml = "";
		
		for(int i=0; i < elementDef.length; i++){
			if(elementDef[i]){
				char src = (char)(elementIndex+97);
				char dst = (char)(i + 97);
						
				if(relation.getMap()
						.contains(Character.toString(src), Character.toString(dst))){
						
					xml += "<dependent from=\""+ dst +"\"/>";
				}
			}
		}
		
		return xml;
	}
	
	/**
	 * Returns true whether a row represents a minimal element.
	 * 
	 * @param elementDef is the definition row of an element.
	 * 
	 * @return True if the element described by the row is minimal.
	 */
	private Boolean isMinimalElement(Boolean[] elementDef){
		int count = 0;
		
		for(int i=0; i < elementDef.length; i++){
			if(elementDef[i])
				count++;
		}
		
		return count == elementDef.length ? true : false;
	}
	
	/**
	 * Returns true whether a row represents a maximal element.
	 * 
	 * @param elementDef is the definition row of an element.
	 * 
	 * @return True if the element described by the row is minimal.
	 */
	private Boolean isMaximalElement(Boolean[] elementDef){
		int count = 0;
		
		for(int i=0; i < elementDef.length; i++){
			if(elementDef[i])
				count++;
		}
		
		return count == 1 ? true : false;
	}
}
