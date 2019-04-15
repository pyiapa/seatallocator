package com.pyiapa.seatallocator.domain;

import java.util.List;

/**
 * Represents the solution to the sitting arrangement application. It holds information
 * about the final sitting configuration of the passengers in the plane as well as the
 * total satisfaction score.
 * 
 * @author pyiapa
 *
 */
public class SittingArrangement {
	
	//seating configuration of passengers in the plane
	private List<List<Passenger>> seatConfiguration;
	
	//total passenger satisfaction
	private String satisfaction;
	
	
	/**
	 * Creates an object that holds the final sitting configuration of passengers in the plane.
	 * 
	 * @param seatConfiguration The sitting configuration of the passengers
	 * @param satisfaction The total passenger satisfaction
	 */
	public SittingArrangement(List<List<Passenger>> seatConfiguration, String satisfaction) {
		this.seatConfiguration = seatConfiguration;
		this.satisfaction = satisfaction;
	}
	
	/**
	 * Gets the final sitting configuration of passengers in the plane.
	 * 
	 * @return The final sitting configuration.
	 */
	public List<List<Passenger>> getSeatConfiguration() {
		return this.seatConfiguration;
	}
	
	/**
	 * Gets the total passenger satisfaction.
	 * 
	 * @return The total passenger satisfaction score
	 */
	public String getSatisfaction() {
		return this.satisfaction;
	}
	
	
	
}
