package unstable.hassediagram.latticedrawing.core;

/**
 * This class represents a level containing lattice elements with the same properties.
 * 
 * @author Eugen Petrosean
 * @since 2010-08-05
 */
public class LatticeElementLevel extends LatticeElementStorage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Adds a range of lattice element collections.
	 * 
	 * @param elements to add.
	 */
	public void addRange(LatticeElementCollection elements){
		for(int i = 0; i < elements.size(); i++){
			LatticeElement element = elements.get(i);
			
			if(!this.contains(element))
				super.add(element);
		}
	}

}
