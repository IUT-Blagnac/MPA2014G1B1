package fr.iut_blagnac.data.groupe;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.groupe.view.GroupeDetails;
import fr.iut_blagnac.data.groupe.view.GroupeRepresentation;
import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;
import fr.iut_blagnac.data.voeux.Voeux;

/**
 * This class is used to do operations on groups.
 * @author Groupe 1B1
 * @version 2.0
 */

public class Groupe extends OptiElement implements Comparable<Groupe>{

	// The id of the group
	private String libelle;
	
	// The list of students
	private List<Etudiant> listeEtudiant = new ArrayList<Etudiant>();
	
	// The project of the group
	private Projet projet;
	
	// The list of wishes
	private List<Voeux> listeVoeux = new ArrayList<Voeux>();
	
	

	private boolean libelleChanged = true;
	private boolean listeEtudiantChanged = true;
	private boolean projetChanged = true;
	private boolean listeVoeuxChanged = true;


	/**
	 * Creates a new group with the data specified.
	 * @param libelle : the label of the group
	 */
	public Groupe (String libelle) {
		this.setLibelle(libelle);
	}
	
	/**
	 * Creates a new group with the data specified.
	 * @param libelle : the label of the group
	 * @param listeEtudiants : the list of students
	 */
	public Groupe (String libelle, ArrayList<Etudiant> listeEtudiants) {
		this.setLibelle(libelle);
		this.setEtudiants(listeEtudiants);
	}
	
	/**
	 * Creates a new group with data load from CSV row
	 * @param csvRow : Array containing a line of a CSV file
	 */
	public Groupe(String[] csvRow) {
		// Init all information from CSV Row
		loadFromArray(csvRow);
	}
	
	/**
	 * Creates a new group without specifying the data.
	 */
	public Groupe () {
		// Call the Constructor Groupe(String, list<Etudiant>)
		this("", null);
	}
	
	/**
	 * Gets the id and the list of students
	 * @return the id and student's list of this group as a string
	 */
	public String toString() {
		String retour = this.getLibelle() ;
		/*
		if (!listEtudiant.isEmpty()){
			retour += "Liste d'étudiant : ";
			for (Etudiant e : listEtudiant) {
				retour+=  "\n" +e.toString();
			}
		}*/
		return retour;
	}

	/**
	 * Gets the group's label
	 * @return label of the group
	 */
	public String getLibelle() {
		return this.libelle;
	}

	/**
	 * Sets label of the group
	 * @param libelle : the new label this group is assigned to.
	 */
	public void setLibelle(String libelle) {
		if(libelle == null){
			this.libelle = "";
		} else {
			this.libelle = libelle;
		}
		this.libelleChanged = true;
	}
	

	public boolean isLibelleChanged() {
		return libelleChanged;
	}

	public void disableLibelleChanged() {
		this.libelleChanged = false;
	}

	/**
	 * Gets the list of students of this group.
	 * @return the ArrayList of group's student
	 */
	public ArrayList<Etudiant> getStudents() {
		if (listeEtudiant == null){
			this.listeEtudiant = new ArrayList<Etudiant>();
		}
		return (ArrayList<Etudiant>) this.listeEtudiant;
		
	}
	
	/**
	 * Get students as string
	 * @return list of studen as string
	 */
	public String getStudentsAsString(){
		String message ="";
		
		if(listeEtudiant!=null){
			for (int i=0 ; i<listeEtudiant.size();i++){
				message += listeEtudiant.get(i) + "\n";
			}
		}
		
		return message;
	}
	
	/**
	 * Set students list
	 * @param listeEtudiant : List of students
	 */
	public void setEtudiants(List<Etudiant> listeEtudiant) {
		if(listeEtudiant == null){
			this.listeEtudiant = new ArrayList<Etudiant>();
		} else {
			this.listeEtudiant = listeEtudiant;
			Etudiant currentEtudiant;
			for(int i=0 ;i<listeEtudiant.size(); i++){
				currentEtudiant = listeEtudiant.get(i);
				if(currentEtudiant!=null && currentEtudiant.getGroupe() != this){
					currentEtudiant.setGroupe(this);
				}
			}
		}
		
		this.listeEtudiantChanged = true;
	}
	
	/**
	 * Load list of students of the group from array 
	 * @param tabEtudiants : The array of students 
	 */
	public void setEtudiants(Etudiant[] tabEtudiants) {
		if(tabEtudiants == null){
			this.listeEtudiant = new ArrayList<Etudiant>();
		} else {
			if(this.listeEtudiant == null){
				this.listeEtudiant = new ArrayList<Etudiant>();
			} else {
				this.listeEtudiant.clear();
			}
			for(Etudiant e : tabEtudiants){
				this.listeEtudiant.add(e);
			}
		}
		
		this.listeEtudiantChanged = true;
	}
	
