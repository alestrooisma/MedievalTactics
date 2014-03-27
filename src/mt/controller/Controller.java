/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.controller;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mt.model.*;
import mt.view.GUI;

/**
 *
 * @author ale
 */
public class Controller implements Runnable{

	private Model model;
	private GUI gui;
	private Unit selectedUnit = null;
	// Pending cleanup (i.e. not sure what to do with it)
	private long turnStartTime;
	private boolean moveMode = false;
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

	public Army[] getArmys() {
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
		this.cameraPosition = cameraPosition;
	}

	public Unit getSelectedUnit() {
		return selectedUnit;
	}

	public void selectUnit(Unit unit) {
		setCameraPosition(unit.getPosition());
		selectedUnit = unit;
	}

	public void deselectUnit() {
		moveMode = false;
		selectedUnit = null;
	}

	public void moveSelectedUnit(Point tileCoords) {
		double distance = Util.walkDistance(selectedUnit.getPosition(), tileCoords);
		if (distance <= selectedUnit.getMovesRemaining()) {
			getMap().getTile(selectedUnit.getPosition()).removeUnit();
			selectedUnit.setPosition(tileCoords);
			getMap().getTile(tileCoords).setUnit(selectedUnit);
			selectedUnit.reduceMoves(distance);
		}
		moveMode = false;
	}
	
	public void toggleMoveMode() {
		moveMode = !moveMode;
	}

	public boolean isInMoveMode() {
		return moveMode;
	}

	public void endTurn() {
		// Finalize turn
		deselectUnit();

		// Start next player's turn
		model.incrementCurrentPlayer();
		startTurn(getCurrentArmy());
	}

	private void startTurn(Army civ) {
		if (getCurrentPlayer() == 0) {
			model.incrementTurn();
		}
		turnStartTime = System.currentTimeMillis();
	}
}
