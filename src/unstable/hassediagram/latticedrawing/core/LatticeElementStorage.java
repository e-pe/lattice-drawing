package unstable.hassediagram.latticedrawing.core;

import java.util.*;

/**
 * This class is a storage for lattice elements.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
public class LatticeElementStorage extends Vector<LatticeElement>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Finds an element by name.
	 * @param name of the lattice element.
	 * @return Returns a LatticeElement object if an element with such name was found.
	 */
	public LatticeElement find(String name){
		Enumeration<LatticeElement> ie = this.elements();
		
		while(ie.hasMoreElements()){
			LatticeElement element = ie.nextElement();
			if(element.getName().equals(name))
				return element; 
		}
		
		return null;
	}
}
