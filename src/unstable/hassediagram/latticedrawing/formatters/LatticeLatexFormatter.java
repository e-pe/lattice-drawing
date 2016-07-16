package unstable.hassediagram.latticedrawing.formatters;

import unstable.hassediagram.latticedrawing.core.*;

/**
 * This class represents a formatter for converting a lattice to the latex format.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
public class LatticeLatexFormatter implements ILatticeFormatter {
	
	private Lattice lattice;
	
	/**
	 * Constructor
	 * @param lattice specifies the structure which will be converted to the latex format.
	 */
	public LatticeLatexFormatter(Lattice lattice){
		this.lattice = lattice;
	}
	
	/**
	 * Converts to the latex format.
	 * 
	 * @return The lattice structure in latex format.
	 */
	public String Format() {
		String latex = "";
		String dependents = "";
		
		LatticeElementLevelCollection storage = this.lattice.getLevels();
		
		latex += "\\psset{xunit=10mm,yunit=10mm}\n";
		latex += "\\pspicture(-10,-10)(20,20)\n";
		latex += "\\psset{linewidth=0.3pt,arrowsize=3pt}\n";
		
		for(int k = 0; k < storage.size(); k++){
			LatticeElementLevel level = storage.get(k);
			
			for(int i = 0; i < level.size(); i++){
				LatticeElement e = level.get(i);
				LatticePoint point = e.getPoint();
				Integer caption = (int)e.getName().toCharArray()[0] - 97;
				
				latex += "\\cnode("+ point.getX() +", "+ point.getY() +"){0.5}{"+ e.getName() +"}\n"; 
				latex += "\\rput("+ point.getX() +", "+ point.getY() +"){$"+ caption +"$}\n";
				
				for(int j = 0; j < e.getElements().size(); j++)
				{
					LatticeElement child = e.getElements().get(j);
					
					dependents += "\\ncline{"+ e.getName() +"}{"+ child.getName() +"}\n"; 
				}
			}
		}
		
		latex += dependents;
		latex += "\\endpspicture";
		
		return latex;
	}

}
