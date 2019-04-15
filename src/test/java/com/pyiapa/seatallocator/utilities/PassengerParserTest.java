package com.pyiapa.seatallocator.utilities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;



import com.pyiapa.seatallocator.utilities.PassengerParser;

/**
 * 
 * Tests different valid and invalid inputs and ensures that PassengerParser works as intended
 * as well as handles all exceptions appropriately.
 * 
 * @author pyiapa
 *
 */
public class PassengerParserTest {
	
	private PassengerParser inputParser;
	private final static String TEST_INPUT_FILE_PATH = "src/test/resources/input/Input.Normal";
	private final static String EMPTY_FILE_FILE_PATH = "src/test/resources/input/Input.InvalidEmptyFile";
	private final static String INVALID_DIMS_FILE_PATH = "src/test/resources/input/Input.InvalidDimensions";
	private final static String INVALID_DIMS_FORMAT_FILE_PATH = "src/test/resources/input/Input.InvalidDimensionsFormat";
	private final static String DIMS_MISSING_FILE_PATH = "src/test/resources/input/Input.InvalidDimensionsMissing";
	private final static String INVALID_PASSENGERS_MISSING_FILE_PATH = "src/test/resources/input/Input.InvalidPassengersMissing";
	private final static String INVALID_PASSENGER_FORMAT_FILE_PATH = "src/test/resources/input/Input.InvalidPassengerFormat";
	private final static int NUM_ROWS = 4;
	private final static int NUM_SEATS_PER_ROW = 4;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setup() {
		 inputParser = new PassengerParser();
	}
	
	@Test
	public void testNullPointerOnInputFile() {
		
		expectedEx.expect(IllegalArgumentException.class);
		
		expectedEx.expectMessage("Please provide Input File");
		inputParser.parseInputFile(null);
		
	}
	
	@Test
	public void testFileDoesNotExist() {
		
		expectedEx.expect(IllegalArgumentException.class);
		
		expectedEx.expectMessage("Please provide Input File");
		inputParser.parseInputFile("");
		
	}
	
	@Test
	public void testEmptyFile() {
		
		expectedEx.expect(IllegalArgumentException.class);
		
		expectedEx.expectMessage("Input file is empty");
		inputParser.parseInputFile(EMPTY_FILE_FILE_PATH);
		
	}
	
	@Test
	public void testIvalidPlaneDimensions() {
		
		expectedEx.expect(IllegalArgumentException.class);
		
		expectedEx.expectMessage("Plane dimensions must be >= 1");
		inputParser.parseInputFile(INVALID_DIMS_FILE_PATH);
		
	}
	
	@Test
	public void testIvalidPlaneDimensionsFormat() {
		
		expectedEx.expect(IllegalArgumentException.class);
		
		expectedEx.expectMessage("Invalid input: plane dimensions must be in numeric format");
		inputParser.parseInputFile(INVALID_DIMS_FORMAT_FILE_PATH);
		
	}
	
	@Test
	public void testPlaneDimensionsMissing() {
		
		expectedEx.expect(IllegalArgumentException.class);
		
		expectedEx.expectMessage("Please provide valid plane dimensions");
		inputParser.parseInputFile(DIMS_MISSING_FILE_PATH);
		
	}
	
	@Test
	public void testValidPlaneDimensions() {
		
		inputParser.parseInputFile(TEST_INPUT_FILE_PATH);
		
		assertTrue(inputParser.getNumRows() == NUM_ROWS);
		assertTrue(inputParser.getNumSeatsInRow() == NUM_SEATS_PER_ROW);
		
	}
	
	@Test
	public void testPassengersMissing() {
		
		expectedEx.expect(IllegalArgumentException.class);
		
		expectedEx.expectMessage("Input must have at least one passenger");
		inputParser.parseInputFile(INVALID_PASSENGERS_MISSING_FILE_PATH);
		
	}
	
	@Test
	public void testInvalidPassengerFormat() {
		
		expectedEx.expect(IllegalArgumentException.class);
		
		expectedEx.expectMessage("Error while parsing passengers. Invalid passenger format.");
		inputParser.parseInputFile(INVALID_PASSENGER_FORMAT_FILE_PATH);
		
	}
	

	
	@After
	public void tearDown() {
		inputParser = null;
	}

	
}
