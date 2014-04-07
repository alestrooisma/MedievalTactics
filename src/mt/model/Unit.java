package mt.model;

import java.awt.Point;
import java.util.ArrayList;

public class Unit {

	// Core fields
	private String name;
	private Army army;
	private Point position;
	//
	// Stats
	private int maxHealth;
	private double speed;
	private ArrayList<Action> actions;
	//
	// Current status
	private int currentHealth;
	private double movesRemaining;
	private boolean mayDash;
	private boolean mayAct;

	public Unit(String name, Army army, int maxHealth, double speed) {
		//TODO how large pre-allocation size of actions?
		//TODO 9-element quick-selection list and a large full list?
		this(name, army, maxHealth, speed, new ArrayList<Action>(9));
	}

	protected Unit(String name, Army army, int maxHealth, double speed, ArrayList<Action> actions) {
		this.name = name;
		this.army = army;
		this.maxHealth = maxHealth;
		this.speed = speed;
		this.actions = actions;
//		while (actions.size() < 9) {
//			actions.add(null);
//		}
	}

	public String getName() {
		return name;
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

	public Action getAction(int i) {
		if (i > actions.size()) {
			return null;
		}
		return actions.get(i - 1);
	}

	public boolean addAction(Action a) {
		return actions.add(a);
	}

	public boolean removeAction(Action a) {
		return actions.remove(a);
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
	
	public boolean isEnemy(Unit unit) {
		return getArmy().isEnemy(unit);
	}

	public static Unit createUnit(String name, Army army, int maxHealth, double speed) {
		Unit unit = new Unit(name, army, maxHealth, speed);
		unit.setCurrentHealth(maxHealth);
		army.addUnit(unit);
		return unit;
	}

	public static Unit createUnit(String name, Army army, int maxHealth, double speed,
			Point pos, Map map) {
		Unit unit = createUnit(name, army, maxHealth, speed);
		unit.setPosition(pos);
		map.getTile(pos).setUnit(unit);
		return unit;
	}
}
