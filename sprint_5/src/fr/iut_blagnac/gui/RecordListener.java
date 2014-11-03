package fr.iut_blagnac.gui;

import fr.iut_blagnac.data.util.OptiElement;

public interface RecordListener {
	public void addElement(OptiElement anElement);
	public OptiElement removeElement(OptiElement anElement);
}
