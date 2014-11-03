package fr.iut_blagnac.data.sujet.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class SujetNomControleur extends OptiElementFieldControleur {

	private JTextField nomField;
	private Sujet sujet;

	public SujetNomControleur(Sujet sujet, JTextField nomField) {
		this.sujet = sujet;
		this.nomField = nomField;
	}


	@Override
	public void updateData(String newData) {
		if(!sujet.getNom().equals(newData)){
			sujet.setNom(newData);
		}
		if(!sujet.isNomValid()){
			nomField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			nomField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}


}
