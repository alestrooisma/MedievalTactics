package mt.controller.input;

import mt.controller.Controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A convenience class.
 * @author Ale Strooisma
 */
public abstract class AbstractMouseHandler implements MouseListener {

	protected Controller controller;

	public AbstractMouseHandler(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
}
