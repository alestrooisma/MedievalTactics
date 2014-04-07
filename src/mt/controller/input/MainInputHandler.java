package mt.controller.input;

import mt.controller.Controller;
import mt.model.Tile;
import mt.model.Unit;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

/**
 * Just a convenience class
 * @author Ale Strooisma
 */
public class MainInputHandler extends AbstractInputHandler implements MouseMotionListener {

	private boolean popupShown = false;
	private Point dragOrigin;

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
			case KeyEvent.VK_B:
				break;
			case KeyEvent.VK_N:
				controller.nextUnit();
				break;
			case KeyEvent.VK_1:
				controller.selectAction(1);
				break;
			case KeyEvent.VK_2:
				controller.selectAction(2);
				break;
			case KeyEvent.VK_3:
				controller.selectAction(3);
				break;
			case KeyEvent.VK_4:
				controller.selectAction(4);
				break;
			case KeyEvent.VK_5:
				controller.selectAction(5);
				break;
			case KeyEvent.VK_6:
				controller.selectAction(6);
				break;
			case KeyEvent.VK_7:
				controller.selectAction(7);
				break;
			case KeyEvent.VK_8:
				controller.selectAction(8);
				break;
			case KeyEvent.VK_9:
				controller.selectAction(9);
				break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dragOrigin = e.getPoint();
		if (e.getButton() == MouseEvent.BUTTON1) {
			Point tileCoords = controller.getGui().windowToTile(e.getPoint());
			Tile tile = null;
			if (controller.getMap().contains(tileCoords)) {
				tile = controller.getMap().getTile(tileCoords);
			}
			if (popupShown) {
				popupShown = false;
			} else if (tile != null && controller.getSelectedUnit() != null 
					&& tile.getUnit() != null && controller.getSelectedAction() != null) {
				controller.selectTarget(tile.getUnit());
			} else if (tile != null && controller.getSelectedUnit() != null 
					&& controller.moveSelectedUnit(tileCoords)) {
			} else if (tile != null && tile.getUnit() != null && tile.getUnit().getArmy() == controller.getCurrentArmy()) {
				controller.selectUnit(tile.getUnit());
			} else {
				controller.deselectUnit();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK) {
			double distanceX = (double) (e.getPoint().x - dragOrigin.x) / 100;
			double distanceY = (double) (e.getPoint().y - dragOrigin.y) / 100;
			Point2D cpos = controller.getCameraPosition();
			controller.setCameraPosition(new Point2D.Double(cpos.getX() - distanceX, cpos.getY() - distanceY));
			dragOrigin = e.getPoint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
