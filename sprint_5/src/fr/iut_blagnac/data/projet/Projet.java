package fr.iut_blagnac.data.projet;

import javax.swing.JDialog;
import javax.swing.JFrame;

import fr.iut_blagnac.data.encadrer.Encadrer;
import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.intervenant.role.Roles;
import fr.iut_blagnac.data.projet.view.ProjetRepresentation;
import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;

/**
 * This class is used to do operations on projects.
 * @author  groupe 1B1
 * @version 2.0
 */
public class Projet extends OptiElement{
	private static int currentID;

	// The group this project is assigned to.
	private String id;
	
	// The group this project is assigned to.
	private Groupe groupe;

	// The subject of this project.
	private Sujet sujet;
	
	// The supervisor of this project.
	private Encadrer[] tabEncadrer = new Encadrer[3];
	
	// The real size of tabEncadrer
	private int sizeTabEncadrer=0;

	private boolean groupeChanged = true;
	private boolean sujetChanged = true;
	private boolean encadrerChanged = true;
	

	
	
	/**
	 * Creates a new project with the data specified.
	 * @param sujet : the subject of this project.
	 * @param groupe : the group this subject is assigned to.
	 */
	public Projet(Sujet sujet, Groupe groupe) {
		this(sujet, groupe, null);
	}
	

	/**
	 * Creates a new project with the data specified.
	 * @param sujet : the subject of this project.
	 * @param groupe : the group this subject is assigned to.
	 * @param tabEncadrer : list of object Encadrer
	 */
	public Projet(Sujet sujet, Groupe groupe, Encadrer[] tabEncadrer) {
		
		this.id=String.valueOf(Projet.currentID);
		Projet.currentID++;
		this.setSujet(sujet);
		this.setGroupe(groupe);
		this.setEncadrer(tabEncadrer);
	}
	
	/**
	 * Creates a new project from csv file
	 * @param csvRow : Array containing a line of a CSV file
	 */
	public Projet(String[] csvRow) {
		loadFromArray(csvRow);
	}
		
	/**
	 * Gets the subject of this project.
	 * @return the subject of this project.
	 */
	public String toString() {
		return this.getGroupe().getLibelle() +  " ; " + this.getSujet() ; 
	}
		
		/*message += "\nListe d'intervenant :"
		 * if (sizeTabEncadrer!= 0) {
			for (Encadrer e : tabEncadrer){
				message += "\n" + e.getIntervenant().toString() + " : " + e.getRole().toString();
			}
		}
		return message;/*
	}

	/**
	 * Gets the subject of this project.
	 * @return the subject of this project.
	 */
	public Sujet getSujet() {
		return this.sujet;
	}

	/**
	 * Sets the subject of this project.
	 * @param sujet : the new subject of this project.
	 */
	public void setSujet(Sujet sujet) {
		this.sujet = sujet;
		if(sujet != null && sujet.getProjet()!=this){
			sujet.setProjet(this);
		}
		this.sujetChanged = true;
	}

	public boolean isSujetChanged() {
		return sujetChanged;
	}

	public void disableSujetChanged() {
		this.sujetChanged = false;
	}

	/**
	 * Gets the group of this project.
	 * @return the group this subject is assigned to.
	 */
	public Groupe getGroupe() {
		return this.groupe;
	}

