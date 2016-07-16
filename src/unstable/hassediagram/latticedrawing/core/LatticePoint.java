package unstable.hassediagram.latticedrawing.core;

/**
 * This class represents the position of a lattice element.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
public class LatticePoint {
	private Float x;
	private Float y;
	
	/**
	 * Constructor
	 * @param x coordinate
	 * @param y coordinate
	 */
	public LatticePoint(Float x, Float y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the x position of the point.
	 * @return X coordinate.
	 */
	public Float getX(){
		return this.x;
	}
	
	/**
	 * Sets the x position of the point.
	 * @param value of the x coordinate.
	 */
	public void setX(Float value){
		this.x = value;
	}
	
	/**
	 * Gets the y position of the point.
	 * @return Y coordinate.
	 */
	public Float getY(){
		return this.y;
	}
	
	/**
	 * Sets the y position of the point.
	 * @param value of the y coordinate.
	 */
	public void getY(Float value){
		this.y = value;
	}
}
