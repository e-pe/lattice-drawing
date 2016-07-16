package unstable.hassediagram.latticedrawing.core;

import java.io.*;
import java.util.*;

import unstable.hassediagram.latticedrawing.formatters.*;
import unstable.hassediagram.latticedrawing.utils.*;

/**
 * This class represents a lattice as an ordered set  
 * in which every pair of elements has a least upper bound and a greatest lower bound.
 * It was implemented as a doubly linked list referencing to both the next and previous elements in the lattice.
 * 
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 *
 */
public class Lattice implements ILatticeElementDependency {
	private Relation relation;
	private Boolean initialized = false;
	private LatticeElementCollection elements; 
	private LatticeElementStorage storage;
	private LatticeElementLevelCollection levels;
	
	/**
	 * Constructor
	 */
	public Lattice(){
		this.relation = new Relation();
		this.storage = new LatticeElementStorage();
		this.elements = new LatticeElementCollection(this, true);
		this.levels = new LatticeElementLevelCollection();
	}
	
	/**
	 * Gets the storage with all lattice elements.
	 * 
	 * @return The storage of all lattice elements stored as a vector.
	 */
	public LatticeElementStorage getStorage(){
		return this.storage;
	}
	
	/**
	 * Gets the level collection with the lattice elements.
	 * 
	 * @return The collection of all levels describing to which level does a lattice element belong.
	 */
	public LatticeElementLevelCollection getLevels(){
		return this.levels;
	}
		
	/**
	 * Gets the relation defining the lattice structure.
	 * 
	 * @return The relation object describing which relations(relational algebra) exist between lattice elements. 
	 */
	public Relation getRelation(){
		return this.relation;
	}
	
	/**
	 * Gets the collection of elements of the first level existing in the lattice.
	 * 
	 * @return The collection of sub lattice elements.
	 */
	public LatticeElementCollection getElements(){
		return this.elements;
	}
	
	/**
	 * Gets the minimal element of the lattice.
	 
	 * @return The minimal element of the lattice.
	 */
	public LatticeElement getMinimalElement(){
		return this.findMinimalElement();
	}
	
	/**
	 * Gets the maximal element of the lattice.
	 
	 * @return The maximal element of the lattice.
	 */
	public LatticeElement getMaximalElement(){
		return this.getElements().get(0);
	}
	
	/**
	 * Gets the parent element if any one exists. 
	 * This attirbute is required to intialize a lattice element collection in the right way.
	 * Only for internal purposes.  
	 
	 * @return The parent lattice element.
	 */	
	public LatticeElement getParent() {
		return null;
	}
	
	/**
	 * Gets the lattice object.
	 * 
	 * @return The current lattice object.
	 */
	public Lattice getLattice(){
		return this;
	}
		
	/**
	 * Creates a new lattice object by consuming the specified xml format.
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
	 * 
	 * @param xml is data in the specified xml format.
	 * 
	 * @return A new lattice object.
	 */
	public static Lattice load(String xml){
		return LatticeBuilder.createLattice(xml);
	}
	
