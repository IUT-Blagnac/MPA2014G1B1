package fr.iut_blagnac.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.iut_blagnac.data.encadrer.Encadrer;
import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.intervenant.role.Roles;
import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.util.DataChangeListener;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.gui.Application;

/**
 * This class is used to define the Intervenant tab on which you can give a role
 * to an intervenant on a specified project
 * 
 * @author Groupe 1B1
 * @version 2.0
 */
// WAAAAARNING! ADDLISTACTIONLISTENER MUST BE IMPLEMENTED
@SuppressWarnings("serial")
public class TabIntervenant extends JPanel implements ChangeListener,
		DataChangeListener {

	// All datas are in the DataManager
	private DataManager currentDataManager;

	// The JList of projects
	private JList<Projet> listeProjets;

	// The JList of subjects
	private JList<Intervenant> listeIntervenants;

	// The JList of groups
	private JList<Roles> listeRoles;

	// The parent window
	private JFrame parent;

	@SuppressWarnings("unused")
	private boolean updatingData;

	/**
	 * Create a new TabAssociation with a DataManager and a parent Window
	 * @param parent
	 * @param currentDataManager
	 */
	public TabIntervenant(JFrame parent, DataManager currentDataManager) {

		// Declaration
		this.currentDataManager = currentDataManager;
		this.parent = parent;

		// ////// Panel's Settings ///////

		// Set the layout of this JPanel
		this.setLayout(new BorderLayout());

		// Declaration of two new JPanel to organise the JPanel
		JPanel center = new JPanel(new GridLayout(1, 2, 20, 20));
		JPanel right = new JPanel(new BorderLayout(20, 20));
		JPanel north = new JPanel(new GridLayout(1, 2, 20, 20));
		// Set the border of panel center
		center.setBorder(BorderFactory.createEmptyBorder(5, 40, 10, 40));
		north.setBorder(BorderFactory.createEmptyBorder(10, 40, 5, 40));
		// Add to this
		this.add(center, BorderLayout.CENTER);
		this.add(north, BorderLayout.NORTH);

		// Creation of a data manager to store the data used
		currentDataManager = new DataManager();

		// Initialization of the JLists
		listeProjets = new JList<Projet>(new DefaultListModel<Projet>());
		listeIntervenants = new JList<Intervenant>(
				new DefaultListModel<Intervenant>());
		listeRoles = new JList<Roles>(Roles.values());

		// Set some settings on JList
//		listeRoles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		listeProjets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		listeIntervenants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		

		// Declaration of the JButton validate
		// TODO
		JButton affect = new JButton(Application.langString.get("affect"));
		affect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				affectation();
			}
		});

		// Creation of the Label that explains how to use the association tab
		JLabel infoAssociation = new JLabel("<html><font size=3>"
				+ Application.langString.get("dialinfoaction")
				+ "</font> </html>");
		// Creation of three JScrollPanes to display the lists of subjects,
		// projects and groups
		JScrollPane projetsScrollPane = new JScrollPane(listeProjets);
		JScrollPane rolesScrollPane = new JScrollPane(listeRoles);
		JScrollPane intervenantScrollPane = new JScrollPane(listeIntervenants);

		// Set the border of JScrollPane
		projetsScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				Application.langString.get("projects")));
		rolesScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				Application.langString.get("roles")));
		intervenantScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				Application.langString.get("advisors")));

		// Add two ScrollPane to the right panel
		right.add(projetsScrollPane, BorderLayout.NORTH);
		right.add(rolesScrollPane, BorderLayout.CENTER);

		// Add ScrollPane to the center panel
		north.add(infoAssociation);
		center.add(intervenantScrollPane);
		center.add(right);

		// Updating data
		updateData();

		this.currentDataManager.addDataChangeListener(new DataChangeListener() {

			@Override
			public void dataChange() {
				updateData();

			}
		});

		this.add(affect, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
	}

	/**
	 * Used to recalculate all data and update the display
	 */
	public void updateData() {
		updatingData = true;

		DefaultListModel<Projet> listeProjetsModel = (DefaultListModel<Projet>) listeProjets
				.getModel();
		listeProjetsModel.removeAllElements();
		for (OptiElement projet : currentDataManager.getProjets()) {
			projet.isValid();
			listeProjetsModel.addElement((Projet) projet);
		}

		DefaultListModel<Intervenant> listeIntervenantModel = (DefaultListModel<Intervenant>) listeIntervenants
				.getModel();
		listeIntervenantModel.removeAllElements();
		for (OptiElement inter : currentDataManager.getIntervenants()) {
			inter.isValid();
			listeIntervenantModel.addElement((Intervenant) inter);
		}

		updatingData = false;

		listeProjets.revalidate();
		listeProjets.repaint();
		listeRoles.revalidate();
		listeRoles.repaint();
		listeIntervenants.revalidate();
		listeIntervenants.repaint();

	}

	/**
	 * Used to updateData() if the state have changed
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		updateData();
	}

	/**
	 * Used to update the display
	 */
	public void revalidate() {
		if (listeProjets != null)
			listeProjets.revalidate();
		if (listeRoles != null)
			listeRoles.revalidate();
		if (listeIntervenants != null)
			listeIntervenants.revalidate();
		super.revalidate();
	}

	/**
	 * Used to update the display
	 */
	public void repaint() {
		if (listeProjets != null)
			listeProjets.repaint();
		if (listeRoles != null)
			listeRoles.repaint();
		if (listeIntervenants != null)
			listeIntervenants.repaint();
		super.repaint();
	}

	/**
	 * Used to updateData(), revalidate(), repaint() if data have changed
	 */
	@Override
	public void dataChange() {
		updateData();
		revalidate();
		repaint();

	}

	/**
	 * Associate a list of students and subject to make a project
	 */
	private void affectation() {
		if (listeIntervenants.getSelectedValue() != null
				&& listeProjets.getSelectedValue() != null
				&& listeRoles.getSelectedValue() != null) {

			// Declaration
			boolean alreadySet = false;
			int tailleTabEncadrer = 0;

			// Recuperation of the advisor, roles and project
			Roles rol = listeRoles.getSelectedValue();
			Intervenant inter = listeIntervenants.getSelectedValue();
			Projet proj = listeProjets.getSelectedValue();

			// Test if OCL rules are respected and if the role isn't set yet
			for (int i = 0; i < proj.getEncadrer().length; i++) {
				if (proj.getEncadrer()[i] != null) {

					if (proj.getEncadrer()[i].getRole().equals(rol)) {
						alreadySet = true;
						JOptionPane.showMessageDialog(parent,
								Application.langString.get("erroralreadyset1"),
								Application.langString.get("error"),
								JOptionPane.WARNING_MESSAGE);
						break;
					}

					if (proj.getEncadrer()[i].getIntervenant().equals(inter)
							&& proj.getEncadrer()[i].getRole().equals(
									Roles.valueOf("superv"))
							&& rol.equals(Roles.valueOf("client"))) {
						alreadySet = true;
						JOptionPane.showMessageDialog(parent,
								Application.langString.get("erroralreadyset2"),
								Application.langString.get("error"),
								JOptionPane.WARNING_MESSAGE);
						break;
					}

					if (proj.getEncadrer()[i].getIntervenant().equals(inter)
							&& proj.getEncadrer()[i].getRole().equals(
									Roles.valueOf("client"))
							&& rol.equals(Roles.valueOf("superv"))) {
						alreadySet = true;
						JOptionPane.showMessageDialog(parent,
								Application.langString.get("erroralreadyset3"),
								Application.langString.get("error"),
								JOptionPane.WARNING_MESSAGE);
						break;
					}
					tailleTabEncadrer++;
				}

			}

			// New relation encadrer between inter, proj and rol
			Encadrer E1 = new Encadrer(inter, proj, rol);

			// Test if the advisor already supervise/is already the clien or the
			// technical assistant of this project
			if (inter.getListeEncadrer().contains(E1)) {
				JOptionPane.showMessageDialog(
						parent,
						Application.langString.get("erroralreadyset4p1")
								+ " "
								+ rol
								+ " "
								+ Application.langString
										.get("erroralreadyset4p2"),
						Application.langString.get("error"),
						JOptionPane.WARNING_MESSAGE);
				alreadySet = true;
			}
			if (!alreadySet && tailleTabEncadrer < 2) {
				inter.getListeEncadrer().add(E1);
				proj.getEncadrer()[tailleTabEncadrer + 1] = E1;
				JOptionPane.showMessageDialog(
						parent,
						Application.langString.get("validAffectp1") + " " + rol
								+ " "
								+ Application.langString.get("validAffectp2")
								+ " " + inter + " "
								+ Application.langString.get("validAffectp3")
								+ " " + proj + ".",
						Application.langString.get("congrat"),
						JOptionPane.INFORMATION_MESSAGE);
			}
			dataChange();
		} else {
			JOptionPane.showMessageDialog(parent,
					Application.langString.get("INTerrornotselected"),
					Application.langString.get("error"),
					JOptionPane.WARNING_MESSAGE);
		}
	}
}
