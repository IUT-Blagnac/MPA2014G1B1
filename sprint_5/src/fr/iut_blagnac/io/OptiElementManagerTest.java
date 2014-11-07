package fr.iut_blagnac.io;


import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Test;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.OptiElement;

public class OptiElementManagerTest extends TestCase {
	
	
	/**
	 * Function to check if to arrays of the String are equals
	 * @param array1 : first array of String
	 * @param array2 : first array of String
	 * @return true if the arrays are equal, false if they aren't
	 */
	private static boolean arraysEqual(Object[]array1, Object[] array2){

		if(array1 == null || array2 == null || array1.length!= array2.length)
			return false;
		
		for (int i = 0; i < array1.length; i++) {
				if(!((OptiElement) array1[i]).equalsElement(array2[i])){
					System.out.println(i + "\n" + (OptiElement) array1[i] + "\n" + (OptiElement) array2[i]);
					return false;
				}
		}
		return true;
	}
	
	/**
	 * Function to generate a random array of a random size
	 * @return The random array
	 */
	private static OptiElement[] generateRandomArray(int type){
		String characters = "azertyuiopmlkjhgfdsqwxcvbnNBVCXWQSDFGHJKLMPOIUYTREZA-";
		OptiElement[] result = new OptiElement[100];
		
		switch(type){
		case 0: 
			char firstName[], lastName[];
			for(int n = 0; n < result.length; n++){
				firstName = new char[(int) (Math.random()*9+2)];
				lastName = new char[(int) (Math.random()*9+2)];
				
				for (int i = 0; i < firstName.length; i++)
					firstName[i] = characters.charAt((int) (Math.random() * characters.length()));
				for (int i = 0; i < lastName.length; i++)
					lastName[i] = characters.charAt((int) (Math.random() * characters.length()));
					
				result[n] = new Etudiant(new Groupe("C"), "0", new String(firstName), new String(lastName));
			}
			break;
		case 1: 
			for(int n = 0; n < result.length; n++){
				result[n] = new Sujet();
			}
			break;
		case 2: 
			for(int n = 0; n < result.length; n++){
				result[n] = new Intervenant();
			}
			break;
		case 3: 
			for(int n = 0; n < result.length; n++){
				result[n] = new Projet(new Sujet(), new Groupe());
			}
			break;
		}
		return result;
	}
	

	@Test
	public void test_saveNLoad() {
		System.out.println("Test of the saving and the opening of a CSV file") ;
		
		// Generation of a random array
		OptiElement[] data = generateRandomArray(0);
		
		// File used to store and load the csv file
		File csvFile = new File("./junit_sample.csv");
		
		try {
			// Save of the csv data
			OptiElementManager.save(null, csvFile,  data);
			
			// Load of the csv data
			OptiElement[] output = OptiElementManager.open(null, csvFile);
			
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
		junit.textui.TestRunner.run(new TestSuite(OptiElementManagerTest.class)); 
	}


}
