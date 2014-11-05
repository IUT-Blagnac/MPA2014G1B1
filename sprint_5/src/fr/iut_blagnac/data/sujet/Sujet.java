package fr.iut_blagnac.data.sujet;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.sujet.view.SujetRepresentation;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;
import fr.iut_blagnac.data.voeux.Voeux;

/**
 * This class is used to do operations on subjects.
 * @author Groupe 1B1
 * Version 2.0
 */
public class Sujet extends OptiElement{
	
	// The id of this subject.
	private String id;
	
	// The name of this subject. 
	private String nom;

	// The title of this subject.
	private String titre;

	// The number if subject 
	private int nbSubject ;
	
	// The project which work on this subject
	private Projet projet;
	
	// The list of wishes of this subject
	private List<Voeux> listeVoeux = new ArrayList<Voeux>();
	

	private boolean idChanged = true;
	private boolean nomChanged = true;
	private boolean titreChanged = true;
	private boolean projetChanged = true;
	


	
	/**
	 * Creates a new subject without specifying the data.
	 */
	public Sujet () {
		this("", "", "");
	}
	
	/**
	 * Creates a new subject with the data specified.
	 * @param id : the id of this subject.
	 * @param nom : the name of this subject.
	 * @param titre : the title of this subject.
	 */
	public Sujet (String id, String nom, String titre) {
		this.setId(id);
		this.setNom(nom);
		this.setTitre(titre);
	}
	
	/**
	 * Creates a new subject with the data specified.
	 * @param id : the id of this subject.
	 * @param nom : the name of this subject.
	 * @param titre : the title of this subject.
	 * @param projet : the project of the subject.
	 */
	public Sujet (String id, String nom, String titre, Projet projet) {
		this(id,nom, titre);
		this.setProjet(projet);
	}
	
	/**
	 * Creates a new subject with the data specified.
	 * @param id : the id of this subject.
	 * @param nom : the name of this subject.
	 * @param titre : the title of this subject.
	 * @param projet : the project of the subject.
	 * @param listeVoeux : the list of wishes
	 */
	public Sujet (String id, String nom, String titre, Projet projet, List<Voeux> listeVoeux) {
		this(id,nom,titre,projet);
		this.setListeVoeux(listeVoeux);
	}
	
	
	public Sujet(String[] csvRow) {
		loadFromArray(csvRow);
	}
	
	/**
	 * Gets the name of this subject.
	 * @return the name of this subject
	 */
	@Override
	public String toString() {
		return id + " " + nom;
	}


	/**
	 * Gets the id of this subject.
	 * @return the id of this subject
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this subject.
	 * @param id : the new id of this subject.
	 */
	public void setId(String id) {
		if(id == null){
			this.id = "";
		} else {
			this.id = id;
		}
		this.idChanged = true;
	}
	

	public boolean isIdChanged() {
		return idChanged;
	}

	public void disableIdChanged() {
		this.idChanged = false;
	}

	/**
	 * Gets the id of this subject.
	 * @return name : the id of this subject
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Sets the id of this subject.
	 * @param name : the new id of this subject.
	 */
	public void setNom(String name) {
		if(name == null){
			this.nom = "";
		}else {
			this.nom = name;
		}
		this.nomChanged = true;
	}

	public boolean isNomChanged() {
		return nomChanged;
	}

	public void disableNomChanged() {
		this.nomChanged = false;
	}

	/**
	 * Gets the title of this subject.
	 * @return the title of this subject
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * Sets the title of this subject.
	 * @param titre : the new title of this subject.
	 */
	public void setTitre(String titre) {
		if(titre == null){
			this.titre = "";
		} else {
			this.titre = titre;
		}
		this.titreChanged = true;
	}
	
	public boolean isTitreChanged() {
		return titreChanged;
	}

	public void disableTitreChanged() {
		this.titreChanged = false;
	}

	/**
	 * Get the project
	 * @return projet
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
		if(projet != null && projet.getSujet() != this){
			projet.setSujet(this);
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
	 * Get the number of subject
	 * @return number of subject
	 */
	public int getNbSubject() {
		return nbSubject;
	}

	/**
	 * Set the number of subject
	 * @param nbSubject : Number of subjects
	 */
	public void setNbSubject(int nbSubject) {
		this.nbSubject = nbSubject;
	}

	/**
	 * Return the list of wishes
	 * @return listVoeux
	 */
	public List<Voeux> getListVoeux() {
		return listeVoeux;
	}

	/**
	 * Set the list wishes
	 * @param listeVoeux : List of wishes
	 */
	public void setListeVoeux(List<Voeux> listeVoeux) {
		this.listeVoeux = listeVoeux;
		if(listeVoeux!=null){
			Voeux currentVoeux;
			for(int i=0;i<listeVoeux.size();i++){
				currentVoeux = listeVoeux.get(i);
				if(currentVoeux!=null && currentVoeux.getSujet()!=this){
					currentVoeux.setSujet(this);
				}
			}
		}
	}

	public  String getValueFor(String champ){
		switch(champ){
		case "id": return id;
		case "nom": return nom;
		case "titre": return titre;
		default : throw new RuntimeException(champ + " does not exist in "  + this.getClass().getSimpleName());
		}
	}


	public boolean isIdValid(){
		return this.id.length() > 0;
	}
	
	public boolean isNomValid(){
		return this.id.length() > 0 ;
	}

	
	public boolean isTitreValid(){
		return this.id.length() > 0;
	}
	
	
	
	/**
	 * Tests if the content of each arguments is correct. <br>
	 * the name and the title must be only composed of letters.
	 * @return  True if it's valid, false if not
	 */
	@Override
	public boolean isValid() {
		boolean valid = true;

		if (!isIdValid()) {
			valid = false;
		}

		if (!isNomValid()) {
			valid = false;
		}	
		
		if (!isTitreValid()) {
			valid = false;
		}		
		
		return valid;
	}

	
	public static String[] getCSVHeader() {
		String[] header = {"id", "nom", "titre"};
		return header;
	}

	/**
	 * Return the object as a table. <br>
	 * [0] is the subject's id. <br>
	 * [1] is the subject's name. <br>
	 * [2] is the subject's title. <br>
	 * @return a table composed of each argument in different cell 
	 */
	@Override
	public String[] toArray() {
		String[] tabS = new String[3];
		
		tabS[0] = String.valueOf(this.id);
		tabS[1] = this.nom;
		tabS[2] = this.titre;
		
		return tabS;
	}
	

	@Override
	public void loadFromArray(String[] csvRow) {

		setId(csvRow[0]);
		setNom(csvRow[1]);
		if(csvRow.length == 3){
			setTitre(csvRow[2]);
		}
	}
	

	/**
	 * @see fr.iut_blagnac.data.util.OptiElement#equals(java.lang.Object)
	 **/
	@Override
	public boolean equalsElement(Object o){
		if(o.getClass()!=Sujet.class){
			return false;
		}
		Sujet s = (Sujet) o;
		if(!this.nom.equals(s.getNom()) 
		|| this.id != s.getId()){
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public OptiElement clone(){
		return new Sujet(id, nom, titre);
	}
	
	public OptiElementRepresentation buildOptiElementRepresentation(){
		return new SujetRepresentation(this);
	}
	public JDialog buildDetails(JFrame application, DataManager dataManager){
		return null;
	}

}
