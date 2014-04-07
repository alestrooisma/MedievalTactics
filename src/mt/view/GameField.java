package mt.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import mt.controller.Controller;
import mt.controller.Resources;
import mt.controller.Util;
import mt.model.Map;
import mt.model.Tile;

/**
 * A panel on which the map is drawn.
 *
 * @author ale
 */
public class GameField extends Panel {

	/**
	 * The width and height of a tile. TODO: where to define size? Probably
	 * (partially) a settings thing.
	 */
	public static final int TILE_SIZE = 100;
	public static final Color MOVE_COLOR = new Color(0, 0, 1, 0.7f);
	public static final Color DASH_COLOR = new Color(1, 1, 0, 0.7f);
	private long last = System.nanoTime();
	private double framerate;
	private double[] framerates = new double[60];
	private int n;
	private Point offset;
	private int v, w;
	private String status = "";
	private boolean fadeStatus = false;
	private long statusTime;

	/**
	 * Creates the {@code GameField} component. Requires a GUI to acquire data
	 * through.
	 *
	 * @param gui the GUI this component is part of
	 */
	public GameField(GUI gui) {
		super(gui);
		this.gui = gui;
	}

	@Override
	protected void render() {
		correctCameraPosition();
		offset = worldToWindow(ZERO);

		drawMap();
		drawHUD();
	}

	private void drawMap() {
		// x and y are coordinates on the grid
		// v and w are coordinates on this panel

		Map map = gui.getController().getMap();

		g.setColor(Color.BLACK);
		g.drawRect(offset.x - 1, offset.y - 1,
				(map.getMinX() + map.getMaxX() + 1) * TILE_SIZE + 1,
				(map.getMinY() + map.getMaxY() + 1) * TILE_SIZE + 1);

		int vOffset = -offset.x;
		int wOffset = -offset.y;

		int xMin = Math.max(map.getMinX(), vOffset / TILE_SIZE);
		int yMin = Math.max(map.getMinY(), wOffset / TILE_SIZE);
		int xMax = Math.min(map.getMaxX(),
				(int) Math.ceil((double) (vOffset + getSize().width) / TILE_SIZE - 1));
		int yMax = Math.min(map.getMaxY(),
				(int) Math.ceil((double) (wOffset + getSize().height) / TILE_SIZE - 1));

		int x, y;
		Tile tile;
		for (y = yMin; y <= yMax; y++) {
			setDrawTileY(y);
			for (x = xMin; x <= xMax; x++) {
				setDrawTileX(x);
				tile = map.getTile(x, y);
				if (tile != null) {
					drawTile(tile);
				}
			}
		}
	}

	private void drawHUD() {
		// New turn message
		long dt = System.currentTimeMillis() - gui.getController().getTurnStartTime();
		if (getFadeColor(0, 0, 0, 0.5f, 1, dt)) {
			g.setFont(boldFont.deriveFont(50f));
			drawStringBC(gui.getController().getCurrentArmy().getName(), getWidth() / 2, 250);
			g.setFont(normalFont.deriveFont(30f));
			drawStringTC("Turn " + gui.getController().getTurn(), getWidth() / 2, 250);
		}

		// FPS
		g.setColor(Color.RED);
		g.setFont(boldFont);
		writeFrameRate();

		// Status message
		if (fadeStatus) {
			if (getFadeColor(1, 0, 0, 4.5f, 0.5f, System.currentTimeMillis() - statusTime)) {
				writeStatus();
			}
		} else {
			g.setColor(Color.RED);
			writeStatus();
		}
	}

	private void writeStatus() {
		g.setFont(boldFont);
		drawStringBC(getStatus(), getWidth() / 2, getHeight() - 50);
	}

	private void writeFrameRate() {
		calculateFrameRate();
		g.drawString(String.format("%.0f", framerate), 0, g.getFont().getSize());
	}

