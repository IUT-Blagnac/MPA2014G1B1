package fr.iut_blagnac.data.intervenant.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class IntervenantNomControleur extends OptiElementFieldControleur{

	private JTextField nomField;
	private Intervenant intervenant;

	public IntervenantNomControleur(Intervenant intervenant, JTextField nomField) {
		this.intervenant = intervenant;
		this.nomField = nomField;
	}


	@Override
	public void updateData(String newData) {
		if(!intervenant.getLastName().equals(newData)){
			intervenant.setLastName(newData);
		}
		if(!intervenant.isLastNameValid()){
			nomField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			nomField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}


}