	/**
	 * Sets the group of this project.
	 * @param groupe : the new group this subject is assigned to.
	 */
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
		if(groupe!=null && groupe.getProjet()!=this){
			groupe.setProjet(this);
		}
		this.groupeChanged = true;
	}

	public boolean isGroupeChanged() {
		return groupeChanged;
	}

	public void disableGroupeChanged() {
		this.groupeChanged = false;
	}

	/**
	 * Gets the list of relation of this project.
	 * @return the list of relation of this subject is assigned to.
	 */
	public Encadrer[] getEncadrer() {
		return this.tabEncadrer;
	}

	/**
	 * Sets the tabEncadrer of this project.
	 * @param tabEncadrer : the new tabEncadrer
	 */
	public void setEncadrer(Encadrer[] tabEncadrer) {
		if(tabEncadrer!=null){
			this.tabEncadrer = tabEncadrer;
			this.sizeTabEncadrer = tabEncadrer.length;
			
			for (int i=0; i<tabEncadrer.length; i++){
				if(tabEncadrer[i] != null){
					if(tabEncadrer[i].getProjet()!=this){
						tabEncadrer[i].setProjet(this);
					}
				}
			}
		} else {
			this.tabEncadrer = new Encadrer[this.sizeTabEncadrer];
			
		}
	}

	public boolean isEncadrerChanged() {
		return encadrerChanged;
	}

	public void disableEncadrerChanged() {
		this.encadrerChanged = false;
	}
	

	
	/**
	 * Get the list of advisors who work on the project as strings
	 * @return list of advisors as String
	 */
	public String getIntervenantAsString(){
		String message = "";
		
		for(int i=0;i<tabEncadrer.length; i++){
			if (tabEncadrer[i] != null && tabEncadrer[i].getIntervenant() != null){
				message += tabEncadrer[i].getIntervenant()+"\n";
			}
		}
		return message;
	}
	
	

	/**
	 * Get the real size of tabEncadrer
	 * @return size
	 */
	public int getSizeTabEncadrer() {
		return sizeTabEncadrer;
	}

	/**
	 * Set the size of tabEncadrer
	 * @param sizeTabEncadrer : The size of tabEncadrer
	 */
	public void setSizeTabEncadrer(int sizeTabEncadrer) {
		this.sizeTabEncadrer = sizeTabEncadrer;
	}
	
	/**
	 * Update the real size of tabEncadrer
	 */
	public void updateSizeTabEncadrer(){
		this.sizeTabEncadrer = tabEncadrer.length;
	}

	/**
	 * @param champ : Champ
	 */
	public  String getValueFor(String champ){
		switch(champ){
		case "idGroupe": return groupe.getLibelle();
		case "idSujet": return sujet.getId();
		default : throw new RuntimeException(champ + " does not exist in "  + this.getClass().getSimpleName());
		}
	}
	

	public boolean isIdValid(){
		return this.groupe.getLibelle().length() >= 1;
	}
	public boolean isGroupeValid(){
		return this.groupe.getLibelle().length() >= 1;
	}
	
	public boolean isSujetValid(){
		return sujet.getId().length() >= 1;
	}
	
	
	

	/**
	 * Test if the content of each arguments is correct. <br>
	 * The subject must be only composed of letters. <br>
	 * The group must be a single capital letter.
	 * @return True if it's valid, false if not
	 */
	@Override
	public boolean isValid() {
		boolean valid = true;
		if (!isIdValid()) {
			valid = false;
		}
		
		if (!isSujetValid()) {
			valid = false;
		}
		
		if (!isGroupeValid()){
			valid = false;
		}
		
		return valid;
	}
	
	
	
	public static String[] getCSVHeader() {
		String[] header = {"id", "groupe", "sujet", "client", "superviseur", "support_technique"};
		return header;
	}

	/**
	 * Return the object as a table <br>
	 * [0] is the project's group <br>
	 * [1] is the project's subject <br>
	 * @return a table composed of each argument in different cell 
	 */
	@Override
	public String[] toArray() {
		String[] tabS = new String[getCSVHeader().length];
		tabS[0] = this.id;
		tabS[1] = this.groupe.getLibelle();
		tabS[2] = this.sujet.getId();
		tabS[3] = "";
		tabS[4] = "";
		tabS[5] = "";
		for(Encadrer encadrement : tabEncadrer){
			if(encadrement!=null){
				switch(encadrement.getRole()){
				case client: tabS[3] = encadrement.getIntervenant().getId(); break; 
				case superv: tabS[4] = encadrement.getIntervenant().getId(); break; 
				case techass: tabS[5] = encadrement.getIntervenant().getId(); break; 
				}
			}
		}
		return tabS;
	}
	
	@Override
	public void loadFromArray(String[] csvRow) {
		this.id=csvRow[0];
		
		
		
		this.setGroupe(new Groupe (csvRow[1]));
		Sujet sujet = new Sujet();
		sujet.setId(csvRow[2]);
		this.setSujet(sujet);
		tabEncadrer[0] = new Encadrer(new Intervenant(), this, Roles.client);
		tabEncadrer[0].getIntervenant().setId(csvRow[3]);
		tabEncadrer[1] = new Encadrer(new Intervenant(), this, Roles.client);
		tabEncadrer[1].getIntervenant().setId(csvRow[4]);
		tabEncadrer[2] = new Encadrer(new Intervenant(), this, Roles.client);
		if(csvRow.length == 6){
			tabEncadrer[2].getIntervenant().setId(csvRow[5]);
		}
	}
	

	/**
	 * @see fr.iut_blagnac.data.util.OptiElement#equals(java.lang.Object)
	 **/
	@Override
	public boolean equalsElement(Object o){
		if(o.getClass()!=Projet.class){
			return false;
		}
		Projet p = (Projet) o;
		if(!this.sujet.getId().equals(p.getSujet()) 
		|| this.groupe != p.getGroupe()){
			return false;
		}
		
		return true;
	}
	
	@Override
	public OptiElement clone(){
		Encadrer[] newtabEncadrer = tabEncadrer.clone();
		
		return new Projet(new Sujet(this.sujet.getId(), this.sujet.getNom(), this.sujet.getTitre()), this.groupe, newtabEncadrer );
	}
	
	public OptiElementRepresentation buildOptiElementRepresentation(){
		return new ProjetRepresentation(this);
	}
	
	public JDialog buildDetails(JFrame application, DataManager dataManager){
		return null;
	}
	
}
