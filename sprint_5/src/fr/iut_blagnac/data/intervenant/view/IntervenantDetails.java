package fr.iut_blagnac.data.intervenant.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import fr.iut_blagnac.data.encadrer.Encadrer;
import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.intervenant.role.Roles;
import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.gui.Application;

/**
 * Dialogue intervenant
 * @author Groupe 1B1
 * @version 2.0//
 * 
 */

@SuppressWarnings("serial")
public class IntervenantDetails extends JDialog {
	
//Attributes
	
	//Display fields
	private JPanel projectPanel ;
	private JLabel project ;
	private DefaultComboBoxModel<Encadrer> projectListModel ;
	@SuppressWarnings("rawtypes")
	private JComboBox projectList ;
	private JList<Sujet> subjects ;
	private JList<Groupe> groups ;
	private DefaultListModel<Roles> rolesModel ;
	private JList<Roles> roles ;

	
	
//Methods and functions
	//Constructor
	public IntervenantDetails (JFrame parentWindow, DataManager pDtM, Intervenant pInterv) {
		super(parentWindow, pInterv.getFirstName() + " " + pInterv.getLastName()) ;
		
		//Closing mode
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE) ;
		
		// Dialog Settings 
		this.setLayout(new BorderLayout()) ;
		
		//Initializing variables
			//Panel
		projectPanel = new JPanel(new FlowLayout()) ;
			//Label
		project = new JLabel(Application.langString.get("project"));
			//ComboBox

		projectListModel = new DefaultComboBoxModel<Encadrer>(pInterv.getListeEncadrer().toArray(new Encadrer[pInterv.getListeEncadrer().size()])) ;
		projectList = new JComboBox<Encadrer>(projectListModel) ;
			//Jlists
		List<Sujet> tabSujetsInt = new ArrayList<Sujet>() ;
		
		for (Encadrer e : pInterv.getListeEncadrer()){
			tabSujetsInt.add(e.getProjet().getSujet());
		}
		
		subjects = new JList<Sujet>(new DefaultListModel<Sujet>()) ;
		groups = new JList<Groupe>(new DefaultListModel<Groupe>()) ;
		rolesModel = new DefaultListModel<Roles>() ;
		roles = new JList<Roles>(rolesModel) ;
		
		for (Roles r : Roles.values()) {
			rolesModel.addElement(r) ;
		}
		
		//Adding the fields to the dialog
		projectPanel.add(project) ;
		projectPanel.add(projectList) ;
		
		this.add(projectPanel, BorderLayout.NORTH) ;
		this.add(subjects, BorderLayout.WEST) ;
		this.add(groups, BorderLayout.CENTER) ;
		this.add(roles, BorderLayout.EAST) ;
		this.setLocationRelativeTo(this);
		this.pack();
	}
}
