package unstable.hassediagram.latticedrawing.utils;

/**
 * This class represents the functionality for calculating different triangle relations.
 * 
 * @author Eugen Petrosean
 * @since 2010-08-03
 */
public class Triangle {
	
	/**
	 * Gets the height of a triangle described by three sides a, b, c.
	 * @param a first side of a triangle
	 * @param b second side of a triangle
	 * @param c the longest side oft the triangle
	 * @return The height of the triangle.
	 */
	public static Float getCHeight(Float a, Float b, Float c){
		int positions = 2;
		double factor = Math.pow(10, positions); 

		a = new Float(Math.round(a * factor) / factor);
		b = new Float(Math.round(b * factor) / factor);
		c = new Float(Math.round(c * factor) / factor);
		
		double root = Math.abs((2 * ((Math.pow(a, 2) * Math.pow(b, 2)) + (Math.pow(b, 2) * Math.pow(c, 2)) + (Math.pow(c, 2)* Math.pow(a, 2)))) - (Math.pow(a, 4) + Math.pow(b, 4) + Math.pow(c, 4)));
		
		return new Float(Math.sqrt(Math.round(root * factor) / factor) / (2 * c));
	}
	
	/**
	 * Gets the distance between two vertices.
	 * @param x1 coordinate of the first vertex
	 * @param y1 coordinate of the first vertex
	 * @param x2 coordinate of the second vertex
	 * @param y2 coordinate of the second vertex
	 * 
	 * @return The distance between two vertices. 
	 */
	public static Float getDistance(Float x1, Float y1, Float x2, Float y2){
		return new Float(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
	}
}
