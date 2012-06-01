package com.screenshotta;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.RenderedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class Capture {
	/*
	 * @returns Location of file.
	 */
	public static String takeScreenshot() {
		try {
			String filename=null;
			Date date = new Date();
			SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat(
					"MMddyyyy-k-m-s");
			StringBuilder now = new StringBuilder(
					dateformatMMDDYYYY.format(date));

			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Robot robot = new Robot();
			Thread.sleep(10);
			ScreenController ss = ScreenController.getInstance();
			Rectangle rect = ss.getScreenArea();
			Image image = robot.createScreenCapture(rect);
			JFileChooser fr = new JFileChooser();
			FileSystemView fw = fr.getFileSystemView();
			fw.getDefaultDirectory();
			String ds = System.getProperty("file.separator");
			String path = fw.getDefaultDirectory() + ds + "My Pictures" +ds;
			filename = JOptionPane.showInputDialog(null, "File name?");
			if (filename == null) { 
				return null;
			}
			filename = filename+".png";
			//filename = "screenshot" + now + ".png";
			File file = new File(path + filename);
			ImageIO.write((RenderedImage) image, "png", file);
			return path + filename;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
