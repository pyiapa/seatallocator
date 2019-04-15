package com.pyiapa.seatallocator.domain;

/**
 * 
 * Holds information about a specific row in the plane to allow for quick
 * access to row statistics such as seat availability, window availability, and
 * total passenger satisfaction in the row.
 * 
 * @author pyiapa
 *
 */
public class RowDescriptor {
	
	//number of available seats in the row
	private int availableSeats;
	
	//number of available window seats in the row
	private int availableWindowSeats;
	
	//total passenger satisfaction in the row
	private double rowSatisfaction;
	
	
	/**
	 * 
	 * Creates an object that holds information about a given row on the airplane
	 * 
	 * @param availableSeats Number of available seats in the row
	 * @param availableWindowSeats Number of available window seats in the row
	 */
	public RowDescriptor(int availableSeats, int availableWindowSeats) {
		this.availableSeats = availableSeats;
		this.availableWindowSeats = availableWindowSeats;
		
		//initial row satisfaction is zero as there are no passengers sitting yet 
		this.rowSatisfaction = 0;

	}
	
	/**
	 * Obtains the number of currently available seats in the row
	 * 
	 * @return Number of available seats in the row
	 */
	public int getAvailableSeats() {
		return this.availableSeats;
	}
	
	/**
	 * Updates the number of currently available seats in the row
	 * by the value provided
	 * 
	 * @param availableSeats The value to add to the number of currently available seats in the row
	 */
	public void updateAvailableSeats(int availableSeats) {
		this.availableSeats += availableSeats;
	}

	/**
	 * Obtains the number of currently available window seats in the row
	 * 
	 * @return Number of available window seats in the row
	 */
	public int getAvailableWindowSeats() {
		return this.availableWindowSeats;
	}
	
	/**
	 * Updates the number of currently available window seats in the row
	 * by adding the value provided
	 * 
	 * @param availableWindowSeats The value to add to the number of available window seats in the row
	 */
	public void updateAvailableWindowSeats(int availableWindowSeats) {
		this.availableWindowSeats += availableWindowSeats;
	}
	
	/**
	 * Obtains the row satisfaction score
	 * 
	 * @return Row satisfaction score
	 */
	public double getRowSatisfaction() {
		return this.rowSatisfaction;
	}
	
	/**
	 * Updates the row satisfaction score by adding the value provided
	 * 
	 * @param rowSatisfaction The value to add to the current row satisfaction score
	 */
	public void updateRowSatisfaction(double rowSatisfaction) {
		this.rowSatisfaction += rowSatisfaction;
	}
}
