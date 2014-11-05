package fr.iut_blagnac.data.encadrer;

import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.intervenant.role.Roles;
import fr.iut_blagnac.data.projet.Projet;

/**
 * Class to rely Intervenant and project with a specified roles from the enum
 * @author Groupe 1B1
 * @version 2.0//
 */
public class Encadrer {
	
	// The intervenant of the relation
	Intervenant intervenant;
	
	// The project of the relation
	Projet projet;
	
	// The role of the intervenant on this project
	Roles role;

	/**
	 * Create a relation between a specified project, intervenant with specified roles on the project
	 * @param intervenant : The operator
	 * @param projet : The project
	 * @param role : The role
	 */
	public Encadrer(Intervenant intervenant, Projet projet, Roles role) {
		this.intervenant = intervenant;
		this.projet = projet;
		this.role = role;		
	}

	/**
	 * Get the advisor
	 * @return the Intervenant
	 */
	public Intervenant getIntervenant() {
		return intervenant;
	}
	
	/**
	 * Set the advisor
	 * @param intervenant : The operator
	 */
	public void setIntervenant(Intervenant intervenant) {
		this.intervenant = intervenant;
		if(!intervenant.getListeEncadrer().contains(this)){
			intervenant.getListeEncadrer().add(this);
		}
	}

	/**
	 * Get the project
	 * @return the Project
	 */
	public Projet getProjet() {
		return projet;
	}

	/**
	 * Set the project
	 * @param projet : The project
	 */
	public void setProjet(Projet projet) {
		this.projet = projet;		
	}

	/**
	 * Get the role of the intervenant on the project
	 * @return the role 
	 */
	public Roles getRole() {
		return role;
	}

	/**
	 * Set the role of the intervenant on the project
	 * @param role : The role
	 */
	public void setRole(Roles role) {
		this.role = role;
	}
	
	
	
}
