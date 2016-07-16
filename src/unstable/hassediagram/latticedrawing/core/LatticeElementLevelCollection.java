package unstable.hassediagram.latticedrawing.core;

import java.util.*;

/**
 * This class is a collection for lattice levels.
 * 
 * @author Eugen Petrosean
 * @since 2010-08-05
 */
public class LatticeElementLevelCollection extends Vector<LatticeElementLevel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Returns a collection of all levels satisfying specified criteria.
	 * 
	 * @param bottom is a level used for creating a specified criterion. 
	 * @param top is a level used for creating a specified criterion.
	 * @param onGetCriteria is a delegate which executes and determines whether the result matches the defined criterion.
	 * @return A new lattice element level collection whith the matching data.
	 */
	public LatticeElementLevelCollection getRange(LatticeElementLevel bottom, LatticeElementLevel top, ILatticeElementLevelCriteria onGetCriteria){
		LatticeElement topElement = top.firstElement();
		LatticeElement bottomElement = bottom.firstElement();		
		LatticeElementLevelCollection levels = new LatticeElementLevelCollection();
		
		for(int i = 0; i < this.size(); i++){
			LatticeElementLevel level = this.get(i);
			LatticeElement element = level.firstElement();
			
			if(onGetCriteria.exists(bottomElement, topElement, element)){
				if(!levels.contains(level))
					levels.add(level);
			}
		}
		
		return levels;
	}
	
	/**
	  * Finds the level which the current element belongs to.
	 * 
	 * @param e is a lattice element
	 * @return A lattice element level object containing the current lattice element.
	 */
	public LatticeElementLevel find(LatticeElement e){
		for(int i = 0; i < this.size(); i++){
			LatticeElementLevel level = this.get(i);
			
			if(level.contains(e))
				return level;
		}
		
		return null;
	}
}
