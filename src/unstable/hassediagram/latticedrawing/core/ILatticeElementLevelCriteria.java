package unstable.hassediagram.latticedrawing.core;

/**
 * This interface represents a delegate for creating criteria to select specified lattice element levels.
 * 
 * @author Eugen Petrosean
 * @since 2010-08-05
 */
public interface ILatticeElementLevelCriteria {
	/**
	 *  
	 * Returns true whether the current criterion satisfies a specified condition.
	 * 
	 * @param bottom is a lattice element used for creating a specified criterion. 
	 * @param top is a lattice element used for creating a specified criterion.
	 * @param current is a lattice element used for creating a specified criterion.
	 * @return True whether the current criterion satisfies a specified condition.
	 */
	public Boolean exists(LatticeElement bottom, LatticeElement top, LatticeElement current);
}
