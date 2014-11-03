package fr.iut_blagnac.data.etudiant.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class EtudiantPrenomControleur  extends OptiElementFieldControleur {

	private JTextField prenomField;
	private Etudiant etudiant;

	public EtudiantPrenomControleur(Etudiant etudiant, JTextField prenomField) {
		this.etudiant = etudiant;
		this.prenomField = prenomField;
	}


	@Override
	public void updateData(String newData) {
		if(!etudiant.getFirstName().equals(newData)){
			etudiant.setFirstName(newData);
		}
		if(!etudiant.isFirstNameValid()){
			prenomField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			prenomField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}

}
