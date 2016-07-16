package unstable.hassediagram.latticedrawing.descriptors;

import unstable.hassediagram.latticedrawing.core.*;

/**
 * This class is responsible for creating the right point descriptor.
 * 
 * @author Eugen Petrosean
 * @since 2010-08-03
 */
public class LatticePointDescriptorFactory {
	
	/**
	 * Creates the specified descriptor.
	 * @param element to which the current descriptor will be applied.
	 * @return The descriptor.
	 */
	public static ILatticePointDescriptor createDescriptor(LatticeElement element){
		LatticeElementCollection parents = element.getParentElements();

		switch(parents.size()){
			case 0:
				return new LatticePointRootBranchDescriptor(element);
			case 1: 
				return new LatticePointNBranchDescriptor(element);	
			default:	
				return new LatticePointNReverseBranchDescriptor(element);
			
		}
	}
}
