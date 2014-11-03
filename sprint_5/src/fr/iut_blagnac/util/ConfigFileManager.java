package fr.iut_blagnac.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import fr.iut_blagnac.gui.Application;


/**
 * Allows to manage the .lang files, containing the Strings used in the application.
 * @author Groupe 1B1
 *  v1.0
 *  Sprint 2
 */
public class ConfigFileManager {
	
	/**
	 * Loads a .lang file which contains the Strings of the application.
	 * @param language : The language the application is currently in.
	 * @return The Strings to be used in the application, in a Map. The keys are the expressions before the ':' on each line, and the values are the expressions after the ':'.
	 * @throws FileNotFoundException If the .lang file couldn't be loaded
	 * @throws IOException If there was a problem when reading the file
	 */
	public static Map<String, String> loadLangFile (String language) throws IOException {
		Map<String, String> data = new HashMap<String, String>();
		String actualLine, actualKey, actualValue;
		
		File langFile = new File("langs/" + language + ".lang");
		BufferedReader langFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(langFile), Charset.forName("UTF-8")));
		
		while ((actualLine = langFileReader.readLine()) != null) {
			if(!actualLine.startsWith("#") && actualLine.contains(":")){
				actualKey = actualLine.substring(0, actualLine.indexOf(':'));
				actualValue = actualLine.substring(actualLine.indexOf(':') +1, actualLine.length());
				data.put(actualKey, actualValue);
			}
		}
		
		langFileReader.close();
		
		if (data.get("dialyes") == null) data.put("dialyes", "Yes");
		if (data.get("dialno") == null) data.put("dialno", "No");
		
		return data;
	}
	
	/**
	 * Loads init.conf file which contains the parameters with which the application is to be launched.
	 * @return The parameters included in init.conf, in a Map. The keys are the expressions before the ':' on each line, and the values are the expressions after the ':'.
	 * @throws IOException If there was a problem when reading the file
	 */
	public static Map<String, String> loadConfFile () throws IOException {
		Map<String, String> data = new HashMap<String, String>();
		String actualLine, actualKey, actualValue;
		
		File confFile = new File("config/init.conf");
		
		try {
			BufferedReader confFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(confFile), Charset.forName("UTF-8")));
			
			while ((actualLine = confFileReader.readLine()) != null) {
				actualKey = actualLine.substring(0, actualLine.indexOf(':'));
				actualValue = actualLine.substring(actualLine.indexOf(':') +1, actualLine.length());
				data.put(actualKey, actualValue);
			}
			
			confFileReader.close();
			
			if (!data.containsKey("language")) throw new IOException("Language not found in the config file!");			
		}
		catch (FileNotFoundException e) {
			data.put("language", "english");
			data.put("sujets", "sujets2014_2015.csv");
			data.put("projets", "projets2014_2015.csv");
			data.put("etudiants", "etudiants2014_2015.csv");
			data.put("intervenants", "intervenants2014_2015.csv");

			BufferedWriter confFileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(confFile), Charset.forName("UTF-8")));
			
			for (String s : data.keySet())
				confFileWriter.write(s + ":" + data.get(s) + "\n");
			
			confFileWriter.close();
		}
		
		return data;
	}
	
	public static void changeLanguage(String language) throws IOException {
		Application.confString.put("language", language);
		
		File confFile = new File("config/init.conf");
		
		BufferedWriter confFileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(confFile), Charset.forName("UTF-8")));
		
		for (String s : Application.confString.keySet())
			confFileWriter.write(s + ":" + Application.confString.get(s) +"\n");
		
		confFileWriter.close();
	}
}
