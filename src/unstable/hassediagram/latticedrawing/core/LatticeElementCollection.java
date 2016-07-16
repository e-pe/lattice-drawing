package unstable.hassediagram.latticedrawing.core;

import unstable.hassediagram.latticedrawing.utils.*;

/**
 * This class stores lattice elements.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 *
 */
public class LatticeElementCollection extends LatticeElementStorage  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean topDown;
	private ILatticeElementDependency dependency;
	
	/**
	 * Constructor
	 * 
	 * @param dependency indicates from which lattice element does the current collection depend.
	 * @param topDown indicates if the collection is a linked list (top -> down or down -> top).
	 */
	public LatticeElementCollection(ILatticeElementDependency dependency, Boolean topDown){
		this.topDown = topDown;
		this.dependency = dependency;
	}
	
	/**
	 * Adds a new lattice element to the collection.
	 * 
	 * @param element is the lattice element to add.
	 */
	public boolean add(LatticeElement element){
		Lattice lattice = this.dependency.getLattice();
		Relation relation = lattice.getRelation();
		LatticeElement parentElement = this.dependency.getParent();
		
		element.setLattice(lattice);
		
		if(this.topDown){
			//stores all elements in a storage collection
			lattice.addToStorage(element);
		
			if(parentElement != null)
				relation.getMap().add(element.getName(), parentElement.getName());			
		}
		
		//adds to the vector collection
		if(!this.contains(element))
			super.add(element);
		
		return true;
	}
	
	/**
	 * Returns true if the current lattice element is the first element in the lattice element collection.
	 * 
	 * @param element is the lattice element
	 * 
	 * @return True whether the element is the first in the collection. 
	 */
	public boolean isFirst(LatticeElement element){
		if(element == this.firstElement())
			return true;
		
		return false;
	}
	
	/**
	 * Returns the last lattice element of the current lattice element collection.
	 * 
	 * @param element is the lattice element
	 * 
	 * @return True whether the element is the last in the collection. 
	 */
	public boolean isLast(LatticeElement element){
		if(element == this.lastElement())
			return true;
		
		return false;
	}
}
