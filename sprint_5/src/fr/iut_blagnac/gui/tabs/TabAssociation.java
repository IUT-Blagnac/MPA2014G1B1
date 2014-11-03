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
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.DataChangeListener;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.gui.Application;

/**
 * This class is used to define the association tab on which you can create a
 * project from group and a subject
 * 
 * @version 2.0
 * @author Groupe 1B1
 */
// ERROR, WE'VE TO CHANGE THE DISPLAY MODE FOR GROUP, NOT STRING BUT OBJECT
@SuppressWarnings("serial")
public class TabAssociation extends JPanel implements ChangeListener,
		DataChangeListener {

	// All datas are in the DataManager
	private DataManager currentDataManager;

	// The JList of projects
	private JList<Projet> listeProjets;

	// The JList of subjects
	private JList<Sujet> listeSujets;

	// The JList of groups
	private JList<Groupe> listeGroupes;

	// The parent window
	private JFrame parent;

	private boolean updatingData;

	/**
	 * Create a new TabAssociation with a DataManager and a parent Window
	 * @param parent
	 * @param currentDataManager
	 */
	public TabAssociation(JFrame parent, DataManager currentDataManager) {

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
		listeSujets = new JList<Sujet>(new DefaultListModel<Sujet>());
		listeGroupes = new JList<Groupe>(new DefaultListModel<Groupe>());

		// Set some settings on JList
		listeGroupes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeProjets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Declaration of the JButton validate
		JButton associate = new JButton(Application.langString.get("associate"));
		associate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				association();
			}
		});

		// To adjust the group selected and the subject selected according to
		// the project selected
		listeProjets.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (updatingData) {
					return;
				}
				// Test if a project is selected
				if (listeProjets.getSelectedValue() != null) {
					// Take back the selected project, his subject and his group
					Projet selection = listeProjets.getSelectedValue();
					String groupe = selection.getGroupe().getLibelle();
					String id = selection.getSujet().getId();

					// Select the subject of the project selected
					for (int i = 0; i < listeSujets.getModel().getSize(); i++)
						if (listeSujets.getModel().getElementAt(i).getId()
								.equals(id))
							listeSujets.setSelectedValue(listeSujets.getModel()
									.getElementAt(i), true);

					// Select the group of the project selected
					for (int i = 0; i < listeGroupes.getModel().getSize(); i++)
						if (listeGroupes.getModel().getElementAt(i)
								.getLibelle().equals(groupe))
							listeGroupes.setSelectedValue(listeGroupes
									.getModel().getElementAt(i), true);
				} else {
					// If zero project selected, zero subject and group selected
					listeSujets.setSelectedIndices(new int[] {});
					listeGroupes.setSelectedIndices(new int[] {});

				}
			}
		});

		// Creation of the Label that explains how to use the association tab
		JLabel infoAssociation = new JLabel("<html><font size=3>"
				+ Application.langString.get("dialinfoassociate")
				+ "</font> </html>");
		// Creation of three JScrollPanes to display the lists of subjects,
		// projects and groups
		JScrollPane projetsScrollPane = new JScrollPane(listeProjets);
		JScrollPane groupesScrollPane = new JScrollPane(listeGroupes);
		JScrollPane sujetsScrollPane = new JScrollPane(listeSujets);

		// Set the border of JScrollPane
		projetsScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				Application.langString.get("projects")));
		groupesScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				Application.langString.get("groups")));
		sujetsScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				Application.langString.get("subjects")));

		// Add two ScrollPane to the right panel
		right.add(groupesScrollPane, BorderLayout.NORTH);
		right.add(sujetsScrollPane, BorderLayout.CENTER);

		// Add ScrollPane to the center panel
		north.add(infoAssociation);
		center.add(projetsScrollPane);
		center.add(right);

		// Updating data
		updateData();

		this.currentDataManager.addDataChangeListener(new DataChangeListener() {

			@Override
			public void dataChange() {
				updateData();

			}
		});

		this.add(associate, BorderLayout.SOUTH);
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

		DefaultListModel<Sujet> listeSujetsModel = (DefaultListModel<Sujet>) listeSujets
				.getModel();
		listeSujetsModel.removeAllElements();
		for (OptiElement sujet : currentDataManager.getSujets()) {
			sujet.isValid();
			listeSujetsModel.addElement((Sujet) sujet);
		}

		DefaultListModel<Groupe> listeGroupesModel = (DefaultListModel<Groupe>) listeGroupes
				.getModel();
		listeGroupesModel.removeAllElements();
		for (OptiElement groupe : currentDataManager.getGroupes()) {
			groupe.isValid();
			listeGroupesModel.addElement((Groupe) groupe);
		}

		updatingData = false;

		listeProjets.revalidate();
		listeProjets.repaint();
		listeGroupes.revalidate();
		listeGroupes.repaint();
		listeSujets.revalidate();
		listeSujets.repaint();

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
		if (listeGroupes != null)
			listeGroupes.revalidate();
		if (listeSujets != null)
			listeSujets.revalidate();
		super.revalidate();
	}

	/**
	 * Used to update the display
	 */
	public void repaint() {
		if (listeProjets != null)
			listeProjets.repaint();
		if (listeGroupes != null)
			listeGroupes.repaint();
		if (listeSujets != null)
			listeSujets.repaint();
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
	private void association() {
		boolean error = false;

		if (listeGroupes.getSelectedIndex() != -1
				&& listeSujets.getSelectedIndex() != -1) {

			// Recuperation of the group
			Groupe selectedGroupe = listeGroupes.getSelectedValue();

			// Recuperation of the subject
			Sujet selectedSujet = listeSujets.getSelectedValue();

			// Création of a project
			Projet project = new Projet(selectedSujet, selectedGroupe);

			// Test if the project doesn't exist yet
			for (OptiElement p : currentDataManager.getProjets()) {
				if (project.getSujet().equals(((Projet) p).getSujet())
						&& project.getGroupe().equals(((Projet) p).getGroupe())) {
					JOptionPane.showMessageDialog(parent,
							Application.langString.get("erroralreadyexist"),
							Application.langString.get("error"),
							JOptionPane.ERROR_MESSAGE);
					error = true;
					break;
				}
			}

			if (selectedGroupe.getProjet() != null && !error) {
				error = true;
				JOptionPane.showMessageDialog(parent,
						Application.langString.get("errorgroupalreadyhave"),
						Application.langString.get("error"),
						JOptionPane.ERROR_MESSAGE);
			}
			
			if (selectedSujet.getProjet() != null && !error) {
				error = true;
				JOptionPane.showMessageDialog(parent,
						Application.langString.get("errorsubjectalreadyhave"),
						Application.langString.get("error"),
						JOptionPane.ERROR_MESSAGE);
			}

			if (!error){
				selectedGroupe.setProjet(project);
				project.setSujet(selectedSujet);
				project.setGroupe(selectedGroupe);
				selectedSujet.setProjet(project);
				currentDataManager.getProjets().add(project);
				dataChange();
				JOptionPane.showMessageDialog(
						parent,
						Application.langString.get("validassociatep1")
								+ " "
								+ project.getGroupe()
								+ " "
								+ Application.langString
										.get("validassociatep2") + " "
								+ project.getSujet(), Application.langString
								.get("congrat"),
						JOptionPane.INFORMATION_MESSAGE);
			}

			dataChange();
		} else {
			JOptionPane.showMessageDialog(parent,
					Application.langString.get("errorselectassociate"),
					Application.langString.get("error"),
					JOptionPane.WARNING_MESSAGE);
		}
	}

}
