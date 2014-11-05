package fr.iut_blagnac.data.voeux;

import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.sujet.Sujet;


/**
 * Association class to rely a group with a subject with a special number of wishes
 * @version 2.0
 * @author user
 */
public class Voeux {

	// The number of wishes for the group and the subject 
	int numVoeux;
	
	// The group
	Groupe groupe;
	
	// The subject
	Sujet sujet;
	
	/**
	 * Create a wishes with special number, special group and subject
	 * @param numV : The number of the wish
	 * @param gpe : The group
	 * @param sujet : The subject
	 */
	public Voeux (int numV, Groupe gpe, Sujet sujet){
		this.numVoeux = numV;
		this.groupe = gpe;
		this.sujet = sujet;		
	}
	
	/**
	 * Create a wishes with -1 as number of wishes, a null group and null subject
	 */
	public Voeux () {
		this(-1,null,null);
	}
	

	/**
	 * Return the number of wishes according to the group and the subject
	 * @return number of wishes
	 */
	public int getNumVoeux() {
		return numVoeux;
	}

	/**
	 * Set the number of wishes according to the group and the subject
	 * @param numVoeux the number of wishes
	 */
	public void setNumVoeux(int numVoeux) {
		this.numVoeux = numVoeux;
	}

	/**
	 * Return the group 
	 * @return the group 
	 */
	public Groupe getGroupe() {
		return groupe;
	}

	/**
	 * Set the group
	 * @param groupe : The group
	 */
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	/**
	 * Return the subject
	 * @return the subject
	 */
	public Sujet getSujet() {
		return sujet;
	}

	/** 
	 * Set the subject 
	 * @param sujet : The subject
	 */
	public void setSujet(Sujet sujet) {
		this.sujet = sujet;
	}
	
	
}
