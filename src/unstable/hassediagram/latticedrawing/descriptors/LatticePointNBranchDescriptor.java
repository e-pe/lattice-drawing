package unstable.hassediagram.latticedrawing.descriptors;

import unstable.hassediagram.latticedrawing.core.*;

/**
 * This class represents a descriptor for positioning sub lattice elements under its parent as a branch.
 * 
 * @author Eugen Petrosean
 * @since 2010-08-03
 */
public class LatticePointNBranchDescriptor implements ILatticePointDescriptor {

	LatticeElement element;
	
	/**
	 * Constructor
	 * @param element to which the current descriptor will be applied.
	 */
	public LatticePointNBranchDescriptor(LatticeElement element)
	{
		this.element = element;
	}
	
	@Override
	/**
	 * Gets the position of the current element.
	 */
	public LatticePoint getPoint() {
		LatticeElement parent = this.element.getParentElements().get(0);
		LatticeElementCollection elements = parent.getElements();
		Integer elementPosition = elements.indexOf(this.element) + 1;
		
		return new LatticePoint(parent.getPoint().getX() + elementPosition - ((elements.size() + 1) / 2.0f), new Float(this.element.getLevel()));
	}

}
