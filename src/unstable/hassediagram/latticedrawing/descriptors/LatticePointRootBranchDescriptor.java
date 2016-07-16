package unstable.hassediagram.latticedrawing.descriptors;

import unstable.hassediagram.latticedrawing.core.*;

/**
 * This class represents a descriptor for positioning the root (maximal) lattice element.
 *  
 * @author Eugen Petrosean
 * @since 2010-08-03
 */
public class LatticePointRootBranchDescriptor implements ILatticePointDescriptor {

	LatticeElement element;
	
	/**
	 * Constructor
	 * 
	 * @param element to which the current descriptor will be applied.
	 */
	public LatticePointRootBranchDescriptor(LatticeElement element){
		this.element = element;
	}
	
	@Override
	/**
	 * Gets the position of the current element.
	 */
	public LatticePoint getPoint() {
		return new LatticePoint(0.0f, Float.parseFloat(element.getLevel().toString()));
	}

}
