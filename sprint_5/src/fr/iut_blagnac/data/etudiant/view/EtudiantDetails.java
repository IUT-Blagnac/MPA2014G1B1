package fr.iut_blagnac.data.etudiant.view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.gui.Application;

@SuppressWarnings("serial")
/**
 * Student Dialog : name, group, subject, list of advisors 
 * @author Groupe 1B1
 * @version 2.0
 * Sprint 3
 **/
public class EtudiantDetails extends JDialog {

	DataManager currentdatamanager;
	
	// WAAAARNING SUJET AND INTERVENANT NOT WORK
	public EtudiantDetails(JFrame parentWindow, DataManager datamanager, Etudiant etu) {
		// Titre de la fenêtre -> Prénom Nom
		super(parentWindow, etu.getFirstName() + " " + etu.getLastName());

		this.currentdatamanager = datamanager;

		JPanel main = new JPanel();
		
		main.setLayout(new GridLayout(4, 1));
		main.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		
		// Création des champs d'affichage d'information
		JTextArea nomA = new JTextArea();
		JTextArea groupeA = new JTextArea();
		JTextArea sujetA = new JTextArea();
		JTextArea interA = new JTextArea();
		
		// Définition du champ nom
		nomA.setEditable(false);
		nomA.setFocusable(false);
		nomA.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("name")),BorderFactory.createEmptyBorder(8, 8,8,8))));
		nomA.setBackground(main.getBackground());
		
		nomA.setText(etu.getFirstName() + " "	+ etu.getLastName());
		
		
		// Définition du champ groupe
		groupeA.setEditable(false);
		groupeA.setFocusable(false);
		groupeA.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("group")),BorderFactory.createEmptyBorder(8, 8,8,8))));
		groupeA.setBackground(main.getBackground());

		if(etu.getGroupe()!= null){
			groupeA.setText(etu.getGroupe().getLibelle());
		}
		
		
		// Définition du champ sujet
		sujetA.setEditable(false);
		sujetA.setFocusable(false);
		sujetA.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("subject")),BorderFactory.createEmptyBorder(8, 8,8,8))));
		sujetA.setBackground(main.getBackground());

		if (etu.getGroupe() != null && etu.getGroupe().getProjet() != null && etu.getGroupe().getProjet().getSujet() != null){
			sujetA.setText(etu.getGroupe().getProjet().getSujet().toString());
		}
		
		
		// Définition du champ intervenant
		interA.setEditable(false);
		interA.setFocusable(false);
		interA.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("advisor")),BorderFactory.createEmptyBorder(8, 8,8,8))));
		interA.setBackground(main.getBackground());

		if(etu.getGroupe() != null && etu.getGroupe().getProjet() != null){
			interA.setText(etu.getGroupe().getProjet().getIntervenantAsString());
		}
		
		
		// Ajout des infos
		main.add(nomA);
		main.add(groupeA);
		main.add(sujetA);
		main.add(interA);

		this.setContentPane(main);
		this.pack();
		this.setLocationRelativeTo(parentWindow);
	}
}
