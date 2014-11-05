package fr.iut_blagnac.data.util;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

public abstract class OptiElement {
	protected static DataManager currentDataManager;
	
	public final static Color ERROR_COLOR = new Color(255, 200, 200);
	public final static Color DEFAULT_COLOR = UIManager.getColor("TextField.background");
	
	public static void setDataManager(DataManager manager){
		currentDataManager = manager;
	}

	/**
	 * Return the name of the object
	 */
	public abstract String toString();
	
	
	/**
	 * Check the validity of the object
	 * @return : true if the data is valid or false if the data isn't valid
	 */
	public abstract boolean isValid();

	/** 
	 * @return : the data ready to be put in a csv file
	 */
	public abstract String[] toArray();

	/** 
	 * @return : the data ready to be put in a csv file
	 */
	public static String[] getCSVHeader(){
		return new String[0];
	}
	
	/** 
	 *  the data ready to be put in a csv file
	 * @param csvRow : Array containing a line of a CSV file
	 */
	public abstract void loadFromArray(String[] csvRow);
	


	

	public abstract OptiElementRepresentation buildOptiElementRepresentation();
	


	public abstract boolean equalsElement(Object o);
	public abstract OptiElement clone();

	public abstract String getValueFor(String champ);

	public abstract JDialog buildDetails(JFrame application, DataManager dataManager);
}
