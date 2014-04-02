/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.model;

import java.awt.Point;

/**
 *
 * @author ale
 */
public class Unit {

	// Core fields
	private Army army;
	private Point position;
	//
	// Stats
	private int maxHealth;
	private double speed;
	//
	// Current status
	private int currentHealth;
	private double movesRemaining;
	private boolean mayDash;
	private boolean mayAct;

	public Unit(Army army, double speed, int maxHealth) {
		this.army = army;
		this.speed = speed;
		this.maxHealth = maxHealth;
	}

	public Army getArmy() {
		return army;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double movesPerTurn) {
		this.speed = movesPerTurn;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public double getMovesRemaining() {
		return movesRemaining;
	}

	public void setMovesRemaining(double movesRemaining) {
		this.movesRemaining = movesRemaining;
	}

	public void reduceMoves(double moves) {
		movesRemaining -= moves;
	}

	public boolean mayDash() {
		return mayDash;
	}

	public void setMayDash(boolean mayDash) {
		this.mayDash = mayDash;
	}

	public void setHasDashed() {
		setMayDash(false);
	}

	public boolean mayAct() {
		return mayAct;
	}

	public void setMayAct(boolean mayAct) {
		this.mayAct = mayAct;
	}

	public void setHasActed() {
		setMayAct(false);
	}

	public void reset() {
		movesRemaining = speed;
		mayDash = true;
		mayAct = true;
	}

	public void destroy(Map map) {
		getArmy().removeUnit(this);
		map.getTile(getPosition()).removeUnit();
	}

	public static Unit createUnit(Army army, double speed, int maxHealth) {
		Unit unit = new Unit(army, speed, maxHealth);
		unit.setCurrentHealth(maxHealth);
		army.addUnit(unit);
		return unit;
	}

	public static Unit createUnit(Army army, double speed, int maxHealth,
			Point pos, Map map) {
		Unit unit = createUnit(army, speed, maxHealth);
		unit.setPosition(pos);
		map.getTile(pos).setUnit(unit);
		return unit;
	}
}
