package unstable.hassediagram.latticedrawing.utils;

import java.util.*;
import java.util.Map.*;

/**
 * This class represents a mathematical relation between objects.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 *
 */
public class Relation {
	private RelationMap map;
	
	/**
	 * Constructor
	 */
	public Relation(){
		this.map = new RelationMap();
	}
	
	/**
	 * Multiplies two relations.
	 * @param relation is the second operand
	 * 
	 * @return A new relation. 
	 */
	public Relation multiplication(Relation relation){
		Relation newRelation = new Relation();
		
		//multiplies two relations e.g S;S
		for(Entry<String, Set<String>> e : this.getMap().entrySet()){
			for(String vertex1 : e.getValue()){
				Set<String> dependencies = relation.getMap().get(vertex1);
				
				if(dependencies != null){
					for (String vertex2 : dependencies)
						if (e.getValue().contains(vertex2)) 
							newRelation.getMap().add(e.getKey(), vertex2);
				}
			}
		}
		
		return newRelation;
	}
	
	/**
	 * Gets the difference between two relations.
	 * 
	 * @param relation is the second operand.
	 * @return A new relation.
	 */
	public Relation difference(Relation relation){
		Relation newRelation = new Relation();
		
		for(Entry<String, Set<String>> e : this.getMap().entrySet()){
			for(String vertex1 : e.getValue()){
				Set<String> dependencies = relation.getMap().get(e.getKey());
				
				if(dependencies == null || !dependencies.contains(vertex1))
					newRelation.getMap().add(e.getKey(), vertex1);

			}
		}
		
		return newRelation;
	}
	
	/**
	 * Unites two relations.
	 * 
	 * @param relation is the second operand.
	 * @return A new relation.
	 */
	public Relation union(Relation relation){
		return null;
	}
	
    /**
     * Intersects two relations.
     * 
     * @param relation is the second operand.
     * @return A new relation.
     */
	public Relation intersection(Relation relation){
		return null;
	}
	
	/**
	 * Domain operation is defined as {(x,x)| exists y: xRy}
	 * 
	 * @return A new relation.
	 */
	public Relation domain(){
		Relation newRelation = new Relation();
		
		for(Entry<String, Set<String>> e : this.getMap().entrySet()){
			newRelation.getMap().add(e.getKey(), e.getKey());
		}
		
		return newRelation;
	}
	
	/**
	 * Codomain operation is defined as {(x,x)| exists y: yRx}
	 * 
	 * @return A new relation.
	 */
	public Relation codomain(){
		Relation newRelation = new Relation();
		
		for(Entry<String, Set<String>> e : this.getMap().entrySet()){
			for(String vertex1 : e.getValue()){
				newRelation.getMap().add(vertex1, vertex1);
			}
		}
		
		return newRelation;
	}
	
	/**
	 * Removes all identity tuples.
	 * 
	 * @return A new relation.
	 */
	public Relation minusIdentity(){
		Relation newRelation = new Relation();
		
		for(Entry<String, Set<String>> e : this.getMap().entrySet()){
			for(String vertex1 : e.getValue()){
				if(!vertex1.equals(e.getKey()))
					newRelation.getMap().add(e.getKey(), vertex1);
			}
		}
		
		return newRelation;
	}
	
	/**
	 * Converse operation is defined as {(y, x)| yRx}
	 * 
	 * @return A new relation.
	 */
	public Relation converse(){
		Relation newRelation = new Relation();
		
		for(Entry<String, Set<String>> e : this.getMap().entrySet()){
			for(String vertex1 : e.getValue()){
				newRelation.getMap().add(vertex1, e.getKey());
			}
		}
		
		return newRelation;
	}
	
	/**
	 * Image operation is defined as {a| exists b: bRa}
	 * <pre>
	 * {@code
	 * 		R = {(a, b), (a, c), (b, d), (c, e), (d, i), (e, i)}
	 * 		
	 * 		image(R) = {b, c, d, e, i}
	 * }
	 * </pre>
	 * @return A set of relation elements.
	 */
	public Set<RelationElement> image(){
		Set<RelationElement> elements = new HashSet<RelationElement>();
		
		for(Entry<String, Set<String>> e : this.getMap().entrySet()){
			for(String vertex1 : e.getValue()){
				if(!elements.contains(vertex1))
					elements.add(new RelationElement(vertex1, e));
			}
		}
		
		return elements;
	}
	
	/**
	 * Preimage operation is defined as {a|exists b: aRb}
      * <pre>
	 * {@code
	 * 		R = {(a, b), (a, c), (b, d), (c, e), (d, i), (e, i)}
	 * 		
	 * 		preimage(R) = {a, b, c, d, e}
	 * }
	 * </pre>
     * @return A set of relation elements.
	 */
	public Set<RelationElement> preimage(){
		Set<RelationElement> elements = new HashSet<RelationElement>();
		
		for(Entry<String, Set<String>> e : this.getMap().entrySet()){		
			if(!elements.contains(e.getKey()))
				elements.add(new RelationElement(e.getKey(), e));
		}
		
		return elements;
	}
	
	/**
	 * Returns true if the relation does not contain any tuples.
	 * 
	 * @return True if the relation does not contain any tuples.
	 */
	public Boolean isEmpty(){
		return this.map.isEmpty();
	}
	
	/**
	 * Gets the map containing dependencies between elements.
	 * 
	 * @return The map containing dependencies between elements.
	 */
	public RelationMap getMap(){
		return this.map;
	}
	
	/**
	 * Copies parts of the current relation to a new relation object. 
	 * 
	 * @param keys are the names of the relation elements.
	 * @param relation is a relatio object to which tupes containing the specified keys will be copied. 
	 */
	public void copyTo(Set<String> keys, Relation relation){
		relation.getMap().clear();
		
		for(String key : keys){
			Entry<String, Set<String>> e = this.map.getEntry(key);
			
			if(e != null){
				for(String value : e.getValue()){
					relation.getMap().add(key, value);
				}
			}
		}
	}
	
	/**
	 * Removes a relation described by two vertices.
	 * 
	 * @param vertex1 is the first vertex
	 * @param vertex2 is the second vertex
	 */
	public void remove(String vertex1, String vertex2){
		this.map.remove(vertex1, vertex2);
	}
	
	/**
	 * Creates a new relation object.
	 * 
	 * @param matrix is a boolean matrix describing relations between elements.
	 *  
	 * @return A new relation object.
	 */
	public static Relation parse(RelationMatrix matrix){
		Relation relation = new Relation();
		
		//creates all defined dependencies in the relation matrix
		for(int i = 0; i < matrix.getRowDimension(); i++){
			for(int j = 0; j < matrix.getColumnDimension(); j++){
				if(matrix.getEntry(i, j))
					relation.getMap().add(Character.toString((char)(97 + i)), 
							Character.toString((char)(97 + j)));
			}
		}
		
		return relation;
	}
}
