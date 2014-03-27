package mt.controller.input;

import mt.controller.Controller;
import mt.model.Tile;
import mt.model.Unit;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Just a convenience class
 * @author Ale Strooisma
 */
public class MainInputHandler extends AbstractInputHandler {

	private boolean popupShown = false;
	private Tile tile;

	public MainInputHandler(Controller controller) {
		super(controller);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Unit u = controller.getSelectedUnit();
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				if (controller.getSelectedUnit() != null) {
					controller.deselectUnit();
				} else {
//					if (Dialog.getConfirmation(controller.getGui().getFrame(),
//							"Are you sure you want to quit the game?")) {
					System.exit(0);
//					}
				}
				break;
			case KeyEvent.VK_ENTER:
				controller.endTurn();
				break;
			case KeyEvent.VK_G:
				if (controller.getSelectedUnit() != null) {
					controller.toggleMoveMode();
				}
				break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point tileCoords = controller.getGui().windowToTile(e.getPoint());
		tile = controller.getMap().getTile(tileCoords);
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (popupShown) {
				popupShown = false;
			} else if (controller.isInMoveMode()) {
				controller.moveSelectedUnit(tileCoords);
			} else if (tile.getUnit() != null) {
				controller.selectUnit(tile.getUnit());
			} else {
				controller.deselectUnit();
			}
		}
	}
}
