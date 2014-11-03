package fr.iut_blagnac.data.groupe.controler;

import javax.swing.JTextField;

import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementFieldControleur;

public class GroupeLibelleControleur  extends OptiElementFieldControleur {

	private JTextField libelleField;
	private Groupe groupe;

	public GroupeLibelleControleur(Groupe groupe, JTextField libelleField) {
		this.groupe = groupe;
		this.libelleField = libelleField;
	}


	@Override
	public void updateData(String newData) {
		if(!groupe.getLibelle().equals(newData)){
			groupe.setLibelle(newData);
		}
		if(!groupe.isLibelleValid()){
			libelleField.setBackground(OptiElement.ERROR_COLOR);
		} else {
			libelleField.setBackground(OptiElement.DEFAULT_COLOR);
		}
	}

}
