package fr.iut_blagnac.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MakeOptiWebTest extends TestCase {

	MakeOptiWeb maker = new MakeOptiWeb();
	Process executionProgrammeATester ; 
	static String programeATester = "MakeOptiWeb";
	

	

	public static void main(String[] args) throws UnsupportedEncodingException,
			FileNotFoundException {
	
		if ( args.length > 0 ) { 
			programeATester = args[0] ; 
		}
		System.out.println("Tests du programme : MakeOptiWeb.java");
		junit.textui.TestRunner.run(new TestSuite(MakeOptiWeb.class));

	}
	
	protected void setUp() throws IOException, InterruptedException {	
		executionProgrammeATester = Runtime.getRuntime().exec("java.exe -cp .;bin "+programeATester); 
		executionProgrammeATester.waitFor();
	}
	
	public void test_File() throws IOException {
		File f = new File("OPTIweb.html");
		
		assertTrue(f.exists());
	}
}
