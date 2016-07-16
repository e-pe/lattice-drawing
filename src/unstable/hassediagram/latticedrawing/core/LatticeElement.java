package unstable.hassediagram.latticedrawing.core;

import java.util.*;

import unstable.hassediagram.latticedrawing.descriptors.ILatticePointDescriptor;
import unstable.hassediagram.latticedrawing.descriptors.LatticePointDescriptorFactory;

/**
 * This class represents an element of a lattice.
 * Each lattice element has a reference to both the child and parent lattice element collections. 
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
public class LatticeElement implements ILatticeElementDependency {
	private String name;
	private Boolean minimal;
	private Boolean maximal;
	private Boolean markable;
	private Integer minDistance = 0;
	private Integer maxDistance = 0;
	
	private Lattice lattice;
	private LatticePoint point;
	private ArrayList<String> dependents;
	private LatticeElementCollection elements;
	private LatticeElementCollection parentElements;
	
	/**
	 * Constructor
	 */
	public LatticeElement(){
		this.elements = new LatticeElementCollection(this, true);
		this.parentElements = new LatticeElementCollection(this, false);
	}
	
	/**
	 * Gets the lattice.
	 * 
	 * @return The current lattice object.
	 */
	public Lattice getLattice(){
		return this.lattice;
	}
	
	/**
	 * Sets the lattice.
	 */
	void setLattice(Lattice lattice){
		this.lattice = lattice;
	}
	
	/**
	 * Gets the name of the element.
	 * 
	 * @return The name of the current element.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Sets the name of the element.
	 * 
	 * @param value is the name of the lattice element.
	 */
	public void setName(String value){
		this.name = value;
	}
	
	/**
	 * Gets the collection of child elements.
	 * 
	 * @return The collection of child lattice elements. 
	 */
	public LatticeElementCollection getElements(){
		return this.elements;
	}
	
	/**
	 * Gets the collection of parent elements.
	 * 
	 * @return The collection of parent lattice elements.
	 */
	public LatticeElementCollection getParentElements(){
		return this.parentElements;
	}
	
	/**
	 * Gets the value whether the element is maximal or not.
	 * 
	 * @return The value if the current element is maximal or not.
	 */
	public Boolean getMaximal(){
		return this.maximal;
	}
	
	/**
	 * Sets the value whether the element is maximal or not.
	 * 
	 * @param value indicating if the element is maximal or not.
	 */
	public void setMaximal(Boolean value){
		this.maximal = value;
	}
	
	/**
	 * Gets the value whether the element is minimal or not.
	 * 
	 * @return The value if the current element is minimal or not.
	 */
	public Boolean getMinimal(){
		return this.minimal;
	}
	
	/**
	 * Sets the value whether the element is minimal or not.
	 * 
	 * @param value indicating if the element is minimal or not.
	 */
	public void setMinimal(Boolean value){
		this.minimal = value;
	}
	
	/**
	 * Gets the value whether the element is markable or not.
	 *
	 * @return The value if the current element is markable or not.
	 */
	public Boolean getMarkable(){
		return this.markable;
	}
	
	/**
	 * Sets the value whether the element is markable or not.
	 * 
	 * @param value indicating if the element is markable or not.
	 */
	public void setMarkable(Boolean value){
		this.markable = value;
	}
	
	/**
	 * Gets the length of the longest chain from the current element to a minimal element.
	 * 
	 * @return The length of the longest chain from the current element to a minimal element.
	 */
	public Integer getMinDistance(){
		return this.minDistance;
	}
	
	/**
	 * Sets the length of the longest chain from the current element to a minimal element.
	 * 
	 * @param distanceMin is the length of the longest chain from the current element to a minimal element.
	 */
	void setMinDistance(Integer minDistance){
		this.minDistance = minDistance;
	}
	
	/**
	 * Gets the length of the longest chain from the current element to a maximal element.
	 * 
	 * @return The length of the longest chain from the current element to a maximal element.
	 */
	public Integer getMaxDistance(){
		return this.maxDistance;
	}
	
	/**
	 * Sets the length of the longest chain from the current element to a maximal element.
	 * 
	 * @param maxDistance is the length of the longest chain from the current element to a maximal element.
	 */
	void setMaxDistance(Integer maxDistance){
		this.maxDistance = maxDistance;
	}
	
	/**
	 * Gets the level of the lattice element determined by a level function on the ordered set given by
	 * 		level(a) = distanceMin(a) - distanceMax(a) + M 
	 * where height(a) is the length of the longest chain from a to a minimal element,
	 * depth(a) is the length of the longest chain from a to maximal element,
	 * M is the length of the longest chain of the ordered set.
	 * 
	 * @return The level of the lattice element.
	 */
	public Integer getLevel(){
		return this.minDistance - this.maxDistance + this.lattice.getMinimalElement().getMaxDistance();
	}
	
	/**
	 * Returns true if the lattice point object has been initialized.
	 * 
	 * @return The value if the current element has a point or not.
	 */
	public Boolean hasPoint(){
		if(this.point == null)
			return false;
		
		return true;
	}
	
	/**
	 * Gets the point of the lattice element in the plain.
	 * 
	 * @return The point object.
	 */
	public LatticePoint getPoint(){
		if(this.point == null){
			ILatticePointDescriptor  descriptor = LatticePointDescriptorFactory.createDescriptor(this);
		
			if(descriptor != null)
				//descriptor computes the lattice element location 
				this.point =  descriptor.getPoint();
		}
		
		return this.point;
	}
	
	/**
	 * Returns the parent element required for the lattice element collection.
	 * 
	 * @return A lattice element.
	 */
	public LatticeElement getParent() {
		return this;
	}
	
	/**
	 * Gets a collection of all dependent elements for creating sub lattice elements.
	 * 
	 * @return A list with names of dependent elements.
	 */
	ArrayList<String> getDependents(){
		return this.dependents;
	}
	
	/**
	 * Sets a collection of all dependent elements for creating sub lattice elements.
	 * 
	 * @param dependents is the collection of dependent elements(collection with child elements).
	 */
	void setDependents(ArrayList<String> dependents){
		this.dependents = dependents;
	}
}
