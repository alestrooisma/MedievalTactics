/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.awt.Point;
import static java.lang.Math.*;

/**
 *
 * @author ale
 */
public class Util {

	public static int distanceSquared(Point a, Point b) {
		return (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
	}

	public static double distance(Point a, Point b) {
		return sqrt(distanceSquared(a, b));
	}

	public static double walkDistance(Point a, Point b) {
		int dx = abs(b.x - a.x);
		int dy = abs(b.y - a.y);
		int min, max;
		if (dx > dy) {
			min = dy;
			max = dx;
		} else {
			min = dx;
			max = dy;
		}
		return min * 1.5 + max - min;
	}

	public static void writeToCSV(String filename, double[][] values) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename);
		} catch (FileNotFoundException ex) {
			System.err.println("Could not write to " + filename);
			return;
		}
		StringBuilder sb = new StringBuilder(1024);
		for (int y = 0; y < values[0].length; y++) {
			for (int x = 0; x < values.length; x++) {
				sb.append(values[x][y]);
				sb.append(',');
			}
			writer.println(sb.toString());
		}
		writer.close();
	}
}
