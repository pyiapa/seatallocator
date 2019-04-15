package com.pyiapa.seatallocator.application;

import java.util.List;

import com.pyiapa.seatallocator.domain.Passenger;
import com.pyiapa.seatallocator.domain.SittingArrangement;
import com.pyiapa.seatallocator.services.SeatAllocator;
import com.pyiapa.seatallocator.utilities.PassengerParser;

/**
 * The driver class of an application that simulates passenger sitting configuration in
 * a plane while trying to optimize and increase passenger satisfaction
 * 
 * @author pyiapa
 *
 */
public class Main {

	/**
	 * 
	 * Drives the application that simulates passenger sitting configuration in a plane
	 * 
	 * @param args Input file with passenger preferences and plane dimensions
	 */
	public static void main(String[] args) {

		// the utility that parses the input to extract customer preferences and plane
		// dimensions
		PassengerParser inputParser = new PassengerParser();

		//the service that performs the seating allocation based on passenger satisfaction
		SeatAllocator seatAllocator;
		
		// holds information about the final sitting configuration and total passenger
		// satisfaction
		SittingArrangement sittingArrangement;

		// the final passenger group sitting configuration
		List<List<Passenger>> seatConfiguration;

		try {
			// parse input file to extract passenger preferences and plane dimensions
			inputParser.parseInputFile(args[0]);
		}

		// this will be triggered in a case where the input is missing or invalid
		catch (IllegalArgumentException e) {

			// print specific error and terminate the application
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		//initialize the service that will perform the seat allocation by providing passengers and their preferences
		//as well as plane dimension information
		seatAllocator = new SeatAllocator( inputParser.getPassengerGroups(), inputParser.getNumRows(),
				 					      inputParser.getNumSeatsInRow(), inputParser.getPassengerCount(), 
				 						  inputParser.getNumWindowsInPlane());
		
		//perform the seat allocation and get the final sitting configuration and satisfaction
		sittingArrangement = seatAllocator.allocateSeats();
		
		//get the final passenger sitting configuration on the plane
		seatConfiguration = sittingArrangement.getSeatConfiguration();
		
		//output the final sitting configuration. Passengers with window preferences satisfied will appear on the
		//sides of the row
		for (int currentRow = 0; currentRow < seatConfiguration.size(); currentRow++) {
			for (int currentPassenger = 0; currentPassenger < seatConfiguration.get(currentRow).size(); currentPassenger++) {
				System.out.print(seatConfiguration.get(currentRow).get(currentPassenger).getPassengerID() + " ");
			}
			System.out.println();
		}
		
		//output the total passenger satisfaction
		System.out.println(sittingArrangement.getSatisfaction());

	}

}
