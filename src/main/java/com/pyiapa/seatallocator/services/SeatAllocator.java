package com.pyiapa.seatallocator.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.pyiapa.seatallocator.domain.Passenger;
import com.pyiapa.seatallocator.domain.RowDescriptor;
import com.pyiapa.seatallocator.domain.SittingArrangement;

/**
 * A service that generates the best sitting configuration for passengers in a flight with the aim of
 * maximum preference satisfaction.
 * 
 * @author pyiapa
 *
 */
public class SeatAllocator {
	
	//holds a group of passengers. Each element is a list of groups of passengers
	//wishing to seat together
	private List<List<Passenger>> passengerGroups;
	
	//the rows of the plane. Each element is a list that represents customers sitting together in a row
	private List<List<Passenger>> planeRows;
	
	//information about each row
	private List<RowDescriptor> rowDescriptors;
	
	//number of rows in the plane
	private int numRows;
	
	//number of seats per row
	private int numSeatsInRow;
	
	//number of windows (per row) in the plane
	private int numWindowsInPlane;
	
	//total passenger count (could be larger than plane capacity if flight is over-subscribed)
	private int passengerCount;
	
	//indicates that no suitable row was found to seat a group of passengers
	private final int NO_SUITABLE_ROW = -1;
	
	//passenger satisfaction values
	private final double FULL_SATISFACTION = 1;
	private final double HALF_SATISFACTION = 0.5;
	private final double NO_SATISFACTION = 0;

	
	
