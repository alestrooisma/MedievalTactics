/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.view;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/**
 * A class that provides an interface between the controller and the GUI. This
 * class should build and connect the GUI.
 *
 * @author Ale Strooisma
 */
public abstract class AbstractGUI {

	/**
	 * Creates an {@code AbstractGUI}.
	 */
	public AbstractGUI() {
	}
	
	/**
	 * Start building the GUI and return while the GUI is being built.
	 */
	public void buildGUI() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/**
	 * Build the GUI and return when it is done.
	 * @throws InterruptedException
	 * @throws InvocationTargetException 
	 */
	public void buildGuiAndWait() throws InterruptedException, InvocationTargetException {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/**
	 * Creates the GUI and makes it visible.
	 */
	abstract protected void createAndShowGUI();
	
	/**
	 * Updates the GUI to reflect the current state.
	 */
	abstract public void update();
}
