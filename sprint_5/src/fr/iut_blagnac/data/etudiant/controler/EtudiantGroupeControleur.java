package fr.iut_blagnac.data.etudiant.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class EtudiantGroupeControleur extends OptiElementFieldControleur {

	private JTextField groupeField;
	private Etudiant etudiant;

	public EtudiantGroupeControleur(Etudiant etudiant, JTextField groupeField) {
		this.etudiant = etudiant;
		this.groupeField = groupeField;
	}



	@Override
	public void updateData(String newData) {
		if(!etudiant.getGroupe().getLibelle().equals(newData)){
			etudiant.setGroup(newData);
		}
		if(!etudiant.isGroupValid()){
			groupeField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			groupeField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}

}
