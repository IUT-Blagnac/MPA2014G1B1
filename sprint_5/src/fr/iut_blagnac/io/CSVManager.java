package fr.iut_blagnac.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVManager {
	
	/**
	 * Function to read a CSV file as a 2D Array
	 * @param csvFile : File to load
	 * @param separator : Cell separator
	 * @return The CSV content in an array
	 * @throws FileNotFoundException If the file couldn't be opened
	 */
	public static String[][] openCSV(File csvFile, char separator) throws FileNotFoundException {
		// List used to store the CSV file content
		List<String[]> result = new ArrayList<String[]>();
		// Scanner used to read the file content
		Scanner fileReader = new Scanner(csvFile, "UTF-8");
		
		// Current line in the CSV file
		String currentLine;
		// Current row content int the CSV data
		String[] lineData;
		
		// Loop used to march the CSV file
		while(fileReader.hasNext()){
			// Read of the current line in the CSV file
			currentLine = fileReader.nextLine();
			// Retrieval of the row data
			lineData = currentLine.split("" + separator);
			for(int i = 0; i < lineData.length; i++){
				lineData[i] = lineData[i].trim();
			}
			// Storage of the row data
			result.add(lineData);
		}
		// Close of the CSV reader
		fileReader.close();
		
		// Conversion from List to Array and return of the data
		return result.toArray(new String[result.size()][]);
	}
	
	
	/**
	 * Function to write an array to a CSV file
	 * @param csvFile : File to write to
	 * @param separator : Cell separator
	 * @param header : Columns names
	 * @param data : Array to write in the CSV file
	 * @throws FileNotFoundException If the file couldn't be created
	 * @throws IOException If the file couldn't be saved
	 */
	public static void saveCSV (File csvFile, char separator, String[] header, String[][] data) throws FileNotFoundException, IOException {
		// Writer used to write the data to the CSV file
		OutputStreamWriter csvFileStream = new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8");
		
		// Check of the existence of a header
		if (header != null && header.length != 0) {
			// Write of the first cell of the header
			csvFileStream.write(header[0]);
			
			// Write of the other cells with a seperator at the beginning
			for (int i = 1; i < header.length; i++) {
				csvFileStream.write(separator);
				csvFileStream.write(header[i]);
			}
			
			// Write of the carriage return
			csvFileStream.write('\n');
		}
		
		for (int i = 0; i < data.length; i++) {
			// Write of the first cell of the row
			if(data[i].length !=0){
				if(data[i][0].length()!=0){
					csvFileStream.write(data[i][0]);
				} else {
					csvFileStream.write(" ");
				}
			}
			
			// Write of the other cells with a seperator at the beginning
			for (int j = 1; j < data[i].length; j++) {
				csvFileStream.write(separator);
				if(data[i][j].length()!=0){
					csvFileStream.write(data[i][j]);
				} else {
					csvFileStream.write(" ");
				}
			}
			
			// Write of the carriage return
			csvFileStream.write('\n');
		}

		// Close of the CSV Writer
		csvFileStream.close();
	}
	
}
