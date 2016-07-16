package unstable.hassediagram.latticedrawing.descriptors;

import unstable.hassediagram.latticedrawing.core.*;

/**
 * This interface represents the functionality for creating a point with x, y coordinates.
 * 
 * @author Eugen Petrosean
 * @since 2010-08-03	
 */
public interface ILatticePointDescriptor {
	/**
	 * Gets the calculated point.
	 * 
	 * @return The point for a lattice element.
	 */
	LatticePoint getPoint();
}
