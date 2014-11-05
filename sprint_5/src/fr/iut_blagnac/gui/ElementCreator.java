package fr.iut_blagnac.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.etudiant.view.EtudiantRepresentation;
import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.groupe.view.GroupeRepresentation;
import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.intervenant.view.IntervenantRepresentation;
import fr.iut_blagnac.data.projet.view.ProjetRepresentation;
import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.sujet.view.SujetRepresentation;
import fr.iut_blagnac.data.util.DataManager;

/**
 * This class is used to save the input data for each OptiElement.
 * @author Groupe 1 B1
 * @version 2.0
 */

//COMMENT LINE 85
@SuppressWarnings("serial")
public class ElementCreator extends JPanel {
	RecordListener listener;
	JComboBox<String> currentElementType;
	JButton addButton;
	JCheckBox editionMode;
	DataManager currentDataManager;

	public ElementCreator(RecordListener listener, Object defaultElement,DataManager data) {
		this();
		currentElementType.setSelectedItem(defaultElement);
		this.listener = listener;
		this.currentDataManager = data;
	}

	public ElementCreator() {
		this.setLayout(new BorderLayout());
		final String[] elementTypes = {
				Application.langString.get("students"),
				Application.langString.get("advisors"),
				Application.langString.get("subjects"),
				Application.langString.get("projects"),
				Application.langString.get("groups")};
		
		JPanel eastPanel = new JPanel();
		
		currentElementType = new JComboBox<String>(elementTypes);
		editionMode = new JCheckBox(Application.langString.get("editmode"));
		addButton = new JButton(Application.langString.get("add"));

		currentElementType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					enableEditMode(getEditionForCurrentType());
				}
			}
		});			
		
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = currentElementType.getSelectedItem().toString();
				if(selected.equals(Application.langString.get("students"))){
					listener.addElement(new Etudiant(new Groupe(),"", "", ""));
				} else if(selected.equals(Application.langString.get("advisors"))){
					listener.addElement(new Intervenant());
				} else if(selected.equals(Application.langString.get("projects"))){
					//listener.addElement(new Projet());
				} else if(selected.equals(Application.langString.get("subjects"))){
					listener.addElement(new Sujet());
				} else if(selected.equals(Application.langString.get("grroups"))){
					listener.addElement(new Groupe());
				}
			}

		});

		editionMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableEditMode(editionMode.isSelected());
			}
		});

		enableEditMode(getEditionForCurrentType());

		this.add(currentElementType, BorderLayout.CENTER);
		eastPanel.add(editionMode);
		eastPanel.add(addButton);
		this.add(eastPanel, BorderLayout.EAST);
	}

	/**
	 * 
	 * @return The comboBox composed of each type of object
	 */
	public JComboBox<String> getComboBox() {
		return this.currentElementType;
	}

	/**
	 * Setter of RecordListener
	 * 
	 * @param listener : The RecordListener
	 */
	public void setRecordListener(RecordListener listener) {
		this.listener = listener;
	}

	/**
	 * Use to enable or disable the edit mode according to the CheckBox
	 * editionMode is selected or not If it's selected, JTextField are editable,
	 * JButton Ajouter is visible, JButton Supprimer is visible If not,
	 * JTextField aren't editable, JButton Ajouter and JButton Supprimer aren't
	 * visible
	 * @param value : True or False
	 */
	public void enableEditMode(boolean value) {
		// Saving of the button state (Selected or not)
		
		String selection = currentElementType.getSelectedItem().toString();
		if (selection.equals(Application.langString.get("groups")) || selection.equals(Application.langString.get("projects"))) {
			editionMode.setEnabled(false);
			editionMode.setSelected(false);
			addButton.setEnabled(false);
		}else {
			editionMode.setEnabled(true);
			editionMode.setSelected(value);
			addButton.setEnabled(value);
			
			enableEditionForCurrentType(value);
		}
		
		if(currentDataManager!=null){
			currentDataManager.notifyDataChange();
		}
		
	}

	
	private void enableEditionForCurrentType(boolean editionEnabled){

		String selected = this.currentElementType.getSelectedItem().toString();
		if(selected.equals(Application.langString.get("students"))){
			EtudiantRepresentation.setEditModeEnabled(editionEnabled);
		} else if(selected.equals(Application.langString.get("advisors"))){
			IntervenantRepresentation.setEditModeEnabled(editionEnabled);
		} else if(selected.equals(Application.langString.get("projects"))){
			ProjetRepresentation.setEditModeEnabled(editionEnabled);
		} else if(selected.equals(Application.langString.get("subjects"))){
			SujetRepresentation.setEditModeEnabled(editionEnabled);
		} else if (selected.equals(Application.langString.get("groups"))){
			GroupeRepresentation.setEditModeEnabled(editionEnabled);
		}
	}
	
	private boolean getEditionForCurrentType(){
		String selected = this.currentElementType.getSelectedItem().toString();
		if(selected.equals(Application.langString.get("students"))){
			return EtudiantRepresentation.isEditModeEnabled();
		} else if(selected.equals(Application.langString.get("advisors"))){
			return IntervenantRepresentation.isEditModeEnabled();
		} else if(selected.equals(Application.langString.get("projects"))){
			return ProjetRepresentation.isEditModeEnabled();
		} else if(selected.equals(Application.langString.get("subjects"))){
			return SujetRepresentation.isEditModeEnabled();
		} else if (selected.equals(Application.langString.get("groups"))){
			return GroupeRepresentation.isEditModeEnabled();
		}
		return false;
	}
}
