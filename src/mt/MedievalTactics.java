package mt;

import java.awt.Point;
import java.awt.geom.Point2D;
import mt.controller.Controller;
import mt.model.Action;
import mt.model.Army;
import mt.model.FireAction;
import mt.model.Map;
import mt.model.Model;
import mt.model.Tile;
import mt.model.Unit;
import mt.view.GUI;

public class MedievalTactics {

	/**
	 * The main function used for running MedievalTactics
	 * 
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		int width = 11, height = 9;
		Tile[] tiles = new Tile[width * height];
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(new Point(i % width, i / width), 0);
		}
		Map map = new Map(0, width - 1, 0, height - 1, tiles);

		Action fire = new FireAction();
		
		Army player = new Army("player1");
		Unit u = Unit.createUnit("Jon", player, 3, 2.5, new Point(4, 7), map);
		u.addAction(fire);
		u = Unit.createUnit("Robb", player, 3, 2.5, new Point(5, 7), map);
		u.addAction(fire);
		u = Unit.createUnit("Theon", player, 3, 2.5, new Point(8, 7), map);
		u.addAction(fire);

		Army enemy = new Army("AI enemy");
		u = Unit.createUnit("Tywin", enemy, 3, 2.5, new Point(3, 1), map);
		u.addAction(fire);
		u = Unit.createUnit("Jaime", enemy, 3, 2.5, new Point(4, 0), map);
		u.addAction(fire);

		GUI gui = new GUI();
		Model model = new Model(map, new Army[]{player, enemy});
		Controller controller = new Controller(model, gui,
				new Point2D.Double(((double) width) / 2 - 0.5, ((double) height) / 2 - 0.5));
		gui.setController(controller);
		controller.run();
	}
}
