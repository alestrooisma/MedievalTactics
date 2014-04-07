package mt.model;

import java.util.LinkedList;

public class Army {

	private String name;
	private boolean defeated = false;
	private LinkedList<Unit> units = new LinkedList<Unit>();

	public Army(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isDefeated() {
		return defeated;
	}

	public void setDefeated(boolean defeated) {
		this.defeated = defeated;
	}

	public LinkedList<Unit> getUnits() {
		return units;
	}
	
	public void addUnit(Unit unit) {
		units.add(unit);
	}
	
	public void removeUnit(Unit unit) {
		units.remove(unit);
	}
	
	public boolean isEnemy(Army army) {
		//TODO allies
		return army != this;
	}
	
	public boolean isEnemy(Unit unit) {
		return isEnemy(unit.getArmy());
	}
}
