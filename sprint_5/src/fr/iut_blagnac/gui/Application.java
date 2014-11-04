package fr.iut_blagnac.gui;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.sujet.Sujet;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.gui.tabs.TabAssociation;
import fr.iut_blagnac.gui.tabs.TabData;
import fr.iut_blagnac.gui.tabs.TabInformation;
import fr.iut_blagnac.gui.tabs.TabIntervenant;
import fr.iut_blagnac.gui.tabs.TabVoeux;
import fr.iut_blagnac.io.OptiElementManager;
import fr.iut_blagnac.util.ConfigFileManager;
import fr.iut_blagnac.util.MakeOptiWeb;

/**
 * Fenetre principale de l'application
 * 
 * @author Groupe 1B1
 * @version 2.0 Sprint 3
 */
@SuppressWarnings({ "serial", "unused" })
public class Application extends JFrame {
	public static Map<String, String> confString;
	public static Map<String, String> langString;

	private JFrame mainFrame = this;
	private ElementDisplayer displayer;
	JTabbedPane onglets;
	private DataManager currentDataManager;
	private TabAssociation associationTab;
	private TabInformation informationTab;
	private TabData dataTab;
	private TabVoeux wishesTab;
	private TabIntervenant advisorsTab;

	/*
	 * CONSTRUCTEUR
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Application(String titre) {
		super(titre);
		System.setProperty("file.encoding", "UTF-8");

		try {
			confString = ConfigFileManager.loadConfFile();
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							this,
							e.getMessage()
									+ "\nPlease delete config/init.conf and restart the Application with writing rights.",
							"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(ERROR);
		}

		try {
			langString = ConfigFileManager.loadLangFile(confString
					.get("language"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					this,
					"Was unable to load the language file \""
							+ confString.get("language") + "\"", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(ERROR);
		}

		currentDataManager = new DataManager();
		OptiElement.setDataManager(currentDataManager);

		// REGLAGE GENERAL DE LA FENETRE
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(800, 500);
		setLocationRelativeTo(null);

		// COMPORTEMENT FENETRE FERMETURE
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				fermer();
			}
		});

		// BARRE DES MENUS

		setJMenuBar(barreMenus());

		// ONGLETS
		onglets = new JTabbedPane(JTabbedPane.TOP);

		dataTab = new TabData(this, currentDataManager);
		associationTab = new TabAssociation(this, currentDataManager);
		informationTab = new TabInformation(currentDataManager);
		wishesTab = new TabVoeux(this, currentDataManager);
		advisorsTab = new TabIntervenant(this, currentDataManager);

		onglets.addTab(langString.get("data"), dataTab);
		onglets.addTab(langString.get("associate"), associationTab);
		onglets.addTab(langString.get("wishes"), wishesTab);
		onglets.addTab(
				langString.get("roles") + " " + langString.get("advisors"),
				advisorsTab);
		onglets.addTab(langString.get("about"), informationTab);

		onglets.addChangeListener(associationTab);
		onglets.addChangeListener(informationTab);
		onglets.addChangeListener(wishesTab);
		onglets.addChangeListener(advisorsTab);

		add(onglets);

		OptiElement[] loadedElements;
		ArrayList storedElements;

		// Chargement des étudiants
		try {
			loadedElements = OptiElementManager.open(this,
					new File(confString.get("etudiants")));
			storedElements = currentDataManager.getEtudiants();
			for (OptiElement element : loadedElements) {
				storedElements.add(element);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Chargement des intervenants
		try {
			loadedElements = OptiElementManager.open(this,
					new File(confString.get("intervenants")));
			storedElements = currentDataManager.getIntervenants();
			for (OptiElement element : loadedElements) {
				storedElements.add(element);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Chargement des sujets
		try {
			loadedElements = OptiElementManager.open(this,
					new File(confString.get("sujets")));
			storedElements = currentDataManager.getSujets();
			for (OptiElement element : loadedElements) {
				storedElements.add(element);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Chargement des projets

		try {
			loadedElements = OptiElementManager.open(this,
					new File(confString.get("projets")));
			storedElements = currentDataManager.getProjets();
			for (OptiElement element : loadedElements) {
				storedElements.add(element);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		currentDataManager.notifyDataChange();

	}

	/*
	 * Permet de créer la barre des menus de la fenêtre principale
	 * 
	 * @returns JMenuBar
	 */
	private JMenuBar barreMenus() {

		// BARRE DES MENUS

		// DECLARATION + INITIALISATION DES DIFFERENTS BOUTONS
		final JMenuBar barreMenu = new JMenuBar();

		JMenu charger = new JMenu(langString.get("load"));
		JMenu sauvegarder = new JMenu(langString.get("save"));
		JMenu option = new JMenu(langString.get("moptions"));
		JMenu fichier = new JMenu(langString.get("file"));

		final JMenuItem exportWeb = new JMenuItem(langString.get("exportWeb"));

		final JMenuItem loadEtudiants = new JMenuItem(langString.get("students") + "...");
		final JMenuItem loadIntervenants = new JMenuItem(langString.get("advisors") + "...");
		final JMenuItem loadProjets = new JMenuItem(langString.get("projects") + "...");
		final JMenuItem loadSujets = new JMenuItem(langString.get("subjects")
				+ "...");

		final JMenuItem saveEtudiants = new JMenuItem(
				langString.get("students") + "...");
		final JMenuItem saveIntervenants = new JMenuItem(
				langString.get("advisors") + "...");
		final JMenuItem saveProjets = new JMenuItem(langString.get("projects")
				+ "...");
		final JMenuItem saveSujets = new JMenuItem(langString.get("subjects")
				+ "...");
		final JMenuItem saveAll = new JMenuItem(langString.get("all") + "...");

		final JMenu langues = new JMenu(langString.get("mlanguages"));
		final ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButtonMenuItem langRadio;

		File langs = new File("langs/");
		for (final String lang : langs.list()) {
			// The value of the String below has an uppercase first letter,
			// which is why it looks kind of strange...
			langRadio = new JRadioButtonMenuItem(""
					+ Character.toString((char) (lang.charAt(0) - 32))
					+ lang.substring(1, lang.lastIndexOf(".lang")));
			buttonGroup.add(langRadio);
			langues.add(langRadio);

			// If the actual language is the one the application is currently in
			// :
			if (Application.confString.get("language").equals(
					lang.substring(0, lang.lastIndexOf(".lang"))))
				langRadio.setSelected(true);

			langRadio.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent evt) {
					if (evt.getStateChange() == ItemEvent.SELECTED) {
						try {
							ConfigFileManager.changeLanguage(lang.substring(0,
									lang.lastIndexOf(".lang")));
							JOptionPane.showMessageDialog(mainFrame,
									Application.langString
											.get("dialrestartnotice"),
									Application.langString.get("dialconfirm"),
									JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e) {
							JOptionPane
									.showMessageDialog(
											mainFrame,
											"An error has occured while modifying the config file.\nPlease delete config/init.conf and restart the Application with writing rights",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}

		JMenuItem quitter = new JMenuItem(langString.get("quit"));
		quitter.setMaximumSize(new Dimension(50, 50));

		fichier.add(exportWeb);

		charger.add(loadEtudiants);
		charger.add(loadIntervenants);
		charger.add(loadProjets);
		charger.add(loadSujets);

		sauvegarder.add(saveEtudiants);
		sauvegarder.add(saveIntervenants);
		sauvegarder.add(saveProjets);
		sauvegarder.add(saveSujets);
		sauvegarder.add(saveAll);

		option.add(langues);

		/*
		 * Début du réglage
		 */

		// AJOUT DES ActionListeners SUR LES BOUTONS
		quitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fermer();
			}
		});

		ActionListener dataUpdateListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				dataTab.revalidate();
				associationTab.revalidate();
				informationTab.revalidate();

				dataTab.repaint();
				associationTab.repaint();
				informationTab.repaint();

			}
		};

