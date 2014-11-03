package fr.iut_blagnac.data.projet.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.projet.controler.ProjetGroupeControleur;
import fr.iut_blagnac.data.projet.controler.ProjetSujetControleur;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;
import fr.iut_blagnac.gui.Application;

@SuppressWarnings("serial")
public class ProjetRepresentation extends OptiElementRepresentation{
	
	private Projet projet;

	
	private JTextField groupeField = new JTextField();
	private JTextField sujetField = new JTextField();

	private static boolean editModeEnabled = false;
	private boolean editModeState = !editModeEnabled;
	private boolean editModeChanged = true;

	

	public ProjetRepresentation(Projet projet){
		this.projet = projet;

		this.setLayout(new GridLayout(1, 0));
		
		this.setBackground(Color.WHITE);


		JPanel groupePanel = new JPanel(new BorderLayout());
		JPanel sujetPanel = new JPanel(new BorderLayout());
		
		JLabel labelSaisieGroupe = new JLabel(Application.langString.get("group"));
		JLabel labelSaisieSujet = new JLabel(Application.langString.get("subject"));
		
		this.groupeField.setText(projet.getGroupe().getLibelle());
		this.groupeField.getDocument().addDocumentListener(new ProjetGroupeControleur(projet, groupeField));
		
		this.sujetField.setText(projet.getSujet().getId());
		this.groupeField.getDocument().addDocumentListener(new ProjetSujetControleur(projet, groupeField));


		groupePanel.add(labelSaisieGroupe, BorderLayout.WEST);
		groupePanel.add(groupeField, BorderLayout.CENTER);

		sujetPanel.add(labelSaisieSujet, BorderLayout.WEST);
		sujetPanel.add(sujetField, BorderLayout.CENTER);
		

		this.add(groupePanel);
		this.add(sujetPanel);
		
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
		
		if(projet.isGroupeChanged()){
			changed = true;
			groupeField.setText(projet.getGroupe().getLibelle());
			for(KeyListener listener : this.groupeField.getKeyListeners()){
				listener.keyTyped(null);
			}
			projet.disableGroupeChanged();
		}
		
		if(projet.isSujetChanged()){
			changed = true;
			sujetField.setText(String.valueOf(this.projet.getSujet()));
			projet.disableSujetChanged();
		}
		

		
		if(editModeState != editModeEnabled){
			
			groupeField.setEditable(editModeEnabled);
			sujetField.setEditable(editModeEnabled);
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
		return projet;
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
