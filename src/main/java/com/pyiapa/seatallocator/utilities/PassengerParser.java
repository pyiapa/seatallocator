package com.pyiapa.seatallocator.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.pyiapa.seatallocator.domain.Passenger;

/**
 * Provides a utility for parsing a file with passenger sitting preferences
 * and airplane dimension information
 * 
 * @author pyiapa
 *
 */
public class PassengerParser {
	
	//number of rows in the plane
	private int numRows;
	
	//number of seats in each row
	private int numSeatsInRow;
	
	//total passenger count
	private int passengerCount;
	
	//number of windows (per row) in the plane
	private final int NUM_WINDOWS_IN_PLANE = 2;
	
	//whether a passenger has a window seating preference
	private final boolean HAS_WINDOW_PREFERENCE = true;
	
	//whether a passenger belongs to a group of passengers
	private final boolean PASSENGER_IN_GROUP = true ;
	
	//a 2D list that holds groups of passengers - each element is a list representing
	//a group of passengers that want to fly together
	private List<List<Passenger>> passengerGroups;
	
	/**
	 * Parses an input file with passenger sitting preferences
	 * and airplane dimension information
	 * 
	 * @param input File that contains passenger sitting preferences and plane dimensions
	 * 
	 * @throws IllegalArgumentException Exception thrown on invalid or missing input
	 */
	public void parseInputFile(String input) throws IllegalArgumentException {
		
		//holds the contents of the input file
		Scanner inputReader;
		
		//a line in the input file
		String fileLine;
		
		//plane dimensions
		String[] planeDimensions;
		
		//a group of passengers read directly from the input file. Until parsed correctly
		//it's still a potential group
		String[] potentialPassengerGroup;
		
		//list that holds a group of passengers wishing to seat together
		List<Passenger> passengerGroup;
		
		
		try {
			//get input file
			inputReader = new Scanner(new File(input));
			
			if(!inputReader.hasNextLine()) { //if file is empty
				inputReader.close();
				throw new IllegalArgumentException("Input file is empty");
			}
	
		}catch (FileNotFoundException | ArrayIndexOutOfBoundsException | NullPointerException e) {
			throw new IllegalArgumentException("Please provide Input File");
		}
        
		//read plane dimensions
		fileLine = inputReader.nextLine();
		
		//split dimensions list to extract number of rows and number of seats per row
		planeDimensions = fileLine.split("[\\s+]");
		
		try {
			
			if(planeDimensions.length != 2) { //at least one plane dimension is missing
				inputReader.close();
				throw new IllegalArgumentException("Please provide valid plane dimensions");
				
			}else {
				//consume plane dimensions
				numRows = Integer.parseInt(planeDimensions[0]);
				numSeatsInRow = Integer.parseInt(planeDimensions[1]);
			}
				
		}catch(NumberFormatException e) {
			inputReader.close();
			throw new IllegalArgumentException("Invalid input: plane dimensions must be in numeric format");
		}
		
		//check for valid plane dimensions
		if(numSeatsInRow < 1 || numRows < 1) {
			inputReader.close();
			throw new IllegalArgumentException("Plane dimensions must be >= 1");
		}
		
		//make sure there are some passengers in the file
		if(!inputReader.hasNextLine()) {
			inputReader.close();
			throw new IllegalArgumentException("Input must have at least one passenger");
		}
		
		//initialize list that will hold the passenger groups
		passengerGroups = new ArrayList<List<Passenger>>();
		
		
		//read passengers
		while (inputReader.hasNextLine()) {
			
			//get a passenger group
			fileLine = inputReader.nextLine();
			potentialPassengerGroup = fileLine.split("[\\s+]");
			
			passengerGroup = new ArrayList<Passenger>();
			
			//process a given passenger
			for(String passenger: potentialPassengerGroup) {
				
				if(passenger.matches("\\d+")) { //passenger with no window preference
					
					//create a new passenger and add them to their group. Indicate whether they have window preference seat
					//and if they are part of a group or flying alone
					passengerGroup.add(new Passenger(Integer.parseInt(passenger), 
							        	   !HAS_WINDOW_PREFERENCE, 
							           (potentialPassengerGroup.length == 1)? !PASSENGER_IN_GROUP:PASSENGER_IN_GROUP));
					
				} else if(passenger.matches("\\d+[W]")) { //passenger with window preference
					
					//create a new passenger and add them to their group. Indicate whether they have window preference seat
					//and if they are part of a group or flying alone
					passengerGroup.add(new Passenger(Integer.parseInt(passenger.substring(0, passenger.length()-1)),
							           HAS_WINDOW_PREFERENCE, 
							           (potentialPassengerGroup.length == 1)? !PASSENGER_IN_GROUP:PASSENGER_IN_GROUP));
					
				}else { //invalid input for passenger
					inputReader.close();
					throw new IllegalArgumentException("Error while parsing passengers. Invalid passenger format.");
				}
				
				//update total passenger count
				passengerCount++;
			}
			
			//sort a given passenger group by window preference in descending order. This makes it easier to satisfy window
			//preferences first when we are sitting the passengers later
			Collections.sort(passengerGroup, new Comparator<Passenger>() {    
		        @Override
		        public int compare(Passenger passenger1, Passenger passenger2) {
		        			return passenger1.hasWindowPreference()? -1:1;
		        }               
			});
			
			//make sure there is at least a passenger in the group before adding it to the final passenger list
			if(passengerGroup.size() > 0) {
				passengerGroups.add(passengerGroup);
			}
			

        }//end of while loop for reading lines of input
        
        inputReader.close();
	}
	
	/**
	 * Informs about the number of seats per row in the plane
	 * 
	 * @return Number of seats per row
	 */
	public int getNumSeatsInRow() {
		return numSeatsInRow;
	}

	/**
	 * Informs about the number of rows in the plane
	 * 
	 * @return Number of rows in the plane
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * Returns the total passenger count.
	 * 
	 * @return Total passenger count
	 */
	public int getPassengerCount() {
		return passengerCount;
	}
	
	/**
	 * Returns the passenger groups
	 * 
	 * @return Passenger groups
	 */
	public List<List<Passenger>> getPassengerGroups() {
		return passengerGroups;
	}
	
	/**
	 * Gets the number of windows (per row) in the plane
	 * 
	 * @return
	 */
	public int getNumWindowsInPlane() {
		return NUM_WINDOWS_IN_PLANE;
	}
	
}
