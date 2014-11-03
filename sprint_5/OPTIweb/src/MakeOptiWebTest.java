import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	
	protected void setUp() throws IOException {	
		executionProgrammeATester = Runtime.getRuntime().exec("java.exe -cp .;bin "+programeATester); 
	}
	
	public void test_File() throws IOException {
		File f = new File("OPTIweb.html");
		
		assertTrue(f.exists());
	}
}
