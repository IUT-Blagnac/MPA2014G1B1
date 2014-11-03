package fr.iut_blagnac.data.sujet.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.sujet.controler.SujetIdControleur;
import fr.iut_blagnac.data.sujet.controler.SujetNomControleur;
import fr.iut_blagnac.data.sujet.controler.SujetTitreControleur;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;
import fr.iut_blagnac.gui.Application;

@SuppressWarnings("serial")
public class SujetRepresentation extends OptiElementRepresentation{
	
	private Sujet sujet;

	private JTextField idField = new JTextField();
	private JTextField nomField = new JTextField();
	private JTextField titreField = new JTextField();
	
	private static boolean editModeEnabled = false;
	private boolean editModeState = !editModeEnabled;
	private boolean editModeChanged = true;


	public SujetRepresentation(Sujet sujet){
		this.sujet = sujet;

		this.setLayout(new GridLayout(1, 0));
		
		this.setBackground(Color.WHITE);

		JPanel idPanel = new JPanel(new BorderLayout());
		JPanel nomPanel = new JPanel(new BorderLayout());
		JPanel titrePanel = new JPanel(new BorderLayout());

		JLabel labelSaisieId = new JLabel(Application.langString.get("id"));
		JLabel labelSaisieNome = new JLabel(Application.langString.get("name"));
		JLabel labelSaisieTitre = new JLabel(Application.langString.get("title"));

		this.idField.setText(String.valueOf(this.sujet.getId()));
		this.idField.getDocument().addDocumentListener(new SujetIdControleur(sujet, idField));
		
		this.nomField.setText(this.sujet.getNom());
		this.nomField.getDocument().addDocumentListener(new SujetNomControleur(sujet, nomField));
		
		this.titreField.setText(this.sujet.getTitre());
		this.titreField.setPreferredSize(nomField.getSize());
		this.titreField.getDocument().addDocumentListener(new SujetTitreControleur(sujet, titreField));
		
		
		


		idPanel.add(labelSaisieId, BorderLayout.WEST);
		idPanel.add(idField, BorderLayout.CENTER);

		nomPanel.add(labelSaisieNome, BorderLayout.WEST);
		nomPanel.add(nomField, BorderLayout.CENTER);
		
		titrePanel.add(labelSaisieTitre, BorderLayout.WEST);
		titrePanel.add(titreField, BorderLayout.CENTER);
		
		

		this.add(idPanel);
		this.add(nomPanel);
		this.add(titrePanel);
		
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

		if(sujet.isIdChanged()){
			changed = true;
			idField.setText(sujet.getId());
			for(KeyListener listener : this.idField.getKeyListeners()){
				listener.keyTyped(null);
			}
			sujet.disableIdChanged();
		}
		
		if(sujet.isNomChanged()){
			changed = true;
			nomField.setText(sujet.getNom());
			for(KeyListener listener : this.nomField.getKeyListeners()){
				listener.keyTyped(null);
			}
			sujet.disableNomChanged();
		}
		
		if(sujet.isTitreChanged()){
			changed = true;
			titreField.setText(sujet.getTitre());
			for(KeyListener listener : this.titreField.getKeyListeners()){
				listener.keyTyped(null);
			}
			sujet.disableTitreChanged();
		}

		
		if(editModeState != editModeEnabled){
			
			idField.setEditable(editModeEnabled);
			nomField.setEditable(editModeEnabled);
			titreField.setEditable(editModeEnabled);
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
		return sujet;
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
