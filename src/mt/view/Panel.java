package mt.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import javax.swing.JPanel;

/**
 * A panel on which the map is drawn.
 *
 * @author ale
 */
public abstract class Panel extends JPanel {

	public static final Point ZERO = new Point();
	protected GUI gui;
	protected Graphics g;
	protected Font normalFont;
	protected Font boldFont;

	public Panel(GUI gui) {
		super(new BorderLayout());
		this.gui = gui;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.g = g;

		normalFont = g.getFont();
		boldFont = normalFont.deriveFont(Font.BOLD);

		render();
	}

	protected abstract void render();

	protected void drawImage(Image img, int x, int y) {
		g.drawImage(img, x, y, null);
	}

	protected void drawStringTL(String str, int x, int y) {
		drawStringBL(str, x, y + g.getFontMetrics().getHeight());
	}

	protected void drawStringTC(String str, int x, int y) {
		drawStringTL(str, x - g.getFontMetrics().stringWidth(str) / 2, y);
	}

	protected void drawStringTR(String str, int x, int y) {
		drawStringTL(str, x - g.getFontMetrics().stringWidth(str), y);
	}

	protected void drawStringML(String str, int x, int y) {
		drawStringBL(str, x, y + g.getFontMetrics().getHeight() / 2);
	}

	protected void drawStringMC(String str, int x, int y) {
		drawStringML(str, x - g.getFontMetrics().stringWidth(str) / 2, y);
	}

	protected void drawStringMR(String str, int x, int y) {
		drawStringML(str, x - g.getFontMetrics().stringWidth(str), y);
	}

	protected void drawStringBL(String str, int x, int y) {
		g.drawString(str, x, y);
	}

	protected void drawStringBC(String str, int x, int y) {
		drawStringBL(str, x - g.getFontMetrics().stringWidth(str) / 2, y);
	}

	protected void drawStringBR(String str, int x, int y) {
		drawStringBL(str, x - g.getFontMetrics().stringWidth(str), y);
	}

	public boolean getFadeColor(float r, float g, float b, float delay, float fade, float dt) {
		if (dt < (delay + fade) * 1000) {
			if (dt < delay * 1000) {
				this.g.setColor(new Color(r, g, b, 1));
			} else {
				float alpha = (delay + fade) / fade - (dt / (fade * 1000));
				this.g.setColor(new Color(r, g, b, alpha));
			}
			return true;
		} else {
			return false;
		}
	}
}