		exportWeb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exportWeb();
			}

		});

		saveEtudiants.addActionListener(new ElementsLoadNSave(saveEtudiants,
				currentDataManager, currentDataManager.getEtudiants(),
				Etudiant.class, false));
		saveEtudiants.addActionListener(dataUpdateListener);
		loadEtudiants.addActionListener(new ElementsLoadNSave(loadEtudiants,
				currentDataManager, currentDataManager.getEtudiants(),
				Etudiant.class, true));
		loadEtudiants.addActionListener(dataUpdateListener);

		saveIntervenants.addActionListener(new ElementsLoadNSave(
				saveIntervenants, currentDataManager, currentDataManager
						.getIntervenants(), Intervenant.class, false));
		saveIntervenants.addActionListener(dataUpdateListener);
		loadIntervenants.addActionListener(new ElementsLoadNSave(
				saveIntervenants, currentDataManager, currentDataManager
						.getIntervenants(), Intervenant.class, true));
		loadIntervenants.addActionListener(dataUpdateListener);

		saveProjets.addActionListener(new ElementsLoadNSave(saveProjets,
				currentDataManager, currentDataManager.getProjets(),
				Projet.class, false));
		saveProjets.addActionListener(dataUpdateListener);
		loadProjets.addActionListener(new ElementsLoadNSave(loadProjets,
				currentDataManager, currentDataManager.getProjets(),
				Projet.class, true));
		loadProjets.addActionListener(dataUpdateListener);

		saveSujets.addActionListener(new ElementsLoadNSave(saveSujets,
				currentDataManager, currentDataManager.getSujets(),
				Sujet.class, false));
		saveSujets.addActionListener(dataUpdateListener);
		loadSujets.addActionListener(new ElementsLoadNSave(saveSujets,
				currentDataManager, currentDataManager.getSujets(),
				Sujet.class, true));
		loadSujets.addActionListener(dataUpdateListener);

		saveAll.addActionListener(new ElementsLoadNSave(saveSujets,
				currentDataManager, currentDataManager.getSujets(),
				Sujet.class, false));
		saveAll.addActionListener(new ElementsLoadNSave(saveProjets,
				currentDataManager, currentDataManager.getProjets(),
				Projet.class, false));
		saveAll.addActionListener(new ElementsLoadNSave(saveIntervenants,
				currentDataManager, currentDataManager.getIntervenants(),
				Intervenant.class, false));
		saveAll.addActionListener(new ElementsLoadNSave(saveEtudiants,
				currentDataManager, currentDataManager.getEtudiants(),
				Etudiant.class, false));

		// AJOUT DES ITEM A LA BARRE
		barreMenu.add(fichier);
		barreMenu.add(charger);
		barreMenu.add(sauvegarder);
		barreMenu.add(option);
		barreMenu.add(quitter);

		return barreMenu;
	}

	/*
	 * Permet de définir le comportement de la fenêtre lors de l'appel de la
	 * méthode fermer() Affiche une fenêtre de confirmation et si choix=oui
	 * fermeture de la fenêtre
	 */
	private void fermer() {
		// AFFICHE UNE BOITE DE CONFIRMATION

		int reponse = JOptionPane.showOptionDialog(
				this,
				langString.get("dialexit"),
				langString.get("dialconfirm"),
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				new String[] { langString.get("dialyes"),
						langString.get("dialno") }, langString.get("dialno"));

		// SI OUI ALORS LA FENETRE SE FERME
		if (reponse == JOptionPane.YES_OPTION) {
			dispose();
			System.exit(0);
		}
	}

	private void exportWeb() {
		// ETUDIANTS
		if (currentDataManager.getEtudiants().size() != 0) {
			OptiElement[] dataEtu = new OptiElement[currentDataManager
					.getEtudiants().size()];
			dataEtu = currentDataManager.getEtudiants().toArray(dataEtu);
			try {
				OptiElementManager.save((Component) this, new File(
						"etudiants2014_2015.csv"), Etudiant.getCSVHeader(),
						dataEtu);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// INTERVENANTS
		if (currentDataManager.getIntervenants().size() != 0) {
			OptiElement[] dataInter = new OptiElement[currentDataManager
					.getIntervenants().size()];
			dataInter = currentDataManager.getIntervenants().toArray(dataInter);
			try {
				OptiElementManager.save((Component) this, new File(
						"intervenants2014_2015.csv"), Intervenant
						.getCSVHeader(), dataInter);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// SUJETS
		if (currentDataManager.getSujets().size() != 0) {
			OptiElement[] dataSubj = new OptiElement[currentDataManager
					.getSujets().size()];
			dataSubj = currentDataManager.getSujets().toArray(dataSubj);
			try {
				OptiElementManager.save((Component) this, new File(
						"sujets2014_2015.csv"), Sujet.getCSVHeader(), dataSubj);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// PROJETS
		if (currentDataManager.getProjets().size() != 0) {
			OptiElement[] dataProj = new OptiElement[currentDataManager
					.getProjets().size()];
			dataProj = currentDataManager.getProjets().toArray(dataProj);
			try {
				OptiElementManager.save((Component) this, new File(
						"projets2014_2015.csv"), Projet.getCSVHeader(),
						dataProj);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// GENERATION HTML PAGE
		MakeOptiWeb.writeAll();
		
		// LANCEMENT PAGE HTML
		try {
			Desktop.getDesktop().open(new File("./OPTIweb.html"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * EXECUTION
	 */
	public static void main(String[] args) throws FontFormatException,
			IOException {
		Application fen = new Application("Sprint" + " 5");
		fen.setVisible(true);
	}

}

class ElementsLoadNSave implements ActionListener {

	private ArrayList<OptiElement> currentElementsList;
	private JMenuItem currentMenuItem;
	private boolean load;
	private DataManager currentDataManager;
	private Class<?> currentElementType;

	public ElementsLoadNSave(JMenuItem menuItem, DataManager dataManager,
			ArrayList<OptiElement> elementsList, Class<?> elementType,
			boolean load) {
		this.load = load;
		this.currentMenuItem = menuItem;
		this.currentElementsList = elementsList;
		this.currentDataManager = dataManager;
		this.currentElementType = elementType;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (load) {
			load();
		} else {
			save();
		}

	}

	private void load() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
		chooser.setCurrentDirectory(new File("./"));
		switch (chooser.showOpenDialog(this.currentMenuItem)) {
		case JFileChooser.APPROVE_OPTION:
			try {
				OptiElement[] loadedElements = OptiElementManager.open(
						this.currentMenuItem, chooser.getSelectedFile());
				if (loadedElements.length == 0) {
					JOptionPane.showMessageDialog(currentMenuItem,
							"No elements found in the given file", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!loadedElements[0].getClass().equals(currentElementType)) {
					JOptionPane
							.showMessageDialog(
									currentMenuItem,
									"Wrong csv file, data not matching (trouver une phrase) ",
									"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				for (OptiElement element : loadedElements) {
					currentElementsList.add(element);
				}

				if (currentElementType == Etudiant.class)
					;
				currentDataManager.refreshGroupes();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			currentDataManager.notifyDataChange();
			break;
		case JFileChooser.ERROR_OPTION:
			JOptionPane.showMessageDialog(currentMenuItem,
					"Unable to load the selected file", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		}
		;

	}

	private void save() {
		JFileChooser chooser = new JFileChooser();
		if (load){
			chooser.setDialogTitle("Chargement des "
					+ this.currentElementType.getSimpleName());
		}else{
			chooser.setDialogTitle("Sauvegarde des "
					+ this.currentElementType.getSimpleName());
		}
		
		chooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
		chooser.setCurrentDirectory(new File("./"));
		switch (chooser.showSaveDialog(chooser)) {
		case JFileChooser.APPROVE_OPTION:
			OptiElement[] data = new OptiElement[currentElementsList.size()];
			data = currentElementsList.toArray(data);
			try {
				OptiElementManager.save(this.currentMenuItem,
						chooser.getSelectedFile(), data);

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this.currentMenuItem,
						"Unable to access the selected file", "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this.currentMenuItem, e,
						"Unknown Error", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case JFileChooser.ERROR_OPTION:
			JOptionPane.showMessageDialog(this.currentMenuItem,
					"Unable to write in the selected file", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		}
		;
	}
}
