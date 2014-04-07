/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.controller;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import mt.model.*;
import mt.view.GUI;

/**
 *
 * @author ale
 */
public class Controller implements Runnable {
	
	private Model model;
	private GUI gui;
	private Unit selectedUnit = null;
	private ListIterator<Unit> unitIterator;
	// Pending cleanup (i.e. not sure what to do with it)
	private long turnStartTime;
	private Point2D cameraPosition;

	public Controller(Model model, GUI gui, Point2D cameraPosition) {
		this.model = model;
		this.gui = gui;
		this.cameraPosition = cameraPosition;
		Resources.loadResources();
	}

	@Override
	public void run() {
		try {
			gui.buildGuiAndWait();
		} catch (InterruptedException ex) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
		}
		gui.show();
		startTurn(getCurrentArmy());
		gui.setStatus("Welcome!", true);
	}

	// Getters
	public Model getModel() {
		return model;
	}

	public GUI getGui() {
		return gui;
	}

	public long getTurnStartTime() {
		return turnStartTime;
	}

	// Map wrapper getters
	public Map getMap() {
		return model.getMap();
	}

	public Army[] getArmies() {
		return model.getArmies();
	}

	public Army getArmy(int player) {
		return model.getArmy(player);
	}

	public Army getCurrentArmy() {
		return model.getCurrentArmy();
	}

	public int getCurrentPlayer() {
		return model.getCurrentPlayer();
	}

	public int getTurn() {
		return model.getTurn();
	}

	// Controller logic
	public Point2D getCameraPosition() {
		return cameraPosition;
	}

	public void setCameraPosition(Point2D cameraPosition) {
		this.cameraPosition.setLocation(cameraPosition);
	}

	public Unit getSelectedUnit() {
		return selectedUnit;
	}

	public void selectUnit(Unit unit) {
		if (Flags.CENTER_UPON_SELECT && unit != null) {
			setCameraPosition(unit.getPosition());
		}
		selectedUnit = unit;
	}

	public void deselectUnit() {
		selectedUnit = null;
	}

	public boolean moveSelectedUnit(Point tileCoords) {
		double distance = Util.walkDistance(selectedUnit.getPosition(), tileCoords);
		if (getMap().getTile(tileCoords).isAccessible()) {
			if (distance <= selectedUnit.getMovesRemaining()) {
				moveUnit(selectedUnit, tileCoords);
				selectedUnit.reduceMoves(distance);
				return true;
			} else if (selectedUnit.mayDash()
					&& distance <= selectedUnit.getMovesRemaining() + selectedUnit.getSpeed()) {
				moveUnit(selectedUnit, tileCoords);
				selectedUnit.setMovesRemaining(0);
				selectedUnit.setHasDashed();
				return true;
			}
		}
		return false;
	}

	private void moveUnit(Unit u, Point tileCoords) {
		getMap().getTile(u.getPosition()).removeUnit();
		u.setPosition(tileCoords);
		getMap().getTile(tileCoords).setUnit(u);
	}

	public void endTurn() {
		// Finalize turn
		deselectUnit();

		// Start next player's turn
		model.incrementCurrentPlayer();
		startTurn(getCurrentArmy());
	}

	private void startTurn(Army army) {
		if (getCurrentPlayer() == 0) {
			model.incrementTurn();
		}
		turnStartTime = System.currentTimeMillis();

		for (Unit u : army.getUnits()) {
			u.reset();
		}

		nextUnit();
	}

	/* TODO interator-safe unit destruction for when unit gets destroyed in own turn
	 * most likely with listIterator.remove, as it will only die when it is acting.
	 */
	public void nextUnit() {
		if (unitIterator == null || unitIterator.hasNext() == false) {
			unitIterator = getCurrentArmy().getUnits().listIterator();
		}

		Unit u = nextUnit2();
		if (u == null) {
			unitIterator = getCurrentArmy().getUnits().listIterator();
			u = nextUnit2();
		}
		selectUnit(u);
	}

	private Unit nextUnit2() {
		while (unitIterator.hasNext()) {
			Unit next = unitIterator.next();
			if (next.mayAct() || next.mayDash() || next.getMovesRemaining() >= 1) {
				return next;
			}
		}
		return null;
	}
}
