package fr.iut_blagnac.data.intervenant.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class IntervenantIdControleur extends OptiElementFieldControleur {

	private JTextField idField;
	private Intervenant intervenant;

	public IntervenantIdControleur(Intervenant intervenant, JTextField idField) {
		this.intervenant = intervenant;
		this.idField = idField;
	}


	@Override
	public void updateData(String newData) {
		if(!intervenant.getId().equals(newData)){
			intervenant.setId(newData);
		}
		if(!intervenant.isIdValid()){
			idField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			idField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}



}
