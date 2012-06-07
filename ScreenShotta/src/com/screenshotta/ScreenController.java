package com.screenshotta;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class ScreenController {
	private static final ScreenController Instance = new ScreenController();
	private ArrayList<Screen> screens = new ArrayList<Screen>();
	
	private ScreenController() {
		initialize();
	}
	private void initialize() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] gs = ge.getScreenDevices();
			System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width);
			
			for (GraphicsDevice gd : gs) {
				screens.add(new Screen(gd));
			}
		} catch (Exception e) {
			
		}
	}
	public static ScreenController getInstance() {
		return Instance;
	}
	public int getNumberOfScreens() {
		return screens.size();
	}
	public void update() {
		screens.clear();
		initialize();
		ScreenShotta.TrayMessage("Screen Layout Updated", getNumberOfScreens()+ " screen(s) detected.");
	}
	public Rectangle getScreenArea() {
		Rectangle rect = new Rectangle();
		/*int tmpx =0;
		int tmpy =0;
		int tmpbx =0;
		int tmpby =0;*/
		Iterator<Screen> itr = screens.iterator();
		while (itr.hasNext()) {
			Screen temp = itr.next();
			if ((temp.x+temp.width) >= rect.width) {
				rect.width = temp.x+temp.width;
			}
			if ((temp.y+temp.height) >= rect.height) {
				rect.height = temp.y+temp.height;
			}
			if (temp.x < rect.x) {
				rect.x = temp.x;
			}
			if (temp.y < rect.y) {
				rect.y = temp.y;
			}
		} 
		if (rect.x < 0) {
			rect.width = rect.width + (Math.abs(rect.x));
		}
		if (rect.y < 0) {
			rect.height = rect.height + (Math.abs(rect.y));
		}
		return rect;
	}
	private class Screen {
		private int height;
		private int width;
		private int x;
		private int y;
		Dimension d = new Dimension();
		
		public Screen(GraphicsDevice g) {
			height = g.getDefaultConfiguration().getBounds().height;
			width  = g.getDefaultConfiguration().getBounds().width;
			x = g.getDefaultConfiguration().getBounds().x;
			y = g.getDefaultConfiguration().getBounds().y;
		}
		public String toString() {
			String s = new String("x="+x+" y="+y+" width="+width+" height="+height);
			return s;
		}
		/*public int height(){
			return height;
		}
		public int width() {
			return width;
		}
		public int x() {
			return x;
		}
		public int y() {
			return y;
		}
		public Dimension getBounds() {
			d.setSize(width, height);
			return d;
		}*/
	}
}