	public void addStudent(Etudiant e){
		if(listeEtudiant == null){
			this.listeEtudiant = new ArrayList<Etudiant>();
		}
		if (!listeEtudiant.contains(e) && e!= null) {
			listeEtudiant.add(e);
			if(e != null || e.getGroupe() != this){
				e.setGroupe(this);
			}
		}
		
		this.listeEtudiantChanged = true;
	}
	
	public void removeStudent(Etudiant e){
		listeEtudiant.remove(e);
		e.detachFromGroup(this);
		
		this.listeEtudiantChanged = true;
	}

	public boolean isListeEtudiantChanged() {
		return listeEtudiantChanged;
	}

	public void disableListeEtudiantChanged() {
		this.listeEtudiantChanged = false;
	}

	/**
	 * Get the project
	 * @return project
	 */
	public Projet getProjet() {
		return projet;
	}

	/**
	 * Set the project
	 * @param projet : Project
	 */
	public void setProjet(Projet projet) {
		this.projet = projet;
		
		if(projet != null && projet.getGroupe()!=this){
			projet.setGroupe(this);
		}
		this.projetChanged = true;
	}

	public boolean isProjetChanged() {
		return projetChanged;
	}

	public void disableProjetChanged() {
		this.projetChanged = false;
	}

	/**
	 * Return the list of Voeux
	 * @return voeux
	 */
	public List<Voeux> getListVoeux() {
		return listeVoeux;
	}

	/**
	 * Set the list of wishes
	 * @param listVoeux : List of wishes
	 */
	public void setListVoeux(List<Voeux> listVoeux) {
		if(listeVoeux != null){
			this.listeVoeux = listVoeux;
			for (int i=0; i<listVoeux.size(); i++){
	
				if(listVoeux.get(i).getGroupe()!=this){
					listVoeux.get(i).setGroupe(this);
				}
			}
		}
		this.listeVoeuxChanged = true;
	}

	public boolean isListeVoeuxChanged() {
		return listeVoeuxChanged;
	}

	public void disableListeVoeuxChanged(boolean listeVoeuxChanged) {
		this.listeVoeuxChanged = listeVoeuxChanged;
	}

	/**
	 * Used to return a value of an expected field 
	 * @param champ : Field's name 
	 * @throws RuntimeException if the field does not exist for this kind of object
	 * @return value 
	 * if "champ" is "libelle", it returns the label of the group
	 * if "champ" is "students", it returns the list of students as String 
	 */
	public  String getValueFor(String champ) throws RuntimeException{
		switch(champ){
		case "libelle": return libelle;
		case "students": return getStudentsAsString();
		default : throw new RuntimeException(champ + " does not exist in "  + this.getClass().getSimpleName());
		}
	}

	
	public boolean isLibelleValid(){
		return libelle.length()>=1;
	}
	
	
	
	/**
	 * Test if the content of each arguments is correct. <br>
	 * The id must be longer than one character <br>
	 * If it's not valid, Field's background will be errorColor (red)<br>
	 * if it's valid, Field's background will be white.<br>
	 * @return True if it's valid, false if not
	 */
	@Override
	public boolean isValid() {
		boolean valid = true;
		if (!isLibelleValid()) {
			valid = false;	
		}
		return valid;
	}

	 
	// TODO
	// TODO
	/**
	 * Used to set the groupe from an CSV Row
	 */
	@Override
	public void loadFromArray(String[] csvRow) {
		setLibelle(csvRow[0]);
	}
	

	/**
	 * @see fr.iut_blagnac.data.util.OptiElement#equals(java.lang.Object)
	 **/
	@Override
	public boolean equalsElement(Object o){
		if(o.getClass()!=Groupe.class){
			return false;
		}
		Groupe p = (Groupe) o;
		if(!this.libelle.equals(p.getLibelle()) 
		|| !this.getStudentsAsString().equals(p.getStudentsAsString())){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Used to duplicate a group
	 * @return Groupe : the same as this object, same id, same wishes
	 */
	@Override
	public OptiElement clone(){
		return new Groupe(libelle, new ArrayList<Etudiant>(listeEtudiant));
	}
	
	

	
	public boolean equals(Object object){
		if (object == null)
			return false;
		else if (object instanceof Groupe){
			return this.libelle.equals(((Groupe)object).getLibelle());
		} else {
			return super.equals(object);
		}
	}

	@Override
	public int compareTo(Groupe o) {
		return this.libelle.compareTo(o.getLibelle());
	}

	@Override
	public String[] toArray() {
		// TODO Auto-generated method stub
		return new String[0];
	}

	public static String[] getCSVHeader() {
		// TODO Auto-generated method stub
		return new String[0];
	}
	
	public OptiElementRepresentation buildOptiElementRepresentation(){
		return new GroupeRepresentation(this);
	}
	
	public JDialog buildDetails(JFrame application, DataManager dataManager){
		return new GroupeDetails(application, dataManager, this);
	}
}
