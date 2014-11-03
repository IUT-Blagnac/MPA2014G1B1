package fr.iut_blagnac.data.etudiant;

import javax.swing.JDialog;
import javax.swing.JFrame;

import fr.iut_blagnac.data.etudiant.view.EtudiantDetails;
import fr.iut_blagnac.data.etudiant.view.EtudiantRepresentation;
import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;
import fr.iut_blagnac.util.Str;

/**
 * This class is used to do operations on groups.
 * @author Groupe 1B1
 * @version 2.0
 */public class Etudiant extends OptiElement {

	// Student's group
	private Groupe groupe;
	
	// Student's id
	private String id;
	
	// Student's First name 
	private String firstName;

	// Student's last name
	private String lastName;
	

	private boolean groupeChanged = true;
	private boolean idChanged = true;
	private boolean firstNameChanged = true;
	private boolean lastNameChanged = true;
	

	/**
	 * Constructor Initialization of first name and last name to an empty string
	 */
	public Etudiant() {
		this(null, "", "", "");
	}
	
	public Etudiant(String[] csvRow) {
		loadFromArray(csvRow);
	}

	/**
	 * Constructor
	 * @param groupe : Student's group
	 * @param id TODO
	 * @param firstName : Student's first name
	 * @param lastName : Student's last name
	 */
	public Etudiant(Groupe groupe, String id, String firstName, String lastName) {
		setGroupe(groupe);
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
	}

	/**
	 * @see fr.iut_blagnac.data.util.OptiElement#toString()
	 */
	@Override
	public String toString() {
		return groupe.getLibelle() + " " + id + " " + firstName + " " + lastName ;
	}
	
	

	/**
	 * Set the last name of the student
	 * @param groupe : Student's group
	 */
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
		if(groupe != null && groupe.getStudents() != null){
			groupe.getStudents().add(this);
		}
		this.groupeChanged = true;
	}
	
	/**
	 * Set the group from a String
	 * @param groupe : Student's group
	 */
	public void setGroup(String groupe) {
		if(groupe == null){
			groupe = "";
		}
		
		if(this.groupe == null || !this.groupe.getLibelle().equals(groupe)){
			this.setGroupe(new Groupe(groupe));
		}
	}	
	
	/**
	 * Get the group of the student
	 */
	public Groupe getGroupe() {
		return groupe;
	}
	
	/**
	 * Set the group from a String
	 * @param gpe : Student's group
	 */
	public void detachFromGroup(Groupe groupe) {
		if(this.groupe != null && this.groupe.equals(groupe)){
			this.groupe = null;
			this.groupeChanged = true;
		}
	}	

	public boolean isGroupeChanged() {
		return groupeChanged;
	}

	public void disableGroupeChanged() {
		this.groupeChanged = false;
	}
	

	/**
	 * Set the id of the Students
	 * @param id : the new id of the student
	 */
	public void setId(String id) {
		if(id == null){
			this.id = "";
		} else {
			this.id = id;
		}
		this.idChanged =true;
	}
	
	/**
	 * Get the id of the Students
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public boolean isIdChanged() {
		return idChanged;
	}

	public void disableIdChanged() {
		this.idChanged = false;
	}
	

	/**
	 * Get the first name of the Students
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Set the first name of the Students
	 * @param firstName : the new First Name of the student
	 */
	public void setFirstName(String firstName) {
		if(firstName == null){
			this.firstName = "";
		} else {
			this.firstName = firstName;
		}
		this.firstNameChanged =true;
	}

	public boolean isFirstNameChanged() {
		return firstNameChanged;
	}

	public void disableFirstNameChanged() {
		this.firstNameChanged = false;
	}

	/**
	 * Get the last name of the student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the last name of the student
	 * @param lastName : Last name
	 */
	public void setLastName(String lastName) {
		if(lastName == null){
			this.lastName = "";
		} else {
			this.lastName = lastName;
		}
		this.lastNameChanged = true;
	}

	public boolean isLastNameChanged() {
		return lastNameChanged;
	}

	public void disableLastNameChanged() {
		this.lastNameChanged = false;
	}
	

	

	public  String getValueFor(String champ){
		switch(champ){
		case "idGroupe": return groupe.getLibelle();
		case "id": return id;
		case "firstName": return firstName;
		case "lastName": return lastName;
		default : throw new RuntimeException(champ + " does not exist in "  + this.getClass().getSimpleName());
		}
	}

	


	
	public boolean isGroupValid(){
		return groupe.getLibelle().length() >= 1;
	}
	
	public boolean isIdValid(){
		return id.length() >= 1;
	}
	
	public boolean isFirstNameValid(){
		return this.firstName.length() > 1 && Str.isLetter(this.firstName);
	}
	public boolean isLastNameValid(){
		return this.lastName.length() > 1 && Str.isLetter(this.lastName);
	}


	@Override
	public boolean isValid() {
		boolean valid = true;

		if (!isGroupValid()){
			valid = false;
		}
		
		if (!isIdValid()){
			valid = false;
		}
		
		if (!isFirstNameValid()) {
			valid = false;
		}
		
		if (!isLastNameValid()) {
			valid = false;
		}
		
		return valid;
	}
	
	
	public static String[] getCSVHeader() {
		String[] header = {"groupe", "id", "nom", "prenom"};
		return header;
	}

	/**
	 * Return the object as a table
	 * [0] is the student's first name
	 * [1] is the student's last name
	 * [2] is the student's group
	 * @return String[] : table composed of each argument in different cell 
	 */
	@Override
	public String[] toArray() {
		String[] result = new String[4];

		result[0] = this.groupe.getLibelle();
		result[1] = this.id;
		result[2] = this.lastName;
		result[3] = this.firstName;
		
		return result;
	}

	/**
	 * @see fr.iut_blagnac.data.util.OptiElement#loadFromArray(java.lang.String[])
	 **/
	@Override
	public void loadFromArray(String[] csvRow) {
		setGroup(csvRow[0]);
		setId(csvRow[1]);
		setLastName(csvRow[2]);
		setFirstName(csvRow[3]);
	}
	
	/**
	 * @see fr.iut_blagnac.data.util.OptiElement#equals(java.lang.Object)
	 **/
	@Override
	public boolean equalsElement(Object o){
		if(o.getClass()!= Etudiant.class){
			return false;
		}
		
		Etudiant e = (Etudiant) o;
		if(this.groupe.getLibelle() != e.getGroupe().getLibelle()
		|| !this.id.equals(e.getId())
		|| !this.firstName.equals(e.getFirstName()) 
		|| !this.lastName.equals(e.getLastName()) 
		){
			return false;
		}
		
		return true;
	}
	
	@Override
	public OptiElement clone(){
		return new Etudiant(groupe, id, firstName, lastName);
	}
	
	public OptiElementRepresentation buildOptiElementRepresentation(){
		return new EtudiantRepresentation(this);
	}
	
	public JDialog buildDetails(JFrame application, DataManager dataManager){
		return new EtudiantDetails(application, dataManager, this);
	}
	
}
