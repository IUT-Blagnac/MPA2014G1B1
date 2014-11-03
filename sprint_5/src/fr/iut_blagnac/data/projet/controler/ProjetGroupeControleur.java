package fr.iut_blagnac.data.projet.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class ProjetGroupeControleur extends OptiElementFieldControleur {

	private JTextField groupeField;
	private Projet projet;

	public ProjetGroupeControleur(Projet projet, JTextField groupeField) {
		this.projet = projet;
		this.groupeField = groupeField;
	}

	@Override
	public void updateData(String newData) {
		if(projet.getGroupe() == null || !projet.getGroupe().getLibelle().equals(newData)){
			projet.setGroupe(new Groupe(newData));
		}
		if(!projet.isGroupeValid()){
			groupeField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			groupeField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}


}
