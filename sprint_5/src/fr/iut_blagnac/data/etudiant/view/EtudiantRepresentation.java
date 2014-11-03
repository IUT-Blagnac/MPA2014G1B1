package fr.iut_blagnac.data.etudiant.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.etudiant.controler.EtudiantGroupeControleur;
import fr.iut_blagnac.data.etudiant.controler.EtudiantIdControleur;
import fr.iut_blagnac.data.etudiant.controler.EtudiantNomControleur;
import fr.iut_blagnac.data.etudiant.controler.EtudiantPrenomControleur;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;
import fr.iut_blagnac.gui.Application;

@SuppressWarnings("serial")
public class EtudiantRepresentation extends OptiElementRepresentation{
	
	private Etudiant etudiant;

	private JTextField groupeField = new JTextField();
	private JTextField idField = new JTextField();
	private JTextField firstNameField = new JTextField();
	private JTextField lastNameField = new JTextField();
	
	private static boolean editModeEnabled = false;
	private boolean editModeState = !editModeEnabled;
	private boolean editModeChanged = true;

	public EtudiantRepresentation(Etudiant etudiant){
		this.etudiant = etudiant;

		this.setLayout(new GridLayout(1, 0));
		
		this.setBackground(Color.WHITE);


		JPanel groupPanel = new JPanel(new BorderLayout());
		JPanel idPanel = new JPanel(new BorderLayout());
		JPanel firstNamePanel = new JPanel(new BorderLayout());
		JPanel lastNamePanel = new JPanel(new BorderLayout());


		JLabel groupeLabel = new JLabel(Application.langString.get("group"));
		JLabel idLabel = new JLabel(Application.langString.get("id"));
		JLabel firstNameLabel = new JLabel(Application.langString.get("firstname"));
		JLabel lastNameLabel = new JLabel(Application.langString.get("lastname"));

		this.groupeField.setText(this.etudiant.getGroupe().getLibelle());
		this.groupeField.getDocument().addDocumentListener(new EtudiantGroupeControleur(etudiant, groupeField));
		
		this.idField.setText(this.etudiant.getId());
		this.idField.getDocument().addDocumentListener(new EtudiantIdControleur(etudiant, idField));
		
		this.firstNameField.setText(this.etudiant.getFirstName());
		this.firstNameField.getDocument().addDocumentListener(new EtudiantPrenomControleur(etudiant, firstNameField));
		
		this.lastNameField.setText(this.etudiant.getLastName());
		this.lastNameField.getDocument().addDocumentListener(new EtudiantNomControleur(etudiant, lastNameField));


		groupPanel.add(groupeLabel, BorderLayout.WEST);
		groupPanel.add(groupeField, BorderLayout.CENTER);

		idPanel.add(idLabel, BorderLayout.WEST);
		idPanel.add(idField, BorderLayout.CENTER);
		
		firstNamePanel.add(firstNameLabel, BorderLayout.WEST);
		firstNamePanel.add(firstNameField, BorderLayout.CENTER);
		
		lastNamePanel.add(lastNameLabel, BorderLayout.WEST);
		lastNamePanel.add(lastNameField, BorderLayout.CENTER);
		

		this.add(groupPanel);
		this.add(idPanel);
		this.add(firstNamePanel);
		this.add(lastNamePanel);
		
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
		if(etudiant.isGroupeChanged()){
			changed = true;
			groupeField.setText(etudiant.getGroupe().getLibelle());
			for(KeyListener listener : this.groupeField.getKeyListeners()){
				listener.keyTyped(null);
			}
			etudiant.disableGroupeChanged();
		}
		
		if(etudiant.isIdChanged()){
			changed = true;
			idField.setText(etudiant.getId());
			for(KeyListener listener : this.idField.getKeyListeners()){
				listener.keyTyped(null);
			}
			etudiant.disableIdChanged();
		}
		
		if(etudiant.isFirstNameChanged()){
			changed = true;
			firstNameField.setText(etudiant.getFirstName());
			for(KeyListener listener : this.firstNameField.getKeyListeners()){
				listener.keyTyped(null);
			}
			etudiant.disableFirstNameChanged();
		}
		
		if(etudiant.isLastNameChanged()){
			changed = true;
			lastNameField.setText(etudiant.getLastName());
			for(KeyListener listener : this.lastNameField.getKeyListeners()){
				listener.keyTyped(null);
			}
			etudiant.disableLastNameChanged();
		}
		
		if(editModeState != editModeEnabled){

			groupeField.setEditable(editModeEnabled);
			idField.setEditable(editModeEnabled);
			firstNameField.setEditable(editModeEnabled);
			lastNameField.setEditable(editModeEnabled);
			editModeChanged = true;
			editModeState = editModeEnabled;
		}
		
		
		if(changed || editModeChanged){
			this.validate();
			editModeChanged = false;
		}
		super.paint(g);
	}


	public static void setEditModeEnabled(boolean newEditMode) {
		editModeEnabled = newEditMode;
	}
	
	public static boolean isEditModeEnabled() {
		return editModeEnabled;
	}


	@Override
	public OptiElement getOptiElement() {
		return etudiant;
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
