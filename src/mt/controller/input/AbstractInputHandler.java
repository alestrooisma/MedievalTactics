package mt.controller.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import mt.controller.Controller;

/**
 * Just a convenience class
 * @author Ale Strooisma
 */
public abstract class AbstractInputHandler extends AbstractMouseHandler implements KeyListener, MouseListener {

	public AbstractInputHandler(Controller controller) {
		super(controller);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
