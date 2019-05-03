package it.vitalegi.minesweeper.bot.io;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface IOProxy {

	int[][][] screenshot(Rectangle area);

	public void saveImage(BufferedImage image, String filename);

	public void pressKey(int key);

	public void sleep(long sleepTime);

	public void leftClick(int x, int y);

	public void rightClick(int x, int y);
}