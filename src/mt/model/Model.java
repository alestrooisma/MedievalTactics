/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.model;

/**
 *
 * @author ale
 */
public class Model {

	private Map map;
	private Army[] armies;
	private int currentPlayer;
	private int turn;

	public Model(Map map, Army[] armies) {
		this.map = map;
		this.armies = armies;
	}

	public Model(Map map, Army[] armies, int currentPlayer, int turn) {
		this.map = map;
		this.currentPlayer = currentPlayer;
		this.armies = armies;
		this.turn = turn;
	}

	public Map getMap() {
		return map;
	}

	public Army[] getArmies() {
		return armies;
	}

	public Army getArmy(int player) {
		return armies[player];
	}

	public Army getCurrentArmy() {
		return armies[currentPlayer];
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void incrementCurrentPlayer() {
		do {
			currentPlayer = (currentPlayer + 1) % armies.length;
		} while (getCurrentArmy().isDefeated());
	}

	public int getTurn() {
		return turn;
	}

	public void incrementTurn() {
		turn++;
	}
}
