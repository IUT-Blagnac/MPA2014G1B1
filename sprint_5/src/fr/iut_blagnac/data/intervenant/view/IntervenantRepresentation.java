package fr.iut_blagnac.data.intervenant.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.intervenant.controler.IntervenantIdControleur;
import fr.iut_blagnac.data.intervenant.controler.IntervenantNomControleur;
import fr.iut_blagnac.data.intervenant.controler.IntervenantPrenomControleur;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;
import fr.iut_blagnac.gui.Application;

@SuppressWarnings("serial")
public class IntervenantRepresentation extends OptiElementRepresentation{
	
	private Intervenant intervenant;

	private JTextField idField = new JTextField();
	private JTextField firstNameField = new JTextField();
	private JTextField lastNameField = new JTextField();
	
	private static boolean editModeEnabled = false;
	private boolean editModeState = !editModeEnabled;
	private boolean editModeChanged = true;


	public IntervenantRepresentation(Intervenant intervenant){
		this.intervenant = intervenant;

		this.setLayout(new GridLayout(1, 0));
		
		this.setBackground(Color.WHITE);

		JPanel idPanel = new JPanel(new BorderLayout());
		JPanel firstNamePanel = new JPanel(new BorderLayout());
		JPanel lastNamePanel = new JPanel(new BorderLayout());
		
		
		JLabel id = new JLabel("id");
		JLabel firstName = new JLabel(Application.langString.get("firstname"));
		JLabel lastName = new JLabel(Application.langString.get("lastname"));

		this.idField.setText(this.intervenant.getId());
		this.idField.getDocument().addDocumentListener(new IntervenantIdControleur(intervenant, idField));

		this.firstNameField.setText(this.intervenant.getFirstName());
		this.firstNameField.getDocument().addDocumentListener(new IntervenantPrenomControleur(intervenant, firstNameField));
		
		this.lastNameField.setText(this.intervenant.getLastName());
		this.lastNameField.getDocument().addDocumentListener(new IntervenantNomControleur(intervenant, lastNameField));
		

		


		idPanel.add(id, BorderLayout.WEST);
		idPanel.add(idField, BorderLayout.CENTER);
		
		firstNamePanel.add(firstName, BorderLayout.WEST);
		firstNamePanel.add(firstNameField, BorderLayout.CENTER);
		
		lastNamePanel.add(lastName, BorderLayout.WEST);
		lastNamePanel.add(lastNameField, BorderLayout.CENTER);

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
		if(intervenant.isIdChanged()){
			changed = true;
			idField.setText(intervenant.getId());
			for(KeyListener listener : this.idField.getKeyListeners()){
				listener.keyTyped(null);
			}
			intervenant.disableIdChanged();
		}
		
		if(intervenant.isFirstNameChanged()){
			changed = true;
			firstNameField.setText(intervenant.getFirstName());
			for(KeyListener listener : this.firstNameField.getKeyListeners()){
				listener.keyTyped(null);
			}
			intervenant.disableFirstNameChanged();
		}
		
		if(intervenant.isLastNameChanged()){
			changed = true;
			lastNameField.setText(intervenant.getLastName());
			for(KeyListener listener : this.lastNameField.getKeyListeners()){
				listener.keyTyped(null);
			}
			intervenant.disableLastNameChanged();
		}

		
		if(editModeState != editModeEnabled){
			
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


	@Override
	public OptiElement getOptiElement() {
		return intervenant;
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