	private void drawTile(Tile tile) {
		Controller c = gui.getController();

		// Pre-calculated some needed stuff
		double distance = Double.MAX_VALUE;
		double moveRange = 0;
		double dashRange = 0;
		if (c.getSelectedUnit() != null) {
			distance = Util.walkDistance(
					c.getSelectedUnit().getPosition(),
					tile.getPosition());
			moveRange = c.getSelectedUnit().getMovesRemaining();
			if (c.getSelectedUnit().mayDash()) {
				dashRange = moveRange + c.getSelectedUnit().getSpeed();
			}
		}

		// Draw terrain background
		g.setColor(Color.GRAY);
		g.fillRect(v, w, TILE_SIZE, TILE_SIZE);

		// Draw movement overlay
		if (c.getSelectedUnit() != null ) {
			if (distance == 0) {
				if (c.getSelectedUnit().getMovesRemaining() > 0) {
					g.setColor(MOVE_COLOR);
				} else if (c.getSelectedUnit().mayDash()) {
					g.setColor(DASH_COLOR);
				}
			} else if (distance <= moveRange) {
				g.setColor(MOVE_COLOR);
			} else if (distance <= dashRange) {
				g.setColor(DASH_COLOR);
			}
		}
		g.fillRect(v, w, TILE_SIZE, TILE_SIZE);
		g.setColor(Color.BLACK);

		// Draw border
		drawImage(Resources.tileborder);

		// Draw top unit
		if (tile.getUnit() != null) {
			if (tile.getUnit().getArmy() == gui.getController().getArmies()[0]) {
				drawImage(Resources.unit);
			} else {
				drawImage(Resources.enemy);
			}
		}
	}

	protected void drawImage(Image img) {
		drawImage(img, v, w);
	}

	protected void setDrawTile(Point p) {
		setDrawTile(p.x, p.y);
	}

	protected void setDrawTile(int x, int y) {
		setDrawTileX(x);
		setDrawTileY(y);
	}

	protected void setDrawTileX(int x) {
		v = x * TILE_SIZE + offset.x;
	}

	protected void setDrawTileY(int y) {
		w = y * TILE_SIZE + offset.y;
	}

	public Point2D windowToWorld(Point2D windowCoordinates) {
		Point2D camPos = gui.getController().getCameraPosition();
		return new Point2D.Double(Math.round((windowCoordinates.getX() - 0.5 * getSize().getWidth()) / TILE_SIZE + camPos.getX()),
				Math.round((windowCoordinates.getY() - 0.5 * getSize().getHeight()) / TILE_SIZE + camPos.getY()));
	}

	public Point worldToWindow(Point2D worldCoordinates) {
		Point2D camPos = gui.getController().getCameraPosition();
		return new Point((int) Math.ceil((worldCoordinates.getX() - camPos.getX() - 0.5) * TILE_SIZE + 0.5 * getSize().width),
				(int) Math.ceil((worldCoordinates.getY() - camPos.getY() - 0.5) * TILE_SIZE + 0.5 * getSize().height));
	}

	protected void calculateFrameRate() {
		long next = System.nanoTime();
		framerates[n] = ((double) 1000000000) / (next - last);
		last = next;

		n = (n + 1) % framerates.length;

		if (n == 0) {
			double var = 0;
			for (int i = 0; i < framerates.length; i++) {
				var += framerates[i];
			}
			this.framerate = var / framerates.length;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status, boolean fade) {
		this.status = status;
		this.fadeStatus = fade;
		this.statusTime = System.currentTimeMillis();
	}

	private void correctCameraPosition() {
		Point2D camPos = gui.getController().getCameraPosition();
		double x = camPos.getX();
		double y = camPos.getY();
		Map map = gui.getController().getMap();

		if (getWidth() > map.getWidth() * TILE_SIZE) {
			x = (double) map.getWidth() / 2 - 0.5;
		} else if ((x + 0.5) * TILE_SIZE < getWidth() / 2) {
			x = ((double) getWidth() / 2 - 0.5) / TILE_SIZE - 0.5;
		} else if ((map.getWidth() - x - 0.5) * TILE_SIZE < getWidth() / 2) {
			x = map.getWidth() - ((double) getWidth() / 2 - 1) / TILE_SIZE - 0.5;
		}

		if (getHeight() > map.getHeight() * TILE_SIZE) {
			y = (double) map.getHeight() / 2 - 0.5;
		} else if ((y + 0.5) * TILE_SIZE < getHeight() / 2) {
			y = ((double) getHeight() / 2 - 0.5) / TILE_SIZE - 0.5;
		} else if ((map.getHeight() - y - 0.5) * TILE_SIZE < getHeight() / 2) {
			y = map.getHeight() - ((double) getHeight() / 2 - 1) / TILE_SIZE - 0.5;
		}

		camPos.setLocation(x, y);
	}
}
