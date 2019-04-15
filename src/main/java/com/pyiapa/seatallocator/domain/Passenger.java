package com.pyiapa.seatallocator.domain;


/**
 * 
 * Holds passenger-related information.
 * 
 * @author pyiapa
 * 
 */
public class Passenger {
	
	//a unique identifier for the passenger
	private int passengerID;
	
	//whether the passenger has window preference or not
	private boolean windowPreference;
	
	//whether the passenger is part of a group
	private boolean passengerInGroup;
	
	//passenger's satisfaction in terms of sitting preferences
	private double satisfaction;
	
	//passenger that was initially in a group but had to be separated due to group not
	//fitting in any available row
	private boolean separatedFromGroup;
	
	/**
	 * 
	 * Creates a passenger object to hold information about a passenger.
	 * 
	 * @param passengerID Passenger's unique identifier
	 * @param windowPreference Whether the passenger has window preference or not
	 * @param passengerInGroup Whether the passenger is part of a group
	 */
	public Passenger(int passengerID, boolean windowPreference, boolean passengerInGroup) {
		this.passengerID = passengerID;
		this.windowPreference = windowPreference;
		this.passengerInGroup = passengerInGroup;
		
		//initially assume no satisfaction until we fulfill the passenger's preferences
		this.satisfaction = 0;
		
		this.separatedFromGroup = false;
	}
	
	/**
	 * Gets passenger's satisfaction score.
	 * 
	 * @return Passenger's satisfaction
	 */
	public double getSatisfaction() {
		return satisfaction;
	}
	
	/**
	 * Updates passenger's satisfaction score by adding the value provided
	 * 
	 * @param satisfaction The value to add to the Passenger's score
	 */
	public void updateSatisfaction(double satisfaction) {
		this.satisfaction += satisfaction;
	}
	
	/**
	 * Gets passenger ID
	 * 
	 * @return Passenger ID
	 */
	public int getPassengerID() {
		return passengerID;
	}

	/**
	 * Informs whether the passenger has window preference.
	 * 
	 * @return Passenger's window preference option
	 */
	public boolean hasWindowPreference() {
		return windowPreference;
	}
	
	/**
	 * Informs whether the passenger is part of a group.
	 * 
	 * @return Whether passenger is part of a group
	 */
	public boolean isPassengerInGroup() {
		return passengerInGroup;
	}
	
	/**
	 * Informs whether the passenger has been separated from their group.
	 * 
	 * @return Whether the passenger has been separated from their group
	 */
	public boolean isSeparatedFromGroup() {
		return separatedFromGroup;
	}
	
	
	/**
	 * Changes passenger status to indicate that they have been separated (or not) from their group.
	 * 
	 * @param separatedFromGroup Updated status on passengers group membership 
	 */
	public void setSeparatedFromGroup(boolean separatedFromGroup) {
		this.separatedFromGroup = separatedFromGroup;
	}
	
	/**
	 * Gets a representation of the passenger
	 */
	public String toString() {
		return String.valueOf(this.passengerID);
	}
	
	

}
