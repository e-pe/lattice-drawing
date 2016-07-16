package unstable.hassediagram.latticedrawing.core;

/**
 * This interface represents a context in which every lattice element collection exists.  
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
interface ILatticeElementDependency {
	/**
	 * Gets the parent element. 
	 * 
	 * @return A lattice element.
	 */
	LatticeElement getParent();
	
	/**
	 * Gets the lattice object.
	 * 
	 * @return A lattice object.
	 */
	Lattice getLattice();
}
