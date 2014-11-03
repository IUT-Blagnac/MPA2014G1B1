package fr.iut_blagnac.data.sujet.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class SujetTitreControleur extends OptiElementFieldControleur {

	private JTextField titreField;
	private Sujet sujet;

	public SujetTitreControleur(Sujet sujet, JTextField titreField) {
		this.sujet = sujet;
		this.titreField = titreField;
	}


	@Override
	public void updateData(String newData) {
		if(!sujet.getTitre().equals(newData)){
			sujet.setTitre(newData);
		}
		if(!sujet.isTitreValid()){
			titreField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			titreField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}


}
