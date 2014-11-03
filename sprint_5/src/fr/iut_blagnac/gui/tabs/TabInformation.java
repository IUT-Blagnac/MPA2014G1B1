package fr.iut_blagnac.gui.tabs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.iut_blagnac.data.util.DataChangeListener;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.gui.Application;

@SuppressWarnings("serial")
public class TabInformation extends JPanel implements ChangeListener {
	
	private DataManager currentDataManager;
	JTextArea nbObj;

	/*
	 * Permet de remplir l'onglet "A propos"
	 */
	public TabInformation (DataManager currentDataManager) {
		this.currentDataManager = currentDataManager;
		
		this.setLayout(new BorderLayout());
		// Creation des dfférents JPanel nécessaire
		JPanel top = new JPanel(new BorderLayout());
		JPanel center = new JPanel(new BorderLayout());
		JPanel centerCenter = new JPanel(new BorderLayout());
		JPanel centerCenterWest = new JPanel(new BorderLayout());
		JPanel bottom = new JPanel();
		
		// Création des différents JLabel contenant les informations
		JTextArea list = new JTextArea(Application.langString.get("abgroup").replace("</br>", "\n").replace("<br>", "\n"));
		JTextArea iut = new JTextArea(Application.langString.get("abiut").replace("</br>", "\n").replace("<br>", "\n"));

		list.setEditable(false);
		list.setFocusable(false);
		
		iut.setEditable(false);
		iut.setFocusable(false);
		
		nbObj = new JTextArea();
		nbObj.setEditable(false);
		nbObj.setFocusable(false);
		
		JLabel sprint = new JLabel(Application.langString.get("absprint"));
		sprint.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Ajout des différents blocs de texte (JLabel) aux JPanel
		top.add(sprint, BorderLayout.NORTH);
		top.add(list, BorderLayout.CENTER);		
		
		// Ajout de centerCenterWest 
		centerCenter.add(centerCenterWest, BorderLayout.WEST);
				
		// Ajout de centerWest et centerCenter à center
		center.add(iut, BorderLayout.WEST);
		center.add(nbObj, BorderLayout.CENTER);
		
		// Ajout de bordures aux différents blocs (JPanel) d'informations
		list.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("abgrouptitle")),BorderFactory.createEmptyBorder(8, 8,8,8))));		
		iut.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("abiuttitle")),BorderFactory.createEmptyBorder(8, 8,8,8))));
		nbObj.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Application.langString.get("abdatatitle")),BorderFactory.createEmptyBorder(8, 8,8,8))));
		bottom.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),BorderFactory.createEmptyBorder(8, 8,8,8)));
		
		

		this.currentDataManager.addDataChangeListener(new DataChangeListener() {
			@Override
			public void dataChange() {
				updateData();
				
			}
		});

		/*top.setBackground(Color.WHITE);
		centerWest.setBackground(Color.WHITE);
		centerCenterWest.setBackground(Color.WHITE);
		*/
		
		nbObj.setBackground(top.getBackground());
		iut.setBackground(top.getBackground());
		list.setBackground(top.getBackground());
		bottom.setBackground(top.getBackground());
		
		// Ajout des différents JPanel au JPanel Principal
		this.add(top, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);	
		
	}

	public void updateData() {
		StringBuilder elementsCounter = new StringBuilder();
		elementsCounter.append(Application.langString.get("abnbstudents") + " : " + currentDataManager.getEtudiants().size() + "\n");
		elementsCounter.append(Application.langString.get("abnbadvisors") + " : " + currentDataManager.getIntervenants().size() + "\n");
		elementsCounter.append(Application.langString.get("abnbsubjects") + " : " + currentDataManager.getSujets().size() + "\n");
		elementsCounter.append(Application.langString.get("abnbprojects") + " : " + currentDataManager.getProjets().size() + "\n");
		elementsCounter.append(Application.langString.get("abnbgroups") + " : " + currentDataManager.getGroupes().size());
		
		nbObj.setText(elementsCounter.toString());
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		updateData();
	}
}
