package unstable.hassediagram.latticedrawing.core;

/**
 * This interface represents a delegate for setting the distance, used by calculating the y coordinate.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
public interface ILatticeElementDelegate {
	/**
	 * Sets the distance for a lattice element.
	 * 
	 * @param element is a lattice element for which the distance will be set.
	 * @param distance is the calculated distance for a lattice element.
	 */
	void setDistance(LatticeElement element, Integer distance);
}
