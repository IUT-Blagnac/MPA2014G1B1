package fr.iut_blagnac.data.util;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public abstract class OptiElementFieldControleur implements DocumentListener {


	
	public abstract void updateData(String newData);


	@Override
	public void changedUpdate(DocumentEvent arg0) {
		try {
			updateData(arg0.getDocument().getText(0, arg0.getDocument().getLength()));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		try {
			updateData(arg0.getDocument().getText(0, arg0.getDocument().getLength()));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		try {
			updateData(arg0.getDocument().getText(0, arg0.getDocument().getLength()));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
