package fr.iut_blagnac.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.DataChangeListener;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.voeux.Voeux;
import fr.iut_blagnac.gui.Application;
import fr.iut_blagnac.util.Str;

/**
 * This class is used to define the wishes tab on which you can enter the
 * group's wishes
 * 
 * @author Groupe 1B1
 */

@SuppressWarnings("serial")
public class TabVoeux extends JPanel implements ChangeListener,
		DataChangeListener {

	// All datas are in the DataManager
	private DataManager currentDataManager;

	// The JList of subject
	private JList<Sujet> listeSujets;

	// The JList of group
	private JList<String> listeGroupes;

	// The JTextArea of wishes
	private JTextArea saisieOrdreVoeux;

	// The parent Frame
	private JFrame parent;

	/**
	 * Create a new TabVoeux with a DataManager and a parent Window
	 * @param parent : The parent window
	 * @param currentDataManager : The DataManager
	 */
	public TabVoeux(JFrame parent, DataManager currentDataManager) {

		// Declaration
		this.currentDataManager = currentDataManager;
		this.parent = parent;

		// ////// Panel's Settings ///////

		// Set the layout of this JPanel
		this.setLayout(new BorderLayout());

		// Declaration of two new JPanel to organise the JPanel
		JPanel center = new JPanel(new GridLayout(1, 2, 20, 20));
		JPanel right = new JPanel(new BorderLayout(5, 5));
		JPanel rightSud = new JPanel(new BorderLayout(5, 5));

		// Set the border of panel center
		center.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

		// Add to this
		this.add(center, BorderLayout.CENTER);

		// Creation of a data manager to store the data used
		currentDataManager = new DataManager();

		// Initialization of the JLists and the text area (for the order input)
		listeSujets = new JList<Sujet>(new DefaultListModel<Sujet>());
		listeGroupes = new JList<String>(new DefaultListModel<String>());

		// Set some settings on JList and JTextArea
		listeSujets.setEnabled(false);
		listeGroupes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JLabel infoOrdreVouex = new JLabel(
				"<html><font size=2>"+Application.langString.get("dialinfowishes") +"<br>"
				+ Application.langString.get("dialexample")+ " : <i>1,5,6,2,...</i></font></html>");
		saisieOrdreVoeux = new JTextArea(3, 20);
		saisieOrdreVoeux.setWrapStyleWord(true);
		saisieOrdreVoeux.setLineWrap(true);

		JScrollPane scroll = new JScrollPane(saisieOrdreVoeux);

		// To fill in the wishes textArea according to the group selected
		listeGroupes.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String selection = listeGroupes.getSelectedValue();
				// Test if one object is selected
				if (selection != null) {
					// To split the string into the JList and retrieve the id
					// selected
					String[] selectionGroupe = selection.split(" ");
					// To retrieve the group id from the selection
					String strChoix = selectionGroupe[0];

					// Declaration of a new group to get a reference to our
					// datas
					Groupe G1 = new Groupe();
					// To retrieve the group selected into our datas
					// Test if the group exists in our datas, JUST TO PREVENT A
					// NULL POINTER
					if (TabVoeux.this.currentDataManager
							.getGroupeWhere(strChoix) != null) {
						G1 = TabVoeux.this.currentDataManager
								.getGroupeWhere(strChoix);
						// Set the text of the wishes textArea

						String ordre = "";
						int i = 0;

						// Go through the list of wishes of the group to take
						// back the number of each wishes and their subject
						for (Voeux v : G1.getListVoeux()) {
							if (i == G1.getListVoeux().size() - 1) {
								ordre += v.getNumVoeux();
							} else {
								ordre += v.getNumVoeux() + ",";
							}
							i++;
						}
						// To fill in the textArea when a group is selected
						saisieOrdreVoeux.setText(ordre);
					}
				}
			}
		});
		// Declaration of the JButton validate
		JButton validate = new JButton(Application.langString.get("rcaccept"));
		validate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validation();
			}
		});

		// Creation of two JScrollPanes to display the lists of subjects and
		// groups
		JScrollPane sujetsScrollPane = new JScrollPane(listeSujets);
		JScrollPane groupesScrollPane = new JScrollPane(listeGroupes);

		// Creation of borders for the scroll panes
		sujetsScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				Application.langString.get("subjects")));
		groupesScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				Application.langString.get("groups")));
		scroll.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				Application.langString.get("order")));

		// Adding elements to the window
		right.add(groupesScrollPane);
		rightSud.add(infoOrdreVouex, BorderLayout.CENTER);
		rightSud.add(scroll, BorderLayout.SOUTH);
		// rightSud.add(scroll, BorderLayout.AFTER_LAST_LINE);

		center.add(sujetsScrollPane);
		center.add(right);
		right.add(rightSud, BorderLayout.SOUTH);
		this.add(validate, BorderLayout.SOUTH);

		updateData();
	}

	/**
	 * Used to recalculate all data and update the display
	 */
	public void updateData() {

		DefaultListModel<Sujet> listeSujetsModel = (DefaultListModel<Sujet>) listeSujets
				.getModel();
		listeSujetsModel.removeAllElements();
		for (OptiElement sujet : currentDataManager.getSujets()) {
			sujet.isValid();
			listeSujetsModel.addElement((Sujet) sujet);
		}

		DefaultListModel<String> listeGroupesModel = (DefaultListModel<String>) listeGroupes
				.getModel();
		listeGroupesModel.removeAllElements();
		HashMap<String, Integer> mappy = currentDataManager.getAllGroups();
		for (Entry<String, Integer> group : mappy.entrySet()) {
			listeGroupesModel.addElement(group.getKey() + " ("
					+ (group.getValue() + 1) + ")");
		}

		listeSujets.revalidate();
		listeSujets.repaint();

		listeGroupes.revalidate();
		listeGroupes.repaint();

	}

	/**
	 * Used to update the display
	 */
	public void revalidate() {
		if (listeSujets != null)
			listeSujets.revalidate();
		if (listeGroupes != null)
			listeGroupes.revalidate();
		super.revalidate();
	}

	/**
	 * Used to update the display
	 */
	public void repaint() {
		if (listeSujets != null)
			listeSujets.repaint();
		if (listeGroupes != null)
			listeGroupes.repaint();
		super.repaint();
	}

	/**
	 * Used to updateData() if the state have changed
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		updateData();
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
	 * Method called when Validate button is clicked
	 */
	public void validation() {
		@SuppressWarnings("unused")
		String choix = "";
		int error = -1;

		// Test if the textArea is empy or not
		if (!saisieOrdreVoeux.getText().isEmpty()) {
			choix = saisieOrdreVoeux.getText();
		} else {
			// The error will be display after
			error = 1;
		}

		// Test if one group is selected into the list
		if (listeGroupes.getSelectedValue() != null) {
			String[] tabGpe = listeGroupes.getSelectedValue().split(" ");
			String gpe = tabGpe[0];
			Groupe G1 = currentDataManager.getGroupeWhere(gpe);

			// Test if the parser is valid for order according to the number of
			// subject
			if (Str.isOrderValid(saisieOrdreVoeux.getText(), currentDataManager
					.getSujets().size())) {
				String[] str = saisieOrdreVoeux.getText().split(",");

				for (int i = 0; i < str.length; i++) {
					Voeux V1 = new Voeux(Integer.parseInt(str[i]), G1,
							currentDataManager.getSujetWhereIdSujet(""
									+ (i + 1)));
					G1.getListVoeux().add(V1);

					Sujet S1 = currentDataManager.getSujetWhereIdSujet(""
							+ (i + 1));
					S1.getListVoeux().add(V1);
				}

				if (G1.getListVoeux().size() == 15) {
					JOptionPane.showMessageDialog(parent,
							Application.langString.get("dialvalidvoeux")+" " +G1, Application.langString.get("congrat"),
							JOptionPane.INFORMATION_MESSAGE);
				}

			} else {
				// The error will be display after
				error = 3;
			}

		} else {
			error = 2;
		}

		// Display of errrors
		if (error == 1) {
			JOptionPane.showMessageDialog(parent,
					Application.langString.get("dialerrororderfieldnull"),
					Application.langString.get("dialtitleerrororderfieldnull"),
					JOptionPane.ERROR_MESSAGE);
		} else if (error == 2) {
			JOptionPane.showMessageDialog(parent,
					Application.langString.get("dialerror0selectgroup"),
					Application.langString.get("dialtitleerror0select"),
					JOptionPane.ERROR_MESSAGE);
		} else if (error == 3) {
			JOptionPane.showMessageDialog(parent,
					Application.langString.get("dialerrornbwishes"),
					Application.langString.get("dialtitlenbwishes"),
					JOptionPane.ERROR_MESSAGE);
			saisieOrdreVoeux.setText("");
		}

	}

}
