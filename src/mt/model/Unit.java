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
	
	// Types
//	public static final int SETTLER = 0;
//	public static final int WARRIOR = 1;
	
	// Fields
	private int type;
	private Point position;
	private Army army;
	private double movesRemaining;
	private double movesPerTurn;
	private int strength;
	private int currentHealth;
	private int maxHealth;

	public Unit(Army army, int type, double movesPerTurn, int strength, int maxHealth) {
		this.army = army;
		this.type = type;
		this.movesPerTurn = movesPerTurn;
		this.strength = strength;
		this.currentHealth = maxHealth;
		this.maxHealth = maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getMovesPerTurn() {
		return movesPerTurn;
	}

	public void setMovesPerTurn(double movesPerTurn) {
		this.movesPerTurn = movesPerTurn;
	}

	public double getMovesRemaining() {
		return movesRemaining;
	}

	public void setMovesRemaining(double movesRemaining) {
		this.movesRemaining = movesRemaining;
	}

	public void resetMoves() {
		movesRemaining = movesPerTurn;
	}
	
	public void reduceMoves(double moves) {
		movesRemaining -= moves;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getType() {
		return type;
	}

	public Army getArmy() {
		return army;
	}
	
	public static Unit createUnit(Army army, int type, 
			double movesPerTurn, int strength, int maxHealth) {
		Unit unit = new Unit(army, type, movesPerTurn, strength, maxHealth);
		army.addUnit(unit);
		return unit;
	}
	
	public static Unit createUnit(Army army, int type, 
			double movesPerTurn, int strength, int maxHealth, Point pos, Map map) {
		Unit unit = createUnit(army, type, movesPerTurn, strength, maxHealth);
		unit.setPosition(pos);
		map.getTile(pos).setUnit(unit);
		return unit;
	}
	
	public void destroy(Map map) {
		getArmy().removeUnit(this);
		map.getTile(getPosition()).removeUnit();
	}
}
