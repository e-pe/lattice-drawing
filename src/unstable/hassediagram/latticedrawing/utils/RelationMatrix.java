package unstable.hassediagram.latticedrawing.utils;

/**
 * This class represents a matrix describing relations between elements.
 * 
 * @author Eugen Petrosean
 * @since 2010-06-01
 */
public class RelationMatrix {
	private String matrixDef;
	private Integer rowDimension;
	private Integer columnDimension;
	private Boolean[][] matrix;
	
	/**
	 * Constructor
	 * 
	 * @param matrixDef is definition string containing the lattice eleemnt description.  
	 * @param rowDimension is the row dimension of the matrix.
	 * @param columnDefinition is the column dimension of the matrix.
	 */
	public RelationMatrix(String matrixDef, Integer rowDimension, Integer columnDimension){
		this.matrixDef = matrixDef;
		this.rowDimension = rowDimension;
		this.columnDimension = columnDimension;
	}
	
	/**
	 * Gets the matrix row dimension.
	 * 
	 * @return The row dimension of the matrix.
	 */
	public Integer getRowDimension(){
		return this.rowDimension;
	}
	
	/**
	 * Gets the matrix column dimension.
	 * 
	 * @return The column dimension of the matrix.
	 */
	public Integer getColumnDimension(){
		return this.columnDimension;
	}
	
	/**
	 * Gets the matrix.
	 * 
	 * @return The matrix.
	 */
	public Boolean[][] getMatrix(){
		if(this.matrix == null)
			this.matrix = this.createMatrix();
		
		return this.matrix;
	}
	
	/**
	 * Gets the matrix definition.
	 * 
	 * @return The matrix definition string.
	 */
	public String getMatrixDef(){
		return this.matrixDef;
	}
	
	/**
	 * Finds a row by the row index.
	 * 
	 * @param rowIndex of the matrix.
	 * @return the row of the matrix.
	 */
	public Boolean[] getRow(Integer rowIndex){
		Boolean[] row = new Boolean[this.columnDimension];		
		char[] values = this.getRowDef(rowIndex).toCharArray();
		
		for(int i = 0; i < this.columnDimension; i++){
			if(values[i] == '0')
				row[i] = false;
			else if(values[i] == '1')
				row[i] = true;
		}
		
		return row;
	}
	
	/**
	 * Gets the row definition.
	 * 
	 * @param rowIndex of the matrix.
	 * @return The row definition.
	 */
	public String getRowDef(Integer rowIndex){
		return matrixDef.substring(rowIndex * this.columnDimension, (rowIndex * this.columnDimension) + this.columnDimension);
	}
	
	/**
	 * Finds an entry by the row and column indecies.
	 * 
	 * @param rowIndex of the matrix.
	 * @param columnIndex of the matrix.
	 * @return the value of the entry.
	 */
	public Boolean getEntry(Integer rowIndex, Integer columnIndex){
		if(this.matrixDef != null && !this.matrixDef.equals(""))
			return this.getMatrix()[rowIndex][columnIndex];
		
		return false;
	}
	
	/**
	 * Creates a new matrix.
	 * 
	 * @return A new matrix.
	 */
	private Boolean[][] createMatrix(){
		Boolean[][] rows = new Boolean[this.rowDimension][this.columnDimension];
		
		for(int i = 0; i < this.rowDimension; i++)
			rows[i] = this.getRow(i);
		
		return rows;
	}
	
	/**
	 * Returns true whether there is no matrix definition.
	 * 
	 * @return True whether there is no matrix definition.
	 */
	public Boolean isEmpty(){
		return this.matrixDef.equals("");
	}
}
