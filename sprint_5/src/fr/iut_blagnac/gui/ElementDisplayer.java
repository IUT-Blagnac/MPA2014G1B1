package fr.iut_blagnac.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.util.DataChangeListener;
import fr.iut_blagnac.data.util.DataFilter;
import fr.iut_blagnac.data.util.DataManager;
import fr.iut_blagnac.data.util.DetailedOptiElementRepresentation;
import fr.iut_blagnac.data.util.OptiElement;
import fr.iut_blagnac.data.util.OptiElementRepresentation;


@SuppressWarnings("serial")
public class ElementDisplayer extends JPanel implements RecordListener, DataChangeListener {
	

	
	
	
	private JFrame currentApplicationWindow;
	private DataManager currentDataManager;
	private Collection<OptiElement> storedElements;
	
	private HashMap<String, DataFilter> filters = new HashMap<String, DataFilter>();
	
	private JPanel displayPanel;
	private JScrollPane pane;

	
	public ElementDisplayer(JFrame applicationWindow, DataManager manager, Collection<OptiElement> arrayList){
		this.currentApplicationWindow = applicationWindow;
		this.currentDataManager = manager;
		this.storedElements = arrayList;
		
		addAllElements(arrayList.toArray(new OptiElement[arrayList.size()]));

		displayPanel = new JPanel(){
			public void paint(Graphics g){
				super.paint(g);
				boolean gray = true;
				for(Component component : this.getComponents()){
					if(gray){
						component.setBackground(new Color(200, 200, 200));
					} else {
						component.setBackground(new Color(240, 240, 240));
					}
					gray  = !gray;
				}
			}
		};
		displayPanel.setLayout(new GridLayout(0, 1, 0, 2));
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(displayPanel, BorderLayout.NORTH);
	    pane = new JScrollPane(contentPane);
	    pane.getVerticalScrollBar().setUnitIncrement(16);
	    
	    this.setLayout(new BorderLayout());
	    this.add(pane, BorderLayout.CENTER);
	}
	

	public void addElement(OptiElement anOptiElement){
		this.storedElements.add(anOptiElement);
		this.displayPanel.add(anOptiElement.buildOptiElementRepresentation());
		
		this.currentDataManager.notifyDataChange();

		this.validate();
		pane.getVerticalScrollBar().setValue(pane.getVerticalScrollBar().getMaximum());
	}
	
	public OptiElement removeElement(OptiElement anOptiElement){
		if(this.storedElements.remove(anOptiElement)){
			this.currentDataManager.notifyDataChange();
		}
		return anOptiElement;
	}
	
	public void addAllElements(OptiElement[] optiElements){
		for(OptiElement anOptiElement : optiElements){
			this.storedElements.add(anOptiElement);
		}
		this.currentDataManager.notifyDataChange();
	}
	
	
	public OptiElement[] getData(){
		return storedElements.toArray(new OptiElement[storedElements.size()]);
	}
	
	
	public void addDataFilter(String name, DataFilter filter){
		filters.put(name, filter);
		dataChange();
	}

	public void removeDataFilter(String name){
		filters.remove(name);
		dataChange();
	}
	
	public void removeAllDataFilter(){
		filters.clear();
		dataChange();
	}
	


	@Override
	public void dataChange() {

		if(this.isVisible() && storedElements != null){
			this.displayPanel.removeAll();
			boolean currentElementMatchesFilters;
			for(OptiElement anOptiElement : storedElements){
				
				currentElementMatchesFilters = true;
				for(Entry<String, DataFilter> filter : filters.entrySet()){
					if(!filter.getValue().matchFilter(anOptiElement)){
						currentElementMatchesFilters = false;
					}
				}

				if(currentElementMatchesFilters ){
					boolean alreadyDisplayed = false;
					Component[] displayedComponents = this.displayPanel.getComponents();
					
					for(int i = displayedComponents.length - 1; i >=0; i--){
						if(displayedComponents[i] instanceof OptiElementRepresentation){
							if(((OptiElementRepresentation)displayedComponents[i]).getOptiElement().equals(anOptiElement)){
								alreadyDisplayed = true;
								break;
							}
						}
					}
					if(!alreadyDisplayed){
						JPanel representation = new DetailedOptiElementRepresentation(anOptiElement.buildOptiElementRepresentation(), anOptiElement.buildDetails(currentApplicationWindow, currentDataManager), this);
						
						representation.addMouseListener(new RightClickMenu(currentDataManager, this));
						
						this.displayPanel.add(representation);
					}
				}
			}
			
			this.displayPanel.invalidate();
			this.invalidate();
			this.displayPanel.validate();
			this.validate();
			this.displayPanel.repaint();
			this.repaint();
		}
	}
	

}

