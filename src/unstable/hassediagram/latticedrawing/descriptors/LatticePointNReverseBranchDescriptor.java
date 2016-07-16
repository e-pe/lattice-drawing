package unstable.hassediagram.latticedrawing.descriptors;

import unstable.hassediagram.latticedrawing.core.*;

/**
 * This class represents a descriptor for positioning a single lattice element under its parent elements as a branch.
 * 
 * @author Eugen Petrosean
 * @since 2010-08-03
 */
public class LatticePointNReverseBranchDescriptor implements ILatticePointDescriptor {

	LatticeElement element;
	
	/**
	 * Constructor
	 * 
	 * @param element to which the current descriptor will be applied.
	 */
	public LatticePointNReverseBranchDescriptor(LatticeElement element){
		this.element = element;
	}
	
	@Override
	/**
	 * Gets the position of the current element.
	 */
	public LatticePoint getPoint() {
		LatticeElementCollection parents = this.element.getParentElements();
		Float xPos = parents.get(0).getPoint().getX();
		Float minX = xPos;
		Float maxX = xPos; 
		
		for(int i = 0; i < parents.size(); i++){
			LatticeElement parent = parents.get(i);
			Float xParentPos = parent.getPoint().getX();
			
			if(xParentPos < minX)
				minX = xParentPos;
			
			if(xParentPos > maxX)
				maxX = xParentPos;
		}
		
		return new LatticePoint((maxX + minX) / 2.0f, new Float(this.element.getLevel()));
	}

}
