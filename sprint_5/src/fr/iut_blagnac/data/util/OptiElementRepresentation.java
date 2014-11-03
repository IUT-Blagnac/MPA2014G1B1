package fr.iut_blagnac.data.util;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class OptiElementRepresentation extends JPanel{

	public abstract OptiElement getOptiElement();
	
	public abstract boolean isEditable();
	
	public abstract void setEditable(boolean editable);
}
