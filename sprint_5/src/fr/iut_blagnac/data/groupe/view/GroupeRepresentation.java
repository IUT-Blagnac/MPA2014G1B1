package fr.iut_blagnac.data.groupe.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.groupe.controler.GroupeLibelleControleur;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;
import fr.iut_blagnac.gui.Application;

@SuppressWarnings("serial")
public class GroupeRepresentation extends OptiElementRepresentation{
	
	private Groupe groupe;
	
	// The label's field
	private JTextField libelleField = new JTextField();
	// The number of student field
	private JTextField nbEtuField = new JTextField();

	private static boolean editModeEnabled = false;
	private boolean editModeState = !editModeEnabled;
	private boolean editModeChanged = true;

	
	

	public GroupeRepresentation(Groupe groupe){
		this.groupe = groupe;

		this.setLayout(new GridLayout(1, 0));
		
		this.setBackground(Color.WHITE);


		JPanel groupePanel = new JPanel(new BorderLayout());
		JPanel nbEtuPanel = new JPanel(new BorderLayout());
		
		
		JLabel labelSaisieGroupe = new JLabel(Application.langString.get("group") + ":");
		JLabel labelSaisieNbEtu = new JLabel(Application.langString.get("nbetu") + ":");
		
		libelleField.setText(this.groupe.getLibelle());
		libelleField.getDocument().addDocumentListener(new GroupeLibelleControleur(groupe, libelleField));
		
		nbEtuField.setText(String.valueOf(this.groupe.getStudents().size()));
		


		groupePanel.add(labelSaisieGroupe, BorderLayout.WEST);
		groupePanel.add(libelleField, BorderLayout.CENTER);
		
		nbEtuPanel.add(labelSaisieNbEtu, BorderLayout.WEST);
		nbEtuPanel.add(nbEtuField, BorderLayout.CENTER);

		
		

		this.add(groupePanel);
		this.add(nbEtuPanel);
		
	}
	

	public void setBackground(Color color){
		super.setBackground(color);
		for(Component component : this.getComponents()){
			if(component.getClass() == JPanel.class){
				component.setBackground(color);
			}
		}

	}
	
	public void paint(Graphics g){
		boolean changed = false;
		
		if(groupe.isLibelleChanged()){
			changed = true;
			libelleField.setText(groupe.getLibelle());
			for(KeyListener listener : this.libelleField.getKeyListeners()){
				listener.keyTyped(null);
			}
			groupe.disableLibelleChanged();
		}
		
		if(groupe.isListeEtudiantChanged()){
			changed = true;
			nbEtuField.setText(String.valueOf(this.groupe.getStudents().size()));
			groupe.disableListeEtudiantChanged();
		}
		

		
		if(editModeState != editModeEnabled){
			
			libelleField.setEditable(editModeEnabled);
			nbEtuField.setEditable(editModeEnabled);
			editModeChanged = true;
			editModeState = editModeEnabled;
		}
		
		
		if(changed || editModeChanged){
			this.validate();
			editModeChanged = false;
		}
		super.paint(g);
	}


	@Override
	public OptiElement getOptiElement() {
		return groupe;
	}



	public static void setEditModeEnabled(boolean newEditMode) {
		editModeEnabled = newEditMode;
	}
	
	public static boolean isEditModeEnabled() {
		return editModeEnabled;
	}
	

	@Override
	public boolean isEditable() {
		return isEditModeEnabled();
	}

	@Override
	public void setEditable(boolean editable) {
		setEditModeEnabled(editable);
	}

}
