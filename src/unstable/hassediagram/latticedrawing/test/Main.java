package unstable.hassediagram.latticedrawing.test;

import unstable.hassediagram.latticedrawing.core.*;
import unstable.hassediagram.latticedrawing.utils.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		Lattice lattice = Lattice.loadFromFile("src/model/unstable/hassediagram/latticedrawing/test/dim6.txt");	
		
		LatticeElement maxElement = lattice.getMaximalElement();
		
		System.out.println("dfdf");
		
	}

}
