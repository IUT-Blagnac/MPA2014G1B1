package fr.iut_blagnac.data.groupe.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.gui.Application;

/**
 * Dialog group : Display information about the group : name, subject, list of intervenant and list of students
 * @author Groupe 1B1
 * @version 2.0
 */
@SuppressWarnings("serial")
public class GroupeDetails extends JDialog{

	@SuppressWarnings("unused")
	private boolean updatingData ; 
	@SuppressWarnings("unused")
	private DataManager currentDM;
	
	
	public GroupeDetails (JFrame parentWindow, DataManager currentDataManager, Groupe groupe) {
		currentDM = currentDataManager;
		this.setLayout(new BorderLayout());
		
		
		JPanel top = new JPanel(new BorderLayout());
		JPanel center = new JPanel(new BorderLayout());

		
		// Création des champs d'affichage d'information
		JTextArea nomG = new JTextArea();
		JTextArea nomS = new JTextArea();
		JTextArea interA = new JTextArea();
		JTextArea etuA = new JTextArea();
		
		// Définition du champ du nom de groupe
		nomG.setEditable(false);
		nomG.setFocusable(false);
		nomG.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("groupname") + " :"),BorderFactory.createEmptyBorder(8, 8,8,8))));
		nomG.setBackground(top.getBackground());
		
		nomG.setText(groupe.getLibelle());
		
		
		// Définition du champ du nom du sujet
		nomS.setEditable(false);
		nomS.setFocusable(false);
		nomS.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("subjectname") + " :"),BorderFactory.createEmptyBorder(8, 8,8,8))));
		nomS.setBackground(top.getBackground());
		
		if (groupe != null && groupe.getProjet() != null && groupe.getProjet().getSujet() != null ){
			nomS.setText(groupe.getProjet().getSujet().toString());
		}
			
		// Définition du champ intervenant
		interA.setEditable(false);
		interA.setFocusable(false);
		interA.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("advisor") +" :"),BorderFactory.createEmptyBorder(8, 8,8,8))));
		interA.setBackground(top.getBackground());

		if(groupe.getProjet() != null && groupe.getProjet().getIntervenantAsString()!= null){
			interA.setText(groupe.getProjet().getIntervenantAsString());
		}
		
		// Définition du champ des étudiants
		etuA.setEditable(false);
		etuA.setFocusable(false);
		if (groupe.getStudentsAsString() != null){
			etuA.setText(groupe.getStudentsAsString().toString());
		}
		etuA.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("students")),BorderFactory.createEmptyBorder(8, 8,8,8))));
		etuA.setBackground(top.getBackground());

		
		// Ajout des champs
		top.add(nomG, BorderLayout.NORTH);
		top.add(nomS, BorderLayout.CENTER);
		
		center.add(etuA,BorderLayout.NORTH);
		center.add(interA, BorderLayout.CENTER);
		
	
		this.add(top, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		
		this.pack();
		this.setLocationRelativeTo(this);
	}
	
}