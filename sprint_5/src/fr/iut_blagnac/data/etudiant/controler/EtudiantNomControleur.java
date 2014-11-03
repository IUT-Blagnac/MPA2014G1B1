package fr.iut_blagnac.data.etudiant.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class EtudiantNomControleur extends OptiElementFieldControleur{

	private JTextField nomField;
	private Etudiant etudiant;

	public EtudiantNomControleur(Etudiant etudiant, JTextField nomField) {
		this.etudiant = etudiant;
		this.nomField = nomField;
	}

	@Override
	public void updateData(String newData) {
		if(!etudiant.getLastName().equals(newData)){
			etudiant.setLastName(newData);
		}
		if(!etudiant.isLastNameValid()){
			nomField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			nomField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}

}
