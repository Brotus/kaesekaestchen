package entity;

/**
 * A simple Enumeration to differ States within the game loop.
 */

public enum FieldStates {
	/**
	 * Edge has already been marked.
	 */
	INVALID, 
	/**
	 * Edge has been marked without surrounding a field.
	 */
	MARKED, 
	/**
	 * Edge has been marked, one Field got owned.
	 */
	ONE,
	/**
	 * The two surrounding Fields of the Edge got owned.
	 */
	TWO
}
