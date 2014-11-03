package fr.iut_blagnac.data.sujet.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class SujetIdControleur extends OptiElementFieldControleur {

	private JTextField idField;
	private Sujet sujet;

	public SujetIdControleur(Sujet sujet, JTextField idField) {
		this.sujet = sujet;
		this.idField = idField;
	}

	@Override
	public void updateData(String newData) {
		if(!sujet.getId().equals(newData)){
			sujet.setId(newData);
		}
		if(!sujet.isIdValid()){
			idField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			idField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}


}