class RightClickMenu extends MouseAdapter {

	
	@SuppressWarnings("unused")
	private JPanel elementsRepresentation;
	private ElementDisplayer currentDisplayer;

	private DataManager currentDataManager;
	
	public RightClickMenu(DataManager dataManager, ElementDisplayer currentDisplayer) {
		this.currentDataManager = dataManager;
		this.currentDisplayer = currentDisplayer;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
		Object source = e.getSource();
		if(!(source instanceof OptiElementRepresentation)){
			return;
		}
		
		final OptiElement currentElement = ((OptiElementRepresentation) source).getOptiElement();
				
		if (e.getButton() == MouseEvent.BUTTON3) {
			final JWindow menu = new JWindow((JFrame) this.currentDisplayer.getRootPane().getParent());
			JPanel panelMenu = new JPanel();
			panelMenu.setLayout(new GridLayout(0, 1));
			
			panelMenu.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY), new EmptyBorder(2, 2, 2, 2)));
			menu.setContentPane(panelMenu);
			menu.setFocusable(true);
					
			menu.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					menu.dispose();
				}
			});
			
			EmptyBorder empBord = new EmptyBorder(0, 5, 0, 0);
			JLabel actionDelete = new JLabel(Application.langString.get("rcdelete"));
			actionDelete.setBorder(empBord);
			JLabel actionClone = new JLabel(Application.langString.get("rcclone"));
			actionClone.setBorder(empBord);
			
			actionDelete.setPreferredSize(new Dimension(100, 20));
			
			actionDelete.setOpaque(true);
			actionDelete.setVerticalTextPosition(JLabel.CENTER);
			actionDelete.addMouseListener(new MouseAdapter() {
				public void mouseEntered (MouseEvent e) {
					((JLabel) e.getSource()).setBackground(UIManager.getColor("Tree.selectionBackground"));
				}
				
				public void mouseExited (MouseEvent e) {
					((JLabel) e.getSource()).setBackground(null);
				}
				
				public void mouseReleased (MouseEvent e) {
					currentDisplayer.removeElement(currentElement);
					menu.dispose();
					currentDataManager.notifyDataChange();
				}
			});
			
			
			actionClone.setOpaque(true);
			actionClone.setVerticalTextPosition(JLabel.CENTER);
			if (currentElement.getClass().equals(Projet.class)) {
				actionClone.addMouseListener(new MouseAdapter() {
					public void mouseEntered (MouseEvent e) {
						((JLabel) e.getSource()).setBackground(UIManager.getColor("Tree.selectionBackground"));
					}
					
					public void mouseExited (MouseEvent e) {
						((JLabel) e.getSource()).setBackground(null);
					}
					
					public void mouseReleased (MouseEvent e) {
						if(currentElement != null){
							if(!((Projet)currentElement).isValid()){
								JOptionPane.showMessageDialog(menu, "Data invalid, can't clone", "Error", JOptionPane.ERROR_MESSAGE);
							} else {
								currentDisplayer.addElement(currentElement.clone());
								currentDataManager.notifyDataChange();
							}
						}
						menu.dispose();
					}
				});
			}
			else {
				actionClone.setForeground(Color.GRAY);;
			}
			
			panelMenu.add(actionDelete);
			//panelMenu.add(actionEdit);
			panelMenu.add(actionClone);
			
			menu.setLocation(e.getXOnScreen(), e.getYOnScreen());
			menu.pack();
			
			Dimension menuDimension = menu.getSize();
			menu.setMinimumSize(new Dimension((int) (menuDimension.width*1.5), menuDimension.height));
			//menu.setPreferredSize();
			menu.setVisible(true);
		}
	}
}
