package mt.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Resources {
	public static BufferedImage grass;
	public static BufferedImage unit;
	public static BufferedImage enemy;

	/* Creation of static members */
	public static void loadResources() {
		//TODO placeholder implementation: fix.
		try {
			grass = loadImage("assets/green.png");
			unit = loadImage("assets/soldiers/bowman.png");
			enemy = loadImage("assets/outlaws/trapper.png");
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("Unable to read image files");
			System.exit(1);
		}
	}
	
	public static BufferedImage loadImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}
	
	public static BufferedImage loadImage(String path, double scaleFactor) throws IOException {
		BufferedImage im = loadImage(path);
		if (scaleFactor != 1) {
			Image scaled = im.getScaledInstance(
					(int) (im.getWidth() * scaleFactor),
					(int) (im.getHeight() * scaleFactor), 
					Image.SCALE_REPLICATE);
			im = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = im.getGraphics();
			g.drawImage(scaled, 0, 0, null);
			g.dispose();
		}
		return im;
	}
}
