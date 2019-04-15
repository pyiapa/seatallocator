package com.pyiapa.seatallocator.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pyiapa.seatallocator.domain.Passenger;
import com.pyiapa.seatallocator.domain.SittingArrangement;
import com.pyiapa.seatallocator.services.SeatAllocator;


/**
 * 
 * Tests various sample inputs and ensures that SeatAllocator works as intended.
 * 
 * @author pyiapa
 *
 */
public class SeatAllocatorTest {
	
	
	/*
	 * Note that in the test cases below, passengers are added in the way that they would have been
	 * returned by the PassengerParser. That is, each group will be sorted in descending order of
	 * window seat preferences. 
	 */
	
	private List<List<Passenger>> passengerGroups;
	private List<List<Passenger>> seatConfiguration;
	private List<Passenger> passengerGroup;
	private SeatAllocator seatAllocator;
	private SittingArrangement sittingArrangement;
	
	private final static boolean WINDOW_PREFERENCE = true;
	private final static boolean PASSENGER_IN_GROUP = true;
	
	@Before
	public void setup() {
		passengerGroups = new ArrayList<List<Passenger>>();
		seatConfiguration = new ArrayList<List<Passenger>>();
	}
	
	
	
	@Test
	public void testNormalFittingGroups() {
		
		int numRows = 4;
		int numSeatsInRow = 4;
		int passengerCount = 16;
		int numWindowsInPlane = 2;
		String expectedSatisfaction = "100.0%";
		
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(1, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(2, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(3, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(4, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(5, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(6, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(7, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(8, !WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(11, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(9, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(10, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(12, WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(13, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(14, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(15, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(16, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
				
		seatAllocator = new SeatAllocator(passengerGroups, numRows, numSeatsInRow, passengerCount, numWindowsInPlane);
        sittingArrangement = seatAllocator.allocateSeats();
        
        assertEquals(expectedSatisfaction, sittingArrangement.getSatisfaction());
        
        seatConfiguration = sittingArrangement.getSeatConfiguration();
        
        assertEquals(4, seatConfiguration.get(0).get(0).getPassengerID());
        assertEquals(5, seatConfiguration.get(0).get(1).getPassengerID());
        assertEquals(6, seatConfiguration.get(0).get(2).getPassengerID());
        assertEquals(7, seatConfiguration.get(0).get(3).getPassengerID());
        
        assertEquals(1, seatConfiguration.get(1).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(1).get(0).hasWindowPreference());
        assertEquals(2, seatConfiguration.get(1).get(1).getPassengerID());
        assertEquals(3, seatConfiguration.get(1).get(2).getPassengerID());
        assertEquals(12, seatConfiguration.get(1).get(3).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(1).get(3).hasWindowPreference());
        
        assertEquals(11, seatConfiguration.get(2).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(2).get(0).hasWindowPreference());
        assertEquals(9, seatConfiguration.get(2).get(1).getPassengerID());
        assertEquals(10, seatConfiguration.get(2).get(2).getPassengerID());
        assertEquals(8, seatConfiguration.get(2).get(3).getPassengerID());
        
        assertEquals(13, seatConfiguration.get(3).get(0).getPassengerID());
        assertEquals(14, seatConfiguration.get(3).get(1).getPassengerID());
        assertEquals(15, seatConfiguration.get(3).get(2).getPassengerID());
        assertEquals(16, seatConfiguration.get(3).get(3).getPassengerID());
		
	}
	
	@Test
	public void testManyWindowPreferencesWithinGroups() {
		
		int numRows = 5;
		int numSeatsInRow = 3;
		int passengerCount = 15;
		int numWindowsInPlane = 2;
		String expectedSatisfaction = "36.67%";
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(1, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(2, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(3, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(4, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(7, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(6, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(5, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		
		passengerGroup.add(new Passenger(10, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(9, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(8, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(11, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(15, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(14, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(13, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(12, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		seatAllocator = new SeatAllocator(passengerGroups, numRows, numSeatsInRow, passengerCount, numWindowsInPlane);
        sittingArrangement = seatAllocator.allocateSeats();
        
        assertEquals(expectedSatisfaction, sittingArrangement.getSatisfaction());
        
        seatConfiguration = sittingArrangement.getSeatConfiguration();
       
        assertEquals(7, seatConfiguration.get(0).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(0).get(0).hasWindowPreference());
        assertEquals(5, seatConfiguration.get(0).get(1).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(0).get(1).hasWindowPreference());
        assertEquals(6, seatConfiguration.get(0).get(2).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(0).get(2).hasWindowPreference());
        
        assertEquals(9, seatConfiguration.get(1).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(1).get(0).hasWindowPreference());
        assertEquals(8, seatConfiguration.get(1).get(1).getPassengerID());
        assertEquals(10, seatConfiguration.get(1).get(2).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(1).get(2).hasWindowPreference());
        
        assertEquals(12, seatConfiguration.get(2).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(2).get(0).hasWindowPreference());
        assertEquals(11, seatConfiguration.get(2).get(1).getPassengerID());
        assertEquals(13, seatConfiguration.get(2).get(2).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(2).get(2).hasWindowPreference());
        
        assertEquals(14, seatConfiguration.get(3).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(3).get(0).hasWindowPreference());
        assertEquals(1, seatConfiguration.get(3).get(1).getPassengerID());
        assertEquals(15, seatConfiguration.get(3).get(2).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(3).get(2).hasWindowPreference());
        
        assertEquals(2, seatConfiguration.get(4).get(0).getPassengerID());
        assertEquals(3, seatConfiguration.get(4).get(1).getPassengerID());
        assertEquals(4, seatConfiguration.get(4).get(2).getPassengerID());
		
	}
	

	@Test
	public void testMorePassengersThanSeatingCapacity() {
		
		int numRows = 4;
		int numSeatsInRow = 4;
		int passengerCount = 23;
		int numWindowsInPlane = 2;
		String expectedSatisfaction = "69.57%";
	
	
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(1, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(2, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(3, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(4, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(5, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(6, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(7, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(8, !WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(11, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(9, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(10, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(12, WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(13, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(14, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
	
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(15, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(16, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(17, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(18, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(19, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(20, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(21, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(22, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(23, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		seatAllocator = new SeatAllocator(passengerGroups, numRows, numSeatsInRow, passengerCount, numWindowsInPlane);
        sittingArrangement = seatAllocator.allocateSeats();
        
        assertEquals(expectedSatisfaction, sittingArrangement.getSatisfaction());
        
        seatConfiguration = sittingArrangement.getSeatConfiguration();
       
        assertEquals(4, seatConfiguration.get(0).get(0).getPassengerID());
        assertEquals(5, seatConfiguration.get(0).get(1).getPassengerID());
        assertEquals(6, seatConfiguration.get(0).get(2).getPassengerID());
        assertEquals(7, seatConfiguration.get(0).get(3).getPassengerID());
        
        assertEquals(20, seatConfiguration.get(1).get(0).getPassengerID());
        assertEquals(21, seatConfiguration.get(1).get(1).getPassengerID());
        assertEquals(22, seatConfiguration.get(1).get(2).getPassengerID());
        assertEquals(23, seatConfiguration.get(1).get(3).getPassengerID());
        
        assertEquals(1, seatConfiguration.get(2).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(2).get(0).hasWindowPreference());
        assertEquals(2, seatConfiguration.get(2).get(1).getPassengerID());
        assertEquals(3, seatConfiguration.get(2).get(2).getPassengerID());
        assertEquals(12, seatConfiguration.get(2).get(3).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(2).get(3).hasWindowPreference());
        
        assertEquals(11, seatConfiguration.get(3).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(3).get(0).hasWindowPreference());
        assertEquals(9, seatConfiguration.get(3).get(1).getPassengerID());
        assertEquals(10, seatConfiguration.get(3).get(2).getPassengerID());
        assertEquals(8, seatConfiguration.get(3).get(3).getPassengerID());
        
	}
	
	@Test
	public void testMorePassengersThanSeattingCapacityLarger() {
		
		int numRows = 8;
		int numSeatsInRow = 4;
		int passengerCount = 40;
		int numWindowsInPlane = 2;
		String expectedSatisfaction = "78.75%";
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(4, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(3, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(1, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(2, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(6, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(5, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(9, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(7, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(8, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(10, WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(11, !WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(13, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(12, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(18, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(16, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(15, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(14, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(17, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(19, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(23, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(22, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(20, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(21, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(26, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(24, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(25, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(27, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(29, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(28, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(31, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(30, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(32, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(33, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(34, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(35, WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(36, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(37, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(40, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(38, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(39, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		seatAllocator = new SeatAllocator(passengerGroups, numRows, numSeatsInRow, passengerCount, numWindowsInPlane);
        sittingArrangement = seatAllocator.allocateSeats();
        
        assertEquals(expectedSatisfaction, sittingArrangement.getSatisfaction());
        
        seatConfiguration = sittingArrangement.getSeatConfiguration();
       
        assertEquals(4, seatConfiguration.get(0).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(0).get(0).hasWindowPreference());
        assertEquals(2, seatConfiguration.get(0).get(1).getPassengerID());
        assertEquals(1, seatConfiguration.get(0).get(2).getPassengerID());
        assertEquals(3, seatConfiguration.get(0).get(3).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(0).get(3).hasWindowPreference());
        
        assertEquals(23, seatConfiguration.get(1).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(1).get(0).hasWindowPreference());
        assertEquals(21, seatConfiguration.get(1).get(1).getPassengerID());
        assertEquals(20, seatConfiguration.get(1).get(2).getPassengerID());
        assertEquals(22, seatConfiguration.get(1).get(3).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(1).get(3).hasWindowPreference());
		
        assertEquals(26, seatConfiguration.get(2).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(2).get(0).hasWindowPreference());
        assertEquals(27, seatConfiguration.get(2).get(1).getPassengerID());
        assertEquals(25, seatConfiguration.get(2).get(2).getPassengerID());
        assertEquals(24, seatConfiguration.get(2).get(3).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(2).get(3).hasWindowPreference());
        
        assertEquals(9, seatConfiguration.get(3).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(3).get(0).hasWindowPreference());
        assertEquals(7, seatConfiguration.get(3).get(1).getPassengerID());
        assertEquals(8, seatConfiguration.get(3).get(2).getPassengerID());
        assertEquals(10, seatConfiguration.get(3).get(3).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(3).get(3).hasWindowPreference());
        
        assertEquals(31, seatConfiguration.get(4).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(4).get(0).hasWindowPreference());
        assertEquals(30, seatConfiguration.get(4).get(1).getPassengerID());
        assertEquals(32, seatConfiguration.get(4).get(2).getPassengerID());
        assertEquals(35, seatConfiguration.get(4).get(3).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(4).get(3).hasWindowPreference());
        
        assertEquals(40, seatConfiguration.get(5).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(5).get(0).hasWindowPreference());
        assertEquals(38, seatConfiguration.get(5).get(1).getPassengerID());
        assertEquals(39, seatConfiguration.get(5).get(2).getPassengerID());
        assertEquals(11, seatConfiguration.get(5).get(3).getPassengerID());
        
        assertEquals(13, seatConfiguration.get(6).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(6).get(0).hasWindowPreference());
        assertEquals(5, seatConfiguration.get(6).get(1).getPassengerID());
        assertEquals(6, seatConfiguration.get(6).get(2).getPassengerID());
        assertEquals(12, seatConfiguration.get(6).get(3).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(6).get(3).hasWindowPreference());
        
        assertEquals(29, seatConfiguration.get(7).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(7).get(0).hasWindowPreference());
        assertEquals(34, seatConfiguration.get(7).get(1).getPassengerID());
        assertEquals(33, seatConfiguration.get(7).get(2).getPassengerID());
        assertEquals(28, seatConfiguration.get(7).get(3).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(7).get(3).hasWindowPreference());
		
		
	}
	
	@Test
	public void testLessPassengersThanSeatingCapacity() {
		
		int numRows = 4;
		int numSeatsInRow = 4;
		int passengerCount = 11;
		int numWindowsInPlane = 2;
		String expectedSatisfaction = "100.0%";
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(1, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(2, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(3, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(4, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(5, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(6, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(9, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(7, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(8, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(10, WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(11, !WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		seatAllocator = new SeatAllocator(passengerGroups, numRows, numSeatsInRow, passengerCount, numWindowsInPlane);
        sittingArrangement = seatAllocator.allocateSeats();
        
        assertEquals(expectedSatisfaction, sittingArrangement.getSatisfaction());
        
        seatConfiguration = sittingArrangement.getSeatConfiguration();
       
        assertEquals(3, seatConfiguration.get(0).get(0).getPassengerID());
        assertEquals(4, seatConfiguration.get(0).get(1).getPassengerID());
        assertEquals(5, seatConfiguration.get(0).get(2).getPassengerID());
        assertEquals(6, seatConfiguration.get(0).get(3).getPassengerID());
        
        assertEquals(9, seatConfiguration.get(1).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(1).get(0).hasWindowPreference());
        assertEquals(7, seatConfiguration.get(1).get(1).getPassengerID());
        assertEquals(8, seatConfiguration.get(1).get(2).getPassengerID());
        assertEquals(10, seatConfiguration.get(1).get(3).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(1).get(3).hasWindowPreference());
        
        assertEquals(1, seatConfiguration.get(2).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(2).get(0).hasWindowPreference());
        assertEquals(2, seatConfiguration.get(2).get(1).getPassengerID());
        assertEquals(11, seatConfiguration.get(2).get(2).getPassengerID());
		
	}
	
	@Test
	public void testTinyPlaneWithMorePassengersThanSeatingCapacity() {
		
		int numRows = 1;
		int numSeatsInRow = 2;
		int passengerCount = 3;
		int numWindowsInPlane = 2;
		String expectedSatisfaction = "16.67%";
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(4, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(1, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(3, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		seatAllocator = new SeatAllocator(passengerGroups, numRows, numSeatsInRow, passengerCount, numWindowsInPlane);
        sittingArrangement = seatAllocator.allocateSeats();
        
        assertEquals(expectedSatisfaction, sittingArrangement.getSatisfaction());
        
        seatConfiguration = sittingArrangement.getSeatConfiguration();
       
        assertEquals(4, seatConfiguration.get(0).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(0).get(0).hasWindowPreference());
        assertEquals(1, seatConfiguration.get(0).get(1).getPassengerID());
     
	}
	
	@Test
	public void testNarrowPane() {
		
		int numRows = 10;
		int numSeatsInRow = 1;
		int passengerCount = 10;
		int numWindowsInPlane = 2;
		String expectedSatisfaction = "45.0%";
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(2, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(1, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(3, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(5, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(4, WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(6, !WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(7, !WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(8, !WINDOW_PREFERENCE, !PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		passengerGroup = new ArrayList<Passenger>();
		passengerGroup.add(new Passenger(9, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroup.add(new Passenger(10, !WINDOW_PREFERENCE, PASSENGER_IN_GROUP));
		passengerGroups.add(passengerGroup);
		
		seatAllocator = new SeatAllocator(passengerGroups, numRows, numSeatsInRow, passengerCount, numWindowsInPlane);
        sittingArrangement = seatAllocator.allocateSeats();
        
        assertEquals(expectedSatisfaction, sittingArrangement.getSatisfaction());
        
        seatConfiguration = sittingArrangement.getSeatConfiguration();
		
		assertEquals(6, seatConfiguration.get(0).get(0).getPassengerID());
        assertEquals(7, seatConfiguration.get(1).get(0).getPassengerID());
        assertEquals(8, seatConfiguration.get(2).get(0).getPassengerID());
        assertEquals(4, seatConfiguration.get(3).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(3).get(0).hasWindowPreference());
        assertEquals(5, seatConfiguration.get(4).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(4).get(0).hasWindowPreference());
        assertEquals(2, seatConfiguration.get(5).get(0).getPassengerID());
        assertEquals(WINDOW_PREFERENCE, seatConfiguration.get(5).get(0).hasWindowPreference());
        assertEquals(1, seatConfiguration.get(6).get(0).getPassengerID());
        assertEquals(3, seatConfiguration.get(7).get(0).getPassengerID());
        assertEquals(9, seatConfiguration.get(8).get(0).getPassengerID());
        assertEquals(10, seatConfiguration.get(9).get(0).getPassengerID());
        
	}
	
	@After
	public void tearDown() {
		passengerGroups = null;
		seatConfiguration = null;
		passengerGroup = null;
		seatAllocator = null;
		sittingArrangement = null;
	}
	

}
