package com.screenshotta;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ScreenShotta {
	public static TrayIcon trayicon = new TrayIcon(getIcon());
	public static SystemTray systray = SystemTray.getSystemTray();
	
	public static void main(String[] args) {
		ScreenShotta ss = new ScreenShotta();
		ss.initTray();
		ScreenController sc = ScreenController.getInstance();
		int i = sc.getNumberOfScreens();
		trayicon.displayMessage("Welcome to ScreenShotta!", "Click the icon to take a screenshot!\n "+i+" Screen(s) Detected", TrayIcon.MessageType.NONE);
	}
	private void initTray() {
		try {
			systray.add(trayicon);
			PopupMenu popup = new PopupMenu();
			MenuItem exit = new MenuItem("Exit");
			MenuItem takess = new MenuItem("Take Screenshot");
			MenuItem update = new MenuItem("Update Screens");
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ap) {	
					int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
					if (confirm == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}});
			takess.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String filepath = Capture.takeScreenshot();
					trayicon.displayMessage("Screenshot Saved!", filepath, TrayIcon.MessageType.NONE);				
				}});
			update.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ScreenController ss = ScreenController.getInstance();
					ss.update();
				}});
			popup.add(update);
			popup.add(takess);
			popup.add(exit);
			trayicon.setPopupMenu(popup);
			trayicon.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
				}
				@Override
				public void mouseReleased(MouseEvent m) {
					if (m.getButton() == 1) {
						String filepath = Capture.takeScreenshot();
						trayicon.displayMessage("Screenshot Saved!", filepath, TrayIcon.MessageType.NONE);
					}
					if (m.getButton() == 3) {
						//System.exit(0);
					}
					
				}
				
			});
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	public static void TrayMessage (String title, String text) {
		trayicon.displayMessage(title, text, TrayIcon.MessageType.INFO);
	}
	private static Image getIcon() {
		Image returnable = null;
		try {
			returnable = ImageIO.read(ScreenShotta.class.getResourceAsStream("/res/icon.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return returnable;
	}
}
