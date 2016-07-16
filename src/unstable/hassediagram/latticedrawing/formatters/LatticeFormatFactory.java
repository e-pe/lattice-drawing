package unstable.hassediagram.latticedrawing.formatters;

import unstable.hassediagram.latticedrawing.core.*;


/**
 * This class is responsible for creating a new lattice formatter.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
public class LatticeFormatFactory {
	/**
	 * Creates a new lattice formatter.
	 * @param lattice to which a specified formatter will be applied.
	 * @param format specifies the convertion type
	 * 
	 * @return The specified formatter.
	 */
	public static ILatticeFormatter CreateFormatter(Lattice lattice, LatticeFormatType format){
		switch(format)
		{
			case Json:
				return new LatticeJsonFormatter(lattice);
			case Latex:
				return new LatticeLatexFormatter(lattice) ;
		}
		
		return null;
	}
}