	/**
	 * Creates a new lattice defined as a boolean matrix stored in a file.
	 * <pre>
	 * {@code
	 * 	<= | 0  1  a  b
	 *	 0 | 1  1  1  1
	 *	 1 | 0  1  0  0
	 *	 a | 0  1  1  0
	 *	 b | 0  1  0  1
	 * }
	 * </pre>
	 * @param filePath is the path to a file containing such a boolean matrix.
	 * 
	 * @return A new lattice object.
	 */
	public static Lattice loadFromFile(String filePath){
		try{
			byte[] buffer = new byte[(int) new File(filePath).length()];
			BufferedInputStream f = new BufferedInputStream(new FileInputStream(filePath));
			f.read(buffer);
			//generates an internal xml and creates a new lattice object
			return Lattice.loadFromText(new String(buffer));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Creates a new lattice defined as a boolean matrix stored in a string.
	 * <pre>
	 * {@code
	 * 	<= | 0  1  a  b
	 *	 0 | 1  1  1  1
	 *	 1 | 0  1  0  0
	 *	 a | 0  1  1  0
	 *	 b | 0  1  0  1
	 * }
	 * </pre> 
	 * @param text is the content containing the specified format.
	 * 
	 * @return A new lattice object.
	 */
	public static Lattice loadFromText(String text){
		return Lattice.load(new LatticeParser(text).parse());
	}
	
	/**
	 * Saves the lattice structure in the specified format.
	 * 
	 * @param format is the type to which the current lattice structure can be converted.
	 * 
	 * @return A representation of the current lattice in the specified format. 
	 */
	public String saveAs(LatticeFormatType format){
		return this.saveAs(LatticeFormatFactory.CreateFormatter(this, format));
	}
	
	/**
	 * Exports the lattice structure to another format.
	 * 
	 * @param formatter is an object implementing ILatticeFormatter that describes an additional format type.
	 * 
	 * @return A representation of the current lattice in the specified format.
	 */
	public String saveAs(ILatticeFormatter formatter){
		return formatter.Format();
	}
			
	/**
	 * Builds a lattice visualization corresponding to a standard hasse diagram.
	 */
	public void adjust(){
		
		if(!this.initialized){
			LatticeElement minElement = this.getMinimalElement();
			//builds a lattice structure from bottom to top beginning with the minimal element
			LatticeBuilder.buildDownTopLattice(this.storage, minElement.getParentElements(), minElement);
			
			this.initialized = true;
		}
		
		//sets the distances for each lattice element beginning with the maximal element and moving to the minimal element
		this.setDistances(this.relation.converse(), this.getMaximalElement(), new ILatticeElementDelegate(){
			public void setDistance(LatticeElement element, Integer distance){
				element.setMaxDistance(distance);
			}
		});
		
		//sets the distances for each lattice element beginning with the minimal element and moving to the maximal element
		this.setDistances(this.relation, this.getMinimalElement(), new ILatticeElementDelegate(){
			public void setDistance(LatticeElement element, Integer distance){
				element.setMinDistance(distance);
			}
		});
		
		//creates the level presentation from left top element to the right bottom element 
		this.levels = this.createPositionLevels();
		
		//beautifies the the lattice visualization
		for(int i = 0; i < 20; i++){
			//sets different positions for elements with the same x coordinate
			this.setPositions();
		
			//removes collisions
			this.removeCollisions(i);
			
			//sets different positions for elements with the same x coordinate	
			this.setPositions();
		}
	}

	/**
	 * Calculates distances to each lattice element from the maximal element and the minimal element.
	 * <pre>
	 * {@code
	 * 		(0)	    a	       <- the maximal element      1.Iteration: P = {a}, 	Pnew = {b, c}	
	 * 			   / \									   2.Iteration: P = {b, c}, Pnew = {d, e}	
	 *      (1)   b   c   (1)                              3.Iteration: P = {d, e}, Pnew = {f, g}
	 *            |   |									   4.Iteration: P = {f, g}, Pnew = {h, i}		
	 *      (2)   d   e   (2)                              5.Iteration: P = {h, i}, Pnew = {i}
	 *            |   |									   6.Iteration: P = {i},    Pnew = {}	
	 *      (3)   f   g   (3)
	 *            |   |
	 *      (4)   h   |
	 *             \ /
	 *      (5)     i     (4) <- will be replaced by (5) as the longest distance
	 *
	 * 	Pseudo Code:
	 *	
	 *	set P = { x:= maximal element}
	 *	
	 *	init Relation
	 *  
	 *  x.distance = 0
	 *  
	 *  i = 0
	 *  
	 *   while ( P != 0 and Relation != 0){
	 *   	i++;
	 *      P = x.getAllValues   where x is element of P 
	 * 		x.drop
	 * 
	 *   	for all p element of P:  p.distance = i
	 *   }
	 * }
	 * </pre>
	 * 
	 * @param def is the relation object describing the current relations between lattice elements.
	 * @param startElement is a lattice element  
	 */
	private void setDistances(Relation def, LatticeElement startElement, ILatticeElementDelegate setDistance){
		int path = 0;
		
		Relation current = new Relation();
		
		HashSet<String> keys = new HashSet<String>();
		keys.add(startElement.getName());
		
		def.copyTo(keys, current);
		keys.clear();		
		path++;
		
		while(!current.isEmpty()){
			Set<RelationElement> elements = current.image();
		
			for(RelationElement element : elements){
							
				LatticeElement latticeElement = this.storage.find(element.getName());			
				setDistance.setDistance(latticeElement, path);
				
				keys.add(element.getName());
				
				current.remove(element.getKey(), element.getName());	
			}
			
			if(current.isEmpty()){
				def.copyTo(keys, current);
				keys.clear();
				path++;
			}
		}
	}
	
	/**
	 * Sets the position of each lattice element if two or more elements have the same position(are overlapping).
	 * <pre>
	 * {@code
     * 				o                                o
 	 *             / \                             /   \
	 *            /   \                           /     \
	 *           o     o            ->           o       o
	 *          / \   / \                      / |       | \
	 *         /   \ /   \                    /  |       |  \
	 *        o     oo    o                  o   o       o   o
	 * }
	 * </pre>
	 */
	private void setPositions(){
		Float minLength = 1f;
		LatticeElementLevelCollection levels = this.getLevels();
		
		for(LatticeElementLevel level : levels){
			for(int i = 0; i < level.size(); i++){
				LatticeElement e1 = level.get(i);
								
				for(int j = 0; j < level.size(); j++){
					LatticeElement e2 = level.get(j);
					
					LatticePoint e1Point = e1.getPoint();
					LatticePoint e2Point = e2.getPoint();
					Float length = Math.abs(e2Point.getX() - e1Point.getX());
					
					if(length < minLength){
						Float factor = (minLength - length) / 2;
						
						e1Point.setX(e1Point.getX() - factor);
						e2Point.setX(e2Point.getX() + factor);
					}
				}
			}
		}
	}
	
	/**
	 * Removes collisions if an element is located very closely to a vertice.
	 * <pre>
	 *  {@code
	 *  		o         o 
 	 *          |         | \
	 *          |         |  \
	 *          o   ->    |   o
	 *          |         |  /
	 *          |         | /
	 *          o         o
	 *  }
	 * </pre> 
	 * @param index is a coefficient indicating how far should be moved a lattice element.
	 */
	private void removeCollisions(Integer index){
		Float dis = 0.3f;		
		LatticeElementLevelCollection levels = this.getLevels();
		for(int i = 1; i < levels.size(); i++){
			LatticeElementLevel level = levels.get(i);
			
			for(LatticeElement e : level){
				for(LatticeElement p : e.getParentElements()){
					LatticeElementLevel pLevel = levels.find(p);
					
					//creates a delegate for looking for all the elements located between two levels
					LatticeElementLevelCollection range = levels.getRange(level, pLevel, new ILatticeElementLevelCriteria(){
						public Boolean exists(LatticeElement bottom, LatticeElement top, LatticeElement elem){
							if( elem.getPoint().getY() > bottom.getPoint().getY() && elem.getPoint().getY() < top.getPoint().getY())
								return true;
							return false;
						}
					});
					
					for(LatticeElementLevel current : range){						
						if(current != pLevel && current != levels.get(0)){
							
							for(LatticeElement c : current){
								Float disC = Triangle.getDistance(p.getPoint().getX(), p.getPoint().getY(), 
										e.getPoint().getX(), e.getPoint().getY());
									
								Float disA = Triangle.getDistance(p.getPoint().getX(), p.getPoint().getY(),
										c.getPoint().getX(), c.getPoint().getY());
								
								Float disB = Triangle.getDistance(c.getPoint().getX(), c.getPoint().getY(), 
										e.getPoint().getX(), e.getPoint().getY());
								
								Float disH = Triangle.getCHeight(disA, disB, disC);
								
								if(disH < dis){
										Integer sign = c.getPoint().getX() > 0f ? +1 : -1;
										Integer cParentSize = c.getParentElements().size();
										Integer eParentSize = e.getParentElements().size();
										Integer pParentSize = p.getParentElements().size();
										Integer minParentSize = Math.min(Math.min(cParentSize, eParentSize), pParentSize);
										
										if(level.size() == 1 && minParentSize == eParentSize)
											e.getPoint().setX(e.getPoint().getX() + (4 * dis) * sign + index);
										else if((current.size() == 1 && minParentSize == cParentSize))
											c.getPoint().setX(e.getPoint().getX() + (4 * dis)* sign + index);
										else if(minParentSize == pParentSize && pParentSize != 0)									
											p.getPoint().setX(p.getPoint().getX() + (4 * dis) * sign + index);
										else if(minParentSize == pParentSize && pParentSize == 0)
											e.getPoint().setX(e.getPoint().getX() + (4 * dis) * sign + index);
										else
											p.getPoint().setX(p.getPoint().getX() + (4 * dis) * sign + index);
								}
							}
						}
					}
				}
			}
		}		
	}
	
	/**
	 * Finds a minimal element.
	 * 
	 * @return The minimal element.
	 */
	private LatticeElement findMinimalElement(){
		for(LatticeElement e : this.storage){
			if(e.getMinimal())
				return e;
		}
			
		return null;
	}
	
	/**
	 * Creates a new level collection by storing each lattice element in the specified level. 
	 * 
	 * @return a new lattice element level collection.
	 */
	private LatticeElementLevelCollection createLevels(){
		LatticeElementLevelCollection levels = new LatticeElementLevelCollection();
		
		//creates the first level with the maximal element 
		LatticeElementLevel level = new LatticeElementLevel();
		level.addRange(this.elements);
		levels.add(level);
		
		while(!level.isEmpty()){
			LatticeElementLevel current = new LatticeElementLevel();
			
			for(LatticeElement e : level)
				 current.addRange(e.getElements());
			
			if(!current.isEmpty())
				levels.add(current);
			
			level = current;
		}
		
		return levels;
	}
	
	/**
	 * Creates a new level collection by the y coordinate.
	 * 
	 * @return A new lattice element level collection.
	 */
	private LatticeElementLevelCollection createPositionLevels(){
		Integer distance = this.getMaximalElement().getLevel();		
		LatticeElementLevelCollection levels = new LatticeElementLevelCollection();
		
		while(distance > -1){
				
			LatticeElementLevel level = new LatticeElementLevel();			
			for(LatticeElement e : this.storage){
				
				if(e.getLevel() == distance){
					level.add(e);
				}
			}
			
			if(!level.isEmpty())
				levels.add(level);
			
			distance--;
		}
		
		return levels;
	}
	
	/**
	 * Adds an element to the storage.
	 */
	void addToStorage(LatticeElement element){
		if(!this.storage.contains(element)){
			this.storage.add(element);
			
			this.initialized = false;
		}
	}
}
