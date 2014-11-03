package fr.iut_blagnac.data.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import fr.iut_blagnac.gui.Application;
import fr.iut_blagnac.gui.RecordListener;

@SuppressWarnings("serial")
public class DetailedOptiElementRepresentation extends OptiElementRepresentation{

	private OptiElement currentElement;
	private OptiElementRepresentation currentElementRepresentation;
	JButton removeButton;
	JButton detailsButton;

	/** 
	 * @return : the GUI of the object with JButton to remove it from a RecordListener
	 */
	public DetailedOptiElementRepresentation(final OptiElementRepresentation representation, final JDialog details,  final RecordListener listener) {
		this.setLayout(new BorderLayout());
		this.currentElement = representation.getOptiElement();
		this.currentElementRepresentation = representation;
		
		removeButton = new JButton(UIManager.getIcon("OptionPane.errorIcon"));
		removeButton.setMaximumSize(new Dimension(34, 34));
		removeButton.setPreferredSize(new Dimension(34, 34));
		//removeButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		
		removeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.removeElement(currentElement);
			}
		});
		

		representation.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		
		
		if (details != null) {
			detailsButton = new JButton(Application.langString.get("details"));
			detailsButton.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent e) {
					details.setVisible(true);
				}
			});
			this.add(detailsButton, BorderLayout.EAST);
		}
		
		this.add(representation, BorderLayout.CENTER);
		this.add(removeButton, BorderLayout.WEST);
		
	}
	
	public void paint(Graphics g){
		if(removeButton.isVisible()!=isEditable()){
			removeButton.setVisible(isEditable());
			validate();
		}
		super.paint(g);
	}
	
	public OptiElement getOptiElement(){
		return currentElement;
	}
	
	
	public boolean equals(Object e){
		if(e instanceof OptiElementRepresentation){
			return currentElement.equals(((OptiElementRepresentation)e).getOptiElement());
		}
		return e.equals(this);
	}
	
	public void setBackground(Color color){
		super.setBackground(color);
		for(Component component : this.getComponents()){
			component.setBackground(color);
		}

		this.setBorder(new MatteBorder(4, 0, 4, 0, color));
	}

	@Override
	public boolean isEditable() {
		return currentElementRepresentation.isEditable();
	}

	@Override
	public void setEditable(boolean editable) {
		currentElementRepresentation.setEditable(editable);
		
	}
}
