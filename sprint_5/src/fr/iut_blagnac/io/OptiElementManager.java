package fr.iut_blagnac.io;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.OptiElement;

public class OptiElementManager {
	
	public static void save (Component parent, File fileName, String[] header, OptiElement[] elements) throws FileNotFoundException, IOException {
		String[][] data = new String[elements.length][3];
		
		for (int i = 0; i < elements.length; i++){
			if(elements[i].isValid()){
				data[i] = elements[i].toArray();
			} else {
				JOptionPane.showMessageDialog(parent, "Data invalid for :\n" + elements[i].toString(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		CSVManager.saveCSV(fileName, ';', header, data);		
	}

	public static void save(JComponent parent, File selectedFile, OptiElement[] data) throws FileNotFoundException, IOException {
		Class<? extends OptiElement> firstClass = data[0].getClass();
		
		boolean homogene = true;
		for(OptiElement element : data){
			if(!element.getClass().equals(firstClass)){
				homogene = false;
				break;
			}
		}
		
		if(homogene){
			 save(parent, selectedFile, data[0].getCSVHeader(), data);
		} else {
			JOptionPane.showMessageDialog(parent, "Data not homogeneous, impossible to save", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static OptiElement[] open(Component parent, File fileName) throws FileNotFoundException {
		String[][] data = CSVManager.openCSV(fileName, ';');
		ArrayList<OptiElement> result = new ArrayList<OptiElement>();
		
		String type = "";
		if(CSVManagerTest.arraysEqual(data[0], Etudiant.getCSVHeader())){
			type = "Etudiant";
		} else if(CSVManagerTest.arraysEqual(data[0], Groupe.getCSVHeader())){
			type = "Groupe";
		} else if(CSVManagerTest.arraysEqual(data[0], Sujet.getCSVHeader())){
			type = "Sujet";
		} else if(CSVManagerTest.arraysEqual(data[0], Intervenant.getCSVHeader())){
			type = "Intervenant";
		} else if(CSVManagerTest.arraysEqual(data[0], Projet.getCSVHeader())){
			type = "Projet";
		}
		
		switch(type){
		case "Etudiant" : 
			for(int n = 1; n < data.length; n++){
				result.add(new Etudiant(data[n]));
			}
			break;
		case "Sujet" : 
			for(int n = 1; n < data.length; n++){
				result.add(new Sujet(data[n]));
			}
			break;
		case "Intervenant" : 
			for(int n = 1; n < data.length; n++){
				result.add(new Intervenant(data[n]));
			}
			break;
		case "Projet" : 
			for(int n = 1; n < data.length; n++){
				result.add(new Projet(data[n]));
			}
			break;
		default :
			JOptionPane.showMessageDialog(parent, "File invalid", "Error", JOptionPane.ERROR_MESSAGE);
		
			break;
		}
		return result.toArray(new OptiElement[result.size()]);
		
	}
}
