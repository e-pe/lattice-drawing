package unstable.hassediagram.latticedrawing.formatters;

import unstable.hassediagram.latticedrawing.core.*;

/**
 * This class represents a formatter for converting a lattice to the json format.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
public class LatticeJsonFormatter implements ILatticeFormatter {
	
	private Lattice lattice;
	
	/**
	 * Constructor
	 * @param lattice specifies the structure which will be converted to the json format.
	 */
	public LatticeJsonFormatter(Lattice lattice){
		this.lattice = lattice;
	}

	/**
	 * Converts to the Json format.
	 * 
	 * @return The lattice structure in json format.
	 */
	public String Format() {
		LatticeElementLevelCollection storage = this.lattice.getLevels();
		
		String json = "[";
		
		for(int k = 0; k < storage.size(); k++){
			LatticeElementLevel level = storage.get(k);
			
			for(int i = 0; i < level.size(); i++){
				LatticeElement e = level.get(i);
				
				String dependents = "[";
			
				for(int j = 0; j < e.getElements().size(); j++)
				{
					LatticeElement child = e.getElements().get(j);
				
					dependents += "\"" + ((int)child.getName().toCharArray()[0] - 97) + "\"";
				
					if(j < e.getElements().size() - 1)
						dependents +=  ","; 
				}
			
				dependents += "]";
			
				LatticePoint point = e.getPoint();
			
				json += "{";
				json += "\"name\":\"" + ((int)e.getName().toCharArray()[0] - 97) + "\",";
				json += "\"markable\":" + e.getMarkable() + ",";
				json += "\"x\":" + point.getX() + ",";
				json += "\"y\":" + point.getY() + ",";
				json += "\"dependents\":" + dependents + "";
				json += "}";
				
				if(i < level.size() - 1)
					json += ",";
			}
			
			if(k < storage.size() - 1)
				json += ",";
		}
		
		json += "]";
		
		return json;
	}

}
