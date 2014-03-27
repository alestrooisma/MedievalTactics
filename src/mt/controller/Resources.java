package mt.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Resources {
	public static BufferedImage tileborder;

	/* Creation of static members */
	public static void loadResources() {
		//TODO placeholder implementation: fix.
		try {
			tileborder = ImageIO.read(new File("assets/tileborder.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("Unable to read image files");
			System.exit(1);
		}
	}
}