	/**
	 * 
	 * Creates an object that generates a sitting configuration for passengers with the aim of
	 * maximum preference satisfaction.
	 * 
	 * @param passengerGroups The groups of passenger to be seated
	 * @param numRows Number of available rows in the plane
	 * @param numSeatsInRow Number of seats per row
	 * @param passengerCount Total passenger count (may be larger than plane capacity)
	 * @param numWindowsInPlane Number of windows (per row) in the plane
	 */
	public SeatAllocator(List<List<Passenger>> passengerGroups, int numRows, int numSeatsInRow, 
			             int passengerCount, int numWindowsInPlane) {
		
		this.passengerGroups = passengerGroups;
		this.numRows = numRows;
		this.numSeatsInRow = numSeatsInRow;
		this.numWindowsInPlane = numWindowsInPlane;
		this.passengerCount = passengerCount;
		
		this.rowDescriptors = new ArrayList<RowDescriptor>();
		this.planeRows = new ArrayList<List<Passenger>>();
	}
	

	
	/**
	 * Performs the sitting allocation of passengers to available seats while trying to maximize
	 * satisfaction based on passenger preferences.
	 * 
	 * @return Passenger Object that holds final sitting configuration and satisfaction score.
	 */
	public SittingArrangement allocateSeats() {
		
		//passengers that their group will not fit in any row so they were separated
		//from their original group
		List<Passenger> separatedPassengerGroups = new ArrayList<Passenger>();
		
		//the group that we are currently looking to fit in an available row
		List<Passenger> currentPassengerGroup = null;
		
		//the row in the plane we are currently inspecting
		int currentRow = 0;
		
		//a suitable row to seat a given passenger group
		int suitableRow = 0 ;
		
		//number of window preferences in the passenger group
		int numWindowPreferences = 0;
		
		//sort passenger groups by prioritizing larger groups
		//if two groups have same size then prioritize groups that have people with window preferences
		passengerGroups = sortPassengerList(passengerGroups);
		
		//open a new row to seat passengers as well as a new descriptor that keeps information
		//about that row
		rowDescriptors.add(new RowDescriptor(numSeatsInRow, numWindowsInPlane));
		planeRows.add(new ArrayList<Passenger>(numSeatsInRow));

		//go over passenger groups and try to find the optimal way to fit them in the plane
		while(!passengerGroups.isEmpty()) {
			
			//get the top passenger group from the list
			currentPassengerGroup = passengerGroups.get(0);
			
			//count the number of window seat preferences in the group under question 
			numWindowPreferences = 0;
			for(Passenger p: currentPassengerGroup) {
				if(p.hasWindowPreference()) {
					numWindowPreferences++;
				}
			}
			
			//try to find a row that will fit this group based on their preferences of group size
			//and window seats
			suitableRow = findSuitableRow(currentRow, currentPassengerGroup.size(), numWindowPreferences);
			
			
			if(suitableRow == NO_SUITABLE_ROW) { //passengers do not fit in any of the existing open rows
				
				if(currentPassengerGroup.size() > numSeatsInRow) { //if passengers will not fit in any row
					
					//break them up, remove them from current list, and add them to the list of single
					//unsatisfied customers to deal with it later.
					//the aim is to try and fit first the customers that will give us full satisfaction rate
					separatedPassengerGroups.addAll(currentPassengerGroup);
					passengerGroups.remove(currentPassengerGroup);
					
				}else { 
					//open a new row to try and seat the current passenger group as well as a new descriptor 
					//that keeps information about that row
					rowDescriptors.add(new RowDescriptor(numSeatsInRow, numWindowsInPlane));
					planeRows.add(new ArrayList<Passenger>(numSeatsInRow));
					
					//proceed to check the next row (that we've just opened) in the plane
					currentRow++; 
				}
				
			}else{ // passengers fit in one of the existing open rows
				
				//add passengers to the row
				for(Passenger currentPassenger: currentPassengerGroup) {
					fitPassengerInCurrentRow(suitableRow, currentPassenger);
				}
				
				//remove passengers from original list since they are now seating
				passengerGroups.remove(currentPassengerGroup);
				
				
			}
			
		}//end of while loop that tries to seat passengers
		
		//sort passengers that were separated from their groups by prioritizing window preferences first.
		//This will at least allow to satisfy some if there are still window seats available 
		Collections.sort(separatedPassengerGroups, new Comparator<Passenger>() {    
	        @Override
	        public int compare(Passenger passenger1, Passenger passenger2) {
	        			return passenger1.hasWindowPreference()? -1:1;
	        }               
		});
		
		
		currentRow = 0;
		
		//seat remaining passengers 
		while(!separatedPassengerGroups.isEmpty()) {
			
			//get passenger from the top of the list
			Passenger currentPassenger = separatedPassengerGroups.get(0);
			
			//find a suitable row, satisfying window preference if possible
			suitableRow = findSuitableRow(currentRow, 1, currentPassenger.hasWindowPreference()?1:0);
			
			
			if(suitableRow == NO_SUITABLE_ROW) { //no suitable row found
	
				
				//open a new row to try and seat the current passenger as well as a new descriptor 
				//that keeps information about that row
				rowDescriptors.add(new RowDescriptor(numSeatsInRow, numWindowsInPlane));
				planeRows.add(new ArrayList<Passenger>(numSeatsInRow));
				
				//proceed to check the next row in the plane (which is the one we've just opened)
				currentRow++; 
				
			}else { // passenger fits in one of the existing rows
				
				//seat passenger in available seat. Indicate that they were separated from their original group
				//in order to update satisfaction appropriately. Remove passenger from the list as they are
				//now seated.
				currentPassenger.setSeparatedFromGroup(true);
				fitPassengerInCurrentRow(suitableRow, currentPassenger);
				separatedPassengerGroups.remove(currentPassenger);
			}
		}
		
		//return the final sitting configuration and satisfaction score
		return (new SittingArrangement(getSeatConfiguration(), getSatisfaction()));
		
	} // end of method allocateSeats()
	
	
	/*
	 * Find a suitable row to seat passengers while satisfying any group or window seat
	 * preferences. Takes number of currently open (available) rows in the plane, the group size,
	 * and how many window preferences are in the group as parameters.
	 */
	private int findSuitableRow(int availableRows, int groupSize, int numWindowPreferences) {
		
		//go over the open (available) rows in plane
		for(int currentRow = 0; currentRow <= availableRows; currentRow++) {
			
			if(groupSize <= rowDescriptors.get(currentRow).getAvailableSeats()) { //if group fits in row
				
				if(numWindowPreferences > 0) { //if there are any window preferences
					
					//Passengers already fit in the inspecting row and we know they have window preferences
					//Thus, if there are available window seats, seat them. Also if we have inspected all possible 
					//rows in the plane and there was no available window seat then we have to seat them here
					if( numWindowPreferences <= rowDescriptors.get(currentRow).getAvailableWindowSeats() || 
					    availableRows >= numRows) {
							return(currentRow);
					}
					
				}else { //there were no window preferences so we can seat passengers in current 
					    //inspecting row since they fit
					return(currentRow);
				}
				
				
			}
		}
		
		//no currently available seat was found to satisfy the preferences
		return NO_SUITABLE_ROW;
	}
	
	
	/*
	 * Seats a Passenger in a given row. Takes a row number and a passenger as parameters
	 */
	private void fitPassengerInCurrentRow(int currentRow, Passenger currentPassenger) {
		
		//decease the number of available seats in the given row
		rowDescriptors.get(currentRow).updateAvailableSeats(-1);
		
		if(currentPassenger.hasWindowPreference()) { //passenger has a window seat preference
			
			
			if(rowDescriptors.get(currentRow).getAvailableWindowSeats() > 0) { //if there are available window seats
				
				//update row information as appropriate
				rowDescriptors.get(currentRow).updateAvailableWindowSeats(-1);
				
				//update passenger satisfaction as appropriate. Even though they got seated, if they were separated
				//form their group, they only get half satisfaction.
				updateSatisfafction(currentRow, currentPassenger, 
						            currentPassenger.isSeparatedFromGroup()? HALF_SATISFACTION:FULL_SATISFACTION);
				
			}else { //no available window seats in the given row
				
				//if passenger was traveling with a group and has not been separated from them, they still get
				//half satisfaction even if their window seat wish was not satisfied.
				if(currentPassenger.isPassengerInGroup() && !currentPassenger.isSeparatedFromGroup()) {
					updateSatisfafction(currentRow, currentPassenger, HALF_SATISFACTION);
				}
			}
			
		}else { //update satisfaction of passengers that did not have window preferences. They are fully
			    //satisfied unless they were separated from their original group
			updateSatisfafction(currentRow, currentPassenger, 
					            currentPassenger.isSeparatedFromGroup()? NO_SATISFACTION:FULL_SATISFACTION);
		}
		
		//place the passenger on the seat in the given row
		planeRows.get(currentRow).add(currentPassenger);
	}
	
