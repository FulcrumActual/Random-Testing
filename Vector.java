
public class Vector {
	private String name; // No restrictions on naming, empty name by default
	private int[] vector; // The number of dimensions for this vector can vary
	
	// Constructor
	public Vector() { // Default: 0-Dimensional Vector
		this(0);
	}
	
	// Constructor
	public Vector(String name) { // Default: 0-Dimensional Vector
		this(); // Instantiate this vector with default values
		this.name = name;
	}
	
	// Constructor
	public Vector(int dimensions) { // Dimension is 1-index
		if (dimensions >= 0) { // Dimension must be non-negative
			this.vector = new int[dimensions];
		} else { // Dimension is negative
			this.vector = new int[0]; // Default: 0-Dimensional Vector
		}
	}
	
	
	// Constructor
	public Vector(String name, int dimensions) { // Dimensions is 1-index
		this(dimensions); // Instantiate this vector with the passed in number of dimensions
		this.name = name; // Give this vector a name
	}
	
	// Constructor
	public Vector(int[] vector) { // Construct vector based on an int array
		this(vector.length); // Instantiate this vector with the same number of dimensions as the int array
		for (int index = 0; index < vector.length; index++) { // Iterate through every dimension of this vector
			this.vector[index] = vector[index]; // Set dimensional values for this vector to the values in the int array
		}
	}
	
	// Constructor
	public Vector(String name, int[] vector) { // Construct vector based on an int array
		this(vector); // Instantiate this vector and initiate dimensional values
		this.name = name; // Give this vector a name
	}
	
	// Constructor
	// Construct vector with a passed in number of dimensions, then fill the first dimensions based on values in an int array
	// If int array is longer than the number of dimensions passed in, the last values of the int array will be truncated, passed in dimensions takes precedence over length of int array
	public Vector(int dimensions, int[] vector) {
		this(dimensions); // Instantiate this vector with the passed in number of dimensions
		
		for (int index = 0; index < dimensions && index < vector.length; index++) { // Iterate through every dimension of the in array, but truncate if array is longer than passed in dimensions
			this.vector[index] = vector[index]; // Initiate this vector with the passed in values in the int array
		}
	}
	
	// Constructor
	// Construct vector with a passed in number of dimensions, then fill the first dimensions based on values in an int array
	// If int array is longer than the number of dimensions passed in, the last values of the int array will be truncated, passed in dimensions takes precedence over length of int array
	public Vector(String name, int dimensions, int[] vector) {
		this(dimensions, vector); // Instantiate and initiate this vector with the passed in values
		this.name = name; // Give this vector a name
	}
	
	public String printVector() { // Prints this vector in 1 line
		String vector = "Vector ";
		int index;
		
		vector += this.name + ": {";
		
		for (index = 0; index < this.vector.length - 1; index++) { // Stop 1 before the last dimension, fence-post algorithm
			vector += this.vector[index] + ", ";
		}
		// index is equal to 1 less than the length of this vector
		
		vector += this.vector[index] + "}\n"; // Start a new line at the end
		
		return vector;
	}
	
	public String printVectorDetails() { // Prints this vector over 3 lines and provides detailed statistics for this vector
		String vector = "Vector ";
		vector += this.name + ": ";
		
		// Print number of dimensions
		if (this.vector.length == 1) {
			vector += "1 Dimension";
		} else {
			vector += this.vector.length + " Dimensions";
		}
		
		vector += "\n";
		
		// INCOMPLETE
		
		return vector;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean setName(String name) {
		this.name = name;
		return true; // Return true to show naming was successful
	}
	
	public int getDimensions() { // Dimension is 1-index
		return this.vector.length;
	}
	
	public int getValue(int dimension) { // Dimension is 1-index
		if (this.checkDimensionExists(dimension)) { // Dimension must exist for this vector
			return this.vector[dimension - 1]; // Convert dimension to 0-index for array
		} else {
			return 0; // Default value for non-existent dimension: 0
		}
	}
	
	public boolean setValue(int dimension, int value) {
		if (this.checkDimensionExists(dimension)) { // Dimension must exist for this vector
			this.vector[dimension] = value;
			return true; // Return true to show that value change was successful
		} else { // Dimension does not exist
			return false; // Return false if value change was unsuccessful
		}
	}
	
	public boolean setDimensions(int setDimensions) { // Works for both adding and deleting dimensions, always works from highest-dimension cells
		int[] tempVector = this.vector; // Save vector data to be re-added to the new vector later
		this.vector = new int[tempVector.length + setDimensions]; // Re-instantiate this vector with the new dimensions
		
		for (int index = 0; index < this.vector.length; index++) {
			this.vector[index] = tempVector[index]; // Fill in this vector with old vector data
		}
		
		return true; // Return true to show that change to number of dimensions was successful
	}
	
	public boolean addVector(Vector vector2) { // Number of dimensions is the larger of the two vectors, values for the same dimension are added together
		for (int index = 0; index < vector2.getDimensions() && index < this.vector.length; index++) { // Stop adding once the maximum dimension of the lower-dimension vector is reached
			this.vector[index] += vector2.getValue(index); // Add the values for that dimension
		}
		return true; // Return true to show that adding was successful
	}
	
	public boolean appendVector(Vector vector2) { // Number of dimensions is the sum of the two vectors' number of dimensions, this vector's values come first
		this.setDimensions(vector2.getDimensions()); // Increase the number of dimensions for this vector to the appropriate number
		return this.addVectorValuesToEnd(vector2); // Append the values of vector2 to the end of this vector, return whether or not appending was successful
	}
	
	public boolean scaleVector(int scale) { // Scales vector
 		for (int index = 0; index < this.vector.length; index++) { // Iterate through every dimension of this vector
			this.setValue(index, scale * this.getValue(index)); // Replace value with its scaled value
		}
		
		return true; // Return true to show scaling was successful
	}
	
	public float getMagnitude() {
		// INCOMPLETE
	}
	
	private boolean checkDimensionExists(int dimension) { // Checks if dimension exists for this instance of vector
		return dimension > 0 && dimension <= this.vector.length; // Return if the dimension exists or not, accounts for change from 1-index to 0-index
	}
	
	private boolean addVectorValuesToEnd(Vector vector2) { // Number of dimensions is the larger of the two vectors, values for the second vector are added forwards but right-justified
		int vector2Dimensions = vector2.getDimensions(); // Store dimensions locally
		int adjustedIndex = this.vector.length - vector2Dimensions; // Index for this vector accounting for right-justification
		
		if (this.vector.length >= vector2Dimensions) { // This vector must have more dimensions than vector2
			for (int index = 0; index < vector2Dimensions; index++) { // Iterate through the dimensions of vector2
				this.vector[adjustedIndex++] = vector2.getValue(index); // Add dimension values for vector2 to this vector, increment adjustedIndex every iteration
			}
			// adjustedIndex is now equal to this.vector.length
			
			return true;
		} else { // Vector2 has more dimensions than this vector
			return false; // Return false to show that adding to end was not successful (not possible)
						  // *Note: Adding with a higher dimensional vector2 can be implemented
		}
	}
}
