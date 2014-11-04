package fr.iut_blagnac.io;


import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Test;

public class CSVManagerTest extends TestCase {

	
	/**
	 * Function to check if to arrays of the String are equals
	 * @param array1 : first array of String
	 * @param array2 : first array of String
	 * @return true if the arrays are equal, false if they aren't
	 */
	private static boolean arraysEqual(String[][] array1, String[][] array2){

		if(array1 == null || array2 == null || array1.length!= array2.length)
			return false;
		
		for (int i = 0; i < array1.length; i++) {
			if(array1[i].length!= array2[i].length)
				return false;
			for (int j = 0; j < array1[i].length; j++) {
				if(!array1[i][j].equals(array2[i][j]))
					return false;
			}		
		}
		return true;
	}

	

	
	/**
	 * Function to generate a random array of a random size
	 * @return The random array
	 */
	private static String[][] generateRandomArray(){

		String[][] data = new String[(int) (Math.random()*10)][(int) (Math.random()*10)];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = String.valueOf((int)(Math.random()*10.));
			}		
		}
		return data;
	}
	

	@Test
	public void test_saveNLoad() {
		System.out.println("Test of the saving and the opening of a CSV file") ;
		
		// Generation of a random array
		String[][] data = generateRandomArray();
		
		// File used to store and load the csv file
		File csvFile = new File("./junit_sample.csv");
		
		try {
			// Save of the csv data
			CSVManager.saveCSV(csvFile, ';', null, data);
			
			// Load of the csv data
			String[][] output = CSVManager.openCSV(csvFile, ';');
			
			// Check of the succes of the saving and the opening
			try{
				assertTrue(arraysEqual(data, output));
				System.out.println("SaveNLoad test passed!");
				return;
			}catch(AssertionError e){
				throw new AssertionError("Failure of the saveNLoad test!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setUp(){
		System.out.println("Test set up");
	}
	
	
	
	public static void main(String[] args){
	junit.textui.TestRunner.run(new TestSuite(CSVManagerTest.class)); 
 }


}