	/*
	 * Updates the passenger and row satisfaction. Takes as parameters a passenger and the 
	 * satisfaction value to be used from updating their satisfaction score.
	 */
	private void updateSatisfafction(int currentRow, Passenger passenger, double satisfaction) {
		passenger.updateSatisfaction(satisfaction);
		rowDescriptors.get(currentRow).updateRowSatisfaction(satisfaction);
	}
	
	/*
	 * Returns the final sitting configuration while performing some cleanup.
	 * Cleanup includes moving passengers with window seats to the sides of the row and removing rows that exceed
	 * the size of the plane.
	 */
	private List<List<Passenger>> getSeatConfiguration() {
		
		//number of available windows in the plane
		int windowsAvailable;
		
		//indicates the window seat position in the row
		int windowPosition;
		
		//passenger to be swapped so we can move a passenger with window seat preference in the window
		Passenger temporaryPasseneger;
		
		//remove any unnecessary plane rows
		while(planeRows.size() > numRows) {
			planeRows.remove(planeRows.size()-1);
		}
		
		//move passengers with window seat preference to the sides of the row
		for(List<Passenger> currentRow: planeRows) {
			windowsAvailable = numWindowsInPlane;
			windowPosition = 0;
			
			for(int currentPassengerPosition = 0; currentPassengerPosition < currentRow.size(); currentPassengerPosition++) {
				
				//swap passengers with window seat preference and move them to the sides if possible 
				if(currentRow.get(currentPassengerPosition).hasWindowPreference() && windowsAvailable > 0) {
				    windowPosition = (windowsAvailable == numWindowsInPlane)? 0:currentRow.size()-1;
					temporaryPasseneger = currentRow.get(windowPosition);
					currentRow.set(windowPosition, currentRow.get(currentPassengerPosition));
					currentRow.set(currentPassengerPosition, temporaryPasseneger);
					windowsAvailable--;
				}
				
				
			}
		}
		
		//return the final sitting configuration of the passengers in the plane
		return planeRows;
	}
	
	/*
	 * Returns the total passenger satisfaction
	 */
	private String getSatisfaction() {
		
		//total passenger satisfaction
		double satisfaction = 0;
		
		//passenger count
		int totalPassengers = passengerCount;
		
		//accumulate passenger satisfaction obtained from each row
		for(int currentRow = 0; currentRow < numRows; currentRow++) {
			satisfaction += rowDescriptors.get(currentRow).getRowSatisfaction();
			totalPassengers-= numSeatsInRow;
			if(totalPassengers <= 0) {
				break;
			}
		}
		
		//return customer satisfaction formatted to two decimal places
		return (new DecimalFormat(".##").format(satisfaction / passengerCount * 100) + "%");
	}
	
	/*
	 * Sort passenger groups by prioritizing larger groups that would yield higher satisfaction. If two groups 
	 * have the same number of passengers, the group with more window seat preferences will have priority. 
	 *  
	 */
	private List<List<Passenger>> sortPassengerList(List<List<Passenger>> passengerGroups){
		
		Collections.sort(passengerGroups, new Comparator<List<Passenger>>() {    
	        @Override
	        public int compare(List<Passenger> passengerGroup1, List<Passenger> passengerGroup2) {
	        		
	        		//value to check if two groups have the same size
	        		int result = passengerGroup2.size() - passengerGroup1.size();
	        		
	        		//number of window preferences for group 1
	        		int group1NumWindowPrefs;
	        		
	        		//number of window preferences for group 2
        			int group2NumWindowPrefs;
	        		
	        		if(result == 0) { //if two passenger groups have the same size, prioritize the group with
	        			             //more window seat preferences
	        			group1NumWindowPrefs = 0;
	        			group2NumWindowPrefs = 0;
	        			
	        			//count window preferences of group 1
	        			for(Passenger pass: passengerGroup1) {
	        				if(pass.hasWindowPreference()) {
	        					group1NumWindowPrefs++;
	        				}
	        			}
	        			
	        			//count window preferences of group 2	        			
	        			for(Passenger pass: passengerGroup2) {
	        				if(pass.hasWindowPreference()) {
	        					group2NumWindowPrefs++;
	        				}
	        			}
	        			
	        			return group2NumWindowPrefs - group1NumWindowPrefs;
	        		}else{
	        			return passengerGroup2.size() - passengerGroup1.size();
	        		}
	        }               
		});
		
		return passengerGroups;
	}
	
	
	
}
