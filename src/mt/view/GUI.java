package mt.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.Timer;
import mt.controller.Controller;
import mt.controller.input.MainInputHandler;

/**
 * The GUI for the Pokemon game on ADE.
 * @author Ale Strooisma
 */
public class GUI extends AbstractGUI implements ActionListener {

	private JFrame frame;
	private GameField gf;
	private Controller controller;
	
	public static final int maxFrameRate = 60;
	private Timer redrawTimer;

	public GUI() {
		super();
		redrawTimer = new Timer(1000 / maxFrameRate, this);
	}

	@Override
	protected void createAndShowGUI() {
		// Create frame
		frame = new JFrame("CivGame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setPreferredSize(new Dimension(1280, 960));

		// Create game field
		gf = new GameField(this);
		gf.setBackground(Color.DARK_GRAY);

		// Add everything
		frame.add(gf, BorderLayout.CENTER);
		
		// Add main input handler
		MainInputHandler handler = new MainInputHandler(controller);
		frame.addKeyListener(handler);
		gf.addMouseListener(handler);

		// Start drawing
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void show() {
		frame.setVisible(true);
		redrawTimer.start();
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public Point worldToWindow(Point worldCoordinates) {
		return gf.worldToWindow(worldCoordinates);
	}
	
	public Point2D windowToWorld(Point windowCoordinates) {
		return gf.windowToWorld(windowCoordinates);
	}
	
	public Point windowToTile(Point windowCoordinates) {
		Point2D worldCoordinates = windowToWorld(windowCoordinates);
		return new Point((int) Math.round(worldCoordinates.getX()), 
				(int) Math.round(worldCoordinates.getY()));
	}
	
	public void attachMouseListener(MouseListener listener) {
		gf.addMouseListener(listener);
	}
	
	public void attachKeyboardListener(KeyListener listener) {
		frame.addKeyListener(listener);
	}

	public void setStatus(String status, boolean fade) {
		gf.setStatus(status, fade);
	}
	
	@Override
	public void update() {
		//frame.repaint();
		gf.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
	}
}
