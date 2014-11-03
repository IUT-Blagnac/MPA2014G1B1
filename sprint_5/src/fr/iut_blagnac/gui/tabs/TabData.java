package fr.iut_blagnac.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.etudiant.view.EtudiantRepresentation;
import fr.iut_blagnac.data.groupe.view.GroupeRepresentation;
import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.intervenant.view.IntervenantRepresentation;
import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.projet.view.ProjetRepresentation;
import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.sujet.view.SujetRepresentation;
import fr.iut_blagnac.data.util.DataFilter;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.gui.Application;
import fr.iut_blagnac.gui.ElementCreator;
import fr.iut_blagnac.gui.ElementDisplayer;

@SuppressWarnings("serial")
public class TabData extends JPanel implements ItemListener{
	
	
	private ElementCreator currentCreator;
	private ElementDisplayer currentDisplayer;
	
	private ElementDisplayer etudiantDisplayer;
	private ElementDisplayer intervenantDisplayer;
	private ElementDisplayer projetDisplayer;
	private ElementDisplayer sujetDisplayer;
	private ElementDisplayer groupeDisplayer;
	private JPanel panelSouth;
	
	private DataManager currentDataManager;


	public TabData(JFrame currentApplication, DataManager currentDataManager){
		this.currentDataManager = currentDataManager;
		
		this.setLayout(new BorderLayout());
		
		panelSouth = new JPanel();

		this.etudiantDisplayer = new ElementDisplayer(currentApplication, currentDataManager, currentDataManager.getEtudiants());
		this.etudiantDisplayer.setVisible(false);
		EtudiantRepresentation.setEditModeEnabled(false);
		
		this.intervenantDisplayer = new ElementDisplayer(currentApplication, currentDataManager, currentDataManager.getIntervenants());
		this.intervenantDisplayer.setVisible(false);
		IntervenantRepresentation.setEditModeEnabled(false);
		
		
		this.projetDisplayer = new ElementDisplayer(currentApplication, currentDataManager, currentDataManager.getProjets());
		this.projetDisplayer.setVisible(false);
		ProjetRepresentation.setEditModeEnabled(false);
		
		this.sujetDisplayer =  new ElementDisplayer(currentApplication, currentDataManager, currentDataManager.getSujets());
		this.sujetDisplayer.setVisible(false);
		SujetRepresentation.setEditModeEnabled(false);
		
		this.groupeDisplayer = new ElementDisplayer(currentApplication, currentDataManager, currentDataManager.getGroupes());
		this.groupeDisplayer.setVisible(false);
		GroupeRepresentation.setEditModeEnabled(false);
		
		currentDataManager.addDataChangeListener(etudiantDisplayer);
		currentDataManager.addDataChangeListener(intervenantDisplayer);
		currentDataManager.addDataChangeListener(projetDisplayer);
		currentDataManager.addDataChangeListener(sujetDisplayer);
		currentDataManager.addDataChangeListener(groupeDisplayer);

		this.currentDisplayer = etudiantDisplayer;
		this.currentDisplayer.setVisible(true);
		
		this.currentCreator = new ElementCreator(currentDisplayer, "Etudiants", currentDataManager);
		this.currentCreator.setRecordListener(currentDisplayer);
		
		this.currentCreator.getComboBox().addItemListener(this);
		
		changeFilter();
		
		this.add(panelSouth,BorderLayout.SOUTH);
		this.add(this.currentDisplayer, BorderLayout.CENTER);
		this.add(this.currentCreator, BorderLayout.NORTH);

		this.revalidate();
		this.repaint();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		this.remove(currentDisplayer);
		currentDisplayer.setVisible(false);
		
		String selected = currentCreator.getComboBox().getSelectedItem().toString();
		if(selected.equals(Application.langString.get("students"))){
			currentDisplayer = etudiantDisplayer;
		} else if(selected.equals(Application.langString.get("advisors"))){
			currentDisplayer = intervenantDisplayer;
		} else if(selected.equals(Application.langString.get("projects"))){
			currentDisplayer = projetDisplayer;
		} else if(selected.equals(Application.langString.get("subjects"))){
			currentDisplayer = sujetDisplayer;
		} else if (selected.equals(Application.langString.get("groups"))){
			currentDisplayer = groupeDisplayer;
			currentDisplayer.setVisible(true);
			currentDataManager.refreshGroupes();
			currentDisplayer.addAllElements(currentDataManager.getGroupes().toArray(new OptiElement[currentDataManager.getGroupes().size()]));
		
		}
		
		changeFilter();
		
		
		currentDisplayer.setVisible(true);
		this.add(this.currentDisplayer, BorderLayout.CENTER);
		currentCreator.setRecordListener(currentDisplayer);

		this.revalidate();
		this.repaint();
	}
	
