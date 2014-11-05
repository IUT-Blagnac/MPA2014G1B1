package fr.iut_blagnac.data.intervenant;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import fr.iut_blagnac.data.encadrer.Encadrer;
import fr.iut_blagnac.data.intervenant.view.IntervenantDetails;
import fr.iut_blagnac.data.intervenant.view.IntervenantRepresentation;
import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;
import fr.iut_blagnac.util.Str;

public class Intervenant extends OptiElement{

	// The id of an intervenant
	private String id;
	
	// The first name of an intervenant
	private String firstName;
	
	// The last name of an intervenant
	private String lastName;
	
	// The list of Encadrer
	private List<Encadrer> listeEncadrer = new ArrayList<Encadrer>();
	

	private boolean idChanged = true;
	private boolean firstNameChanged = true;
	private boolean lastNameChanged = true;
	private boolean listeEncadrerChanged = true;
	
	
	/**
	 * Constructor 
	 * Initialization of first name and last name to an empty string
	 */
	public Intervenant (){
		this("", "", "");
	}
	
	/**
	 * Constructor
	 * @param csvRow : Array containing a line of a CSV file
	 */
	public Intervenant(String[] csvRow) {
		loadFromArray(csvRow);
	}
	
	/**
	 * Constructor 
	 * @param id : Identifiant de l'intervenant
	 * @param firstName : Prénom de l'intervenant
	 * @param lastName : Nom de l'intervenant
	 */
	public Intervenant(String id, String firstName, String lastName) {
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);

	} 
	
	/**
	 * Constructor 
	 * @param id : Identifiant de l'intervenant
	 * @param firstName : Prénom de l'intervenant
	 * @param lastName : Nom de l'intervenant
	 * @param listeEncadrer : the list of Encadrer
	 */
	public Intervenant(String id, String firstName, String lastName, List<Encadrer> listeEncadrer) {
		this(id, firstName, lastName);
		this.setListeEncadrer(listeEncadrer);
	} 
	
	// Getters and setters

	/**
	 * @see fr.iut_blagnac.data.util.OptiElement#toString()
	 */
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
	
	/**
	 * Return the id of the Intervenant
	 * @return the Id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Set the id of the Intervenant
	 * @param id : Id
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
	 * Return the first name of the Intervenant
	 * @return the first name 
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Set the first name of the Intervenant
	 * @param firstName : First name
	 */
	public void setFirstName(String firstName) {
		if(firstName == null){
			this.firstName = "";
		} else {
			this.firstName = firstName;
		}
		this.firstNameChanged = true;
	}

	public boolean isFirstNameChanged() {
		return firstNameChanged;
	}

	public void disableFirstNameChanged() {
		this.firstNameChanged = false;
	}
	
		
	/**
	 * Get the last name of the teacher
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Set the last name of the teacher
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

	/**
	 * Get the list of Encadrer
	 * @return listEncadrer
	 */
	public List<Encadrer> getListeEncadrer() {
		return listeEncadrer;
	}
	
	/**
	 * Set the list of Encadrer
	 * @param listEncadrer : list of Encadrer
	 */
	public void setListeEncadrer(List<Encadrer> listEncadrer) {
		this.listeEncadrer = listEncadrer;
		this.listeEncadrerChanged = true;
	}

	public boolean isListeEncadrerChanged() {
		return listeEncadrerChanged;
	}

	public void disableListeEncadrerChanged() {
		this.listeEncadrerChanged = false;
	}
	

	/**
	 * Get the list of project as string
	 * @return list of project as string
	 */
	public String getProjetsAsString() {
		String message = "";
		if(listeEncadrer != null){
			for (Encadrer e : listeEncadrer){
				message += e.getProjet() +"\n";
			}
		}
		return message ;
	}
	
	/**
	 * Get the list of project 
	 * @return list of project 
	 */
	public List<Projet> getProjets() {
		List<Projet> listProjets = new ArrayList<Projet>();

		if(listeEncadrer != null){
			for (Encadrer e : listeEncadrer){
				listProjets.add(e.getProjet());
			}
		}
		return listProjets;
	}
	
	public  String getValueFor(String champ){
		switch(champ){
		case "id": return id;
		case "firstName": return firstName;
		case "lastName": return lastName;
		default : throw new RuntimeException(champ + " does not exist in "  + this.getClass().getSimpleName());
		}
	}
	
	
	public boolean isIdValid(){
		return id.length() > 0;
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
		String[] header = {"id", "prenom", "nom"};
		return header;
	}

	/**
	 * Return the object as a table <br>
	 * [0] is the teacher's id <br>
	 * [1] is the teacher's first name <br>
	 * [2] is the teacher's last name <br>
	 * @return : table composed of each argument in different cell 
	 */
	@Override
	public String[] toArray() {
		String[] tabS = new String[3];
		
		tabS[0] = this.id;
		tabS[1] = this.firstName;
		tabS[2] = this.lastName;		
		
		return tabS;
	}

	@Override
	public void loadFromArray(String[] csvRow) {
		setId(csvRow[0]);
		setFirstName(csvRow[1]);
		if(csvRow.length == 3){
			setLastName(csvRow[2]);
		}
	}
	

	/**
	 * @see fr.iut_blagnac.data.util.OptiElement#equals(java.lang.Object)
	 **/
	@Override
	public boolean equalsElement(Object o){
		if(o.getClass()!=Intervenant.class){
			return false;
		}
		
		Intervenant i = (Intervenant) o;
		if(this.id!=i.getId()
		|| !this.firstName.equals(i.getFirstName()) 
		|| !this.lastName.equals(i.getLastName())){
			return false;
		}
		
		return true;
	}
	
	@Override
	public OptiElement clone(){
		return new Intervenant(id, firstName, lastName);
	}
	
	public OptiElementRepresentation buildOptiElementRepresentation(){
		return new IntervenantRepresentation(this);
	}
	
	public JDialog buildDetails(JFrame application, DataManager dataManager){
		return new IntervenantDetails(application, dataManager, this);
	}
}
