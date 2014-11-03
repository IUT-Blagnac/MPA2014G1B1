package fr.iut_blagnac.data.intervenant.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class IntervenantPrenomControleur  extends OptiElementFieldControleur {

	private JTextField prenomField;
	private Intervenant intervenant;

	public IntervenantPrenomControleur(Intervenant intervenant, JTextField prenomField) {
		this.intervenant = intervenant;
		this.prenomField = prenomField;
	}


	@Override
	public void updateData(String newData) {
		if(!intervenant.getFirstName().equals(newData)){
			intervenant.setFirstName(newData);
		}
		if(!intervenant.isFirstNameValid()){
			prenomField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			prenomField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}

}