	public void changeFilter() {
		String type = null;
		String selected = currentCreator.getComboBox().getSelectedItem().toString();
		if(selected.equals(Application.langString.get("students"))){
			type = "Etudiants";
		} else if(selected.equals(Application.langString.get("advisors"))){
			type = "Intervenants";
		} else if(selected.equals(Application.langString.get("projects"))){
			type  = "Projets";
		} else if(selected.equals(Application.langString.get("subjects"))){
			type = "Sujets";
		} else {
			type = "";
		}
		
		
		switch (type) {
		case "Etudiants":{
			panelSouth.removeAll();
			repaint();
			
			JLabel filter = new JLabel("Filtrer : ");
			JLabel group = new JLabel("Groupe");
			JLabel firstName = new JLabel("Prénom");
			JLabel lastName = new JLabel("Nom");
			
			final JTextField txtLastName = new JTextField();
			final JTextField txtGroupe = new JTextField();
			final JTextField txtFirstName = new JTextField();
			
			txtGroupe.setPreferredSize(new Dimension(20, 20));
			txtFirstName.setPreferredSize(new Dimension(70, 20));
			txtLastName.setPreferredSize(new Dimension(70, 20));
			


			DocumentListener typingListener = new DocumentListener(){
				public void changedUpdate(DocumentEvent e) {
					if(e.getDocument().equals(txtGroupe.getDocument())){
						etudiantDisplayer.removeDataFilter("etudiant_id");
						etudiantDisplayer.addDataFilter("etudiant_groupe", new DataFilter(Etudiant.class, "idGroupe", txtGroupe.getText(), false));
					} else if(e.getDocument().equals(txtFirstName.getDocument())){
						etudiantDisplayer.removeDataFilter("etudiant_firstName");
						etudiantDisplayer.addDataFilter("etudiant_firstName", new DataFilter(Etudiant.class, "firstName", txtFirstName.getText(), false));
					} else if(e.getDocument().equals(txtLastName.getDocument())){
						etudiantDisplayer.removeDataFilter("etudiant_lastName");
						etudiantDisplayer.addDataFilter("etudiant_lastName", new DataFilter(Etudiant.class, "lastName", txtLastName.getText(), false));
					}
					etudiantDisplayer.dataChange();
				}
				public void insertUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
				public void removeUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
			};
			

			txtGroupe.getDocument().addDocumentListener(typingListener);
			txtFirstName.getDocument().addDocumentListener(typingListener);
			txtLastName.getDocument().addDocumentListener(typingListener);
			
			
			panelSouth.add(filter);
			panelSouth.add(group);
			panelSouth.add(txtGroupe);
			panelSouth.add(firstName);
			panelSouth.add(txtFirstName);
			panelSouth.add(lastName);
			panelSouth.add(txtLastName);
		}break;
			
		case "Intervenants":{
			panelSouth.removeAll();
			repaint();
			
			JLabel filter = new JLabel("Filtrer : ");
			JLabel id = new JLabel("Id");
			JLabel firstName = new JLabel("Prénom");
			JLabel lastName = new JLabel("Nom");

			final JTextField txtId = new JTextField();
			final JTextField txtLastName = new JTextField();
			final JTextField txtFirstName = new JTextField();

			txtId.setPreferredSize(new Dimension(70, 20));
			txtFirstName.setPreferredSize(new Dimension(70, 20));
			txtLastName.setPreferredSize(new Dimension(70, 20));
			

			DocumentListener typingListener = new DocumentListener(){
				public void changedUpdate(DocumentEvent e) {
					if(e.getDocument().equals(txtId.getDocument())){
						intervenantDisplayer.removeDataFilter("intervenant_id");
						intervenantDisplayer.addDataFilter("intervenant_id", new DataFilter(Intervenant.class, "id", txtId.getText(), false));
					} else if(e.getDocument().equals(txtFirstName.getDocument())){
						intervenantDisplayer.removeDataFilter("intervenant_firstName");
						intervenantDisplayer.addDataFilter("intervenant_firstName", new DataFilter(Intervenant.class, "firstName", txtFirstName.getText(), false));
					} else if(e.getDocument().equals(txtLastName.getDocument())){
						intervenantDisplayer.removeDataFilter("intervenant_lastName");
						intervenantDisplayer.addDataFilter("intervenant_lastName", new DataFilter(Intervenant.class, "lastName", txtLastName.getText(), false));
					}
					intervenantDisplayer.dataChange();
				}
				public void insertUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
				public void removeUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
			};
			

			txtId.getDocument().addDocumentListener(typingListener);
			txtFirstName.getDocument().addDocumentListener(typingListener);
			txtLastName.getDocument().addDocumentListener(typingListener);
			
			panelSouth.add(filter);
			panelSouth.add(id);
			panelSouth.add(txtId);
			panelSouth.add(firstName);
			panelSouth.add(txtFirstName);
			panelSouth.add(lastName);
			panelSouth.add(txtLastName);
		}break;
			
		case "Projets":{
			panelSouth.removeAll();
			repaint();
			
			JLabel filter = new JLabel("Filtrer : ");
			JLabel group = new JLabel("Groupe");
			JLabel subject = new JLabel("Sujet");
			JLabel superviseur = new JLabel("Superviseur");
			
			final JTextField txtGroupe= new JTextField();
			final JTextField txtSubject = new JTextField();
			final JTextField txtSuperviseur = new JTextField();
			
			txtGroupe.setPreferredSize(new Dimension(70, 20));
			txtSubject.setPreferredSize(new Dimension(70, 20));
			txtSuperviseur.setPreferredSize(new Dimension(70, 20));
			
			
			DocumentListener typingListener = new DocumentListener(){
				public void changedUpdate(DocumentEvent e) {
					if(e.getDocument().equals(txtGroupe.getDocument())){
						projetDisplayer.removeDataFilter("projet_groupe");
						projetDisplayer.addDataFilter("projet_groupe", new DataFilter(Projet.class, "idGroupe", txtGroupe.getText(), false));
					} else if(e.getDocument().equals(txtSubject.getDocument())){
						projetDisplayer.removeDataFilter("projet_sujet");
						projetDisplayer.addDataFilter("projet_sujet", new DataFilter(Projet.class, "idSujet", txtSubject.getText(), false));
					} else if(e.getDocument().equals(txtSuperviseur.getDocument())){
						projetDisplayer.removeDataFilter("projet_superviseur");
						projetDisplayer.addDataFilter("projet_superviseur", new DataFilter(Projet.class, "idSuperviseur", txtSuperviseur.getText(), false));
					}
					projetDisplayer.dataChange();
				}
				public void insertUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
				public void removeUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
			};

			txtGroupe.getDocument().addDocumentListener(typingListener);
			txtSubject.getDocument().addDocumentListener(typingListener);
			txtSuperviseur.getDocument().addDocumentListener(typingListener);
			
			
			panelSouth.add(filter);
			panelSouth.add(group);
			panelSouth.add(txtGroupe);
			panelSouth.add(subject);
			panelSouth.add(txtSubject);
			panelSouth.add(superviseur);
			panelSouth.add(txtSuperviseur);
		}break;
			
		case "Sujets":{ 
			panelSouth.removeAll();
			repaint();
			
			
			JLabel filter = new JLabel("Filtrer : ");
			JLabel id = new JLabel("Id");
			JLabel name = new JLabel("Nom");
			JLabel title = new JLabel("Titre");
			
			final JTextField txtId = new JTextField();
			final JTextField txtName = new JTextField();
			final JTextField txtTitle = new JTextField();
			
			txtId.setPreferredSize(new Dimension(70, 20));
			txtName.setPreferredSize(new Dimension(70, 20));
			txtTitle.setPreferredSize(new Dimension(70, 20));
			
			sujetDisplayer.removeAllDataFilter();

			DocumentListener sujetTypingListener = new DocumentListener(){
				public void changedUpdate(DocumentEvent e) {
					if(e.getDocument().equals(txtId.getDocument())){
						sujetDisplayer.removeDataFilter("sujet_id");
						sujetDisplayer.addDataFilter("sujet_id", new DataFilter(Sujet.class, "id", txtId.getText(), false));
					} else if(e.getDocument().equals(txtName.getDocument())){
						sujetDisplayer.removeDataFilter("sujet_name");
						sujetDisplayer.addDataFilter("sujet_name", new DataFilter(Sujet.class, "nom", txtName.getText(), false));
					} else if(e.getDocument().equals(txtTitle.getDocument())){
						sujetDisplayer.removeDataFilter("sujet_title");
						sujetDisplayer.addDataFilter("sujet_title", new DataFilter(Sujet.class, "titre", txtTitle.getText(), false));
					}
					projetDisplayer.dataChange();
				}
				public void insertUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
				public void removeUpdate(DocumentEvent e) {
					changedUpdate(e);
				}
			};

			txtId.getDocument().addDocumentListener(sujetTypingListener);
			txtName.getDocument().addDocumentListener(sujetTypingListener);
			txtTitle.getDocument().addDocumentListener(sujetTypingListener);
			
			panelSouth.add(filter);
			panelSouth.add(id);
			panelSouth.add(txtId);
			panelSouth.add(name);
			panelSouth.add(txtName);
			panelSouth.add(title);
			panelSouth.add(txtTitle);
		}break;
			
		default:
			panelSouth.removeAll();
			revalidate();
		}
	}
}
