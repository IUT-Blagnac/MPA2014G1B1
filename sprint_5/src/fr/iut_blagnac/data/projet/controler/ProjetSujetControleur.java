package fr.iut_blagnac.data.projet.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class ProjetSujetControleur extends OptiElementFieldControleur {

	private JTextField sujetField;
	private Projet projet;

	public ProjetSujetControleur(Projet projet, JTextField sujetField) {
		this.projet = projet;
		this.sujetField = sujetField;
	}


	@Override
	public void updateData(String newData) {
		if(projet.getSujet() == null || !projet.getSujet().getId().equals(newData)){
			Sujet sujet = new Sujet();
			sujet.setId(newData);
			projet.setSujet(sujet);
		}
		if(!projet.isGroupeValid()){
			sujetField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			sujetField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}


	

}
