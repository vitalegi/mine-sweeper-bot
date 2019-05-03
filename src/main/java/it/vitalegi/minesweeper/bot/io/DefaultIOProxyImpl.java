package it.vitalegi.minesweeper.bot.io;

import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.minesweeper.bot.image.recognition.ImageMap;

@Service
public class DefaultIOProxyImpl implements IOProxy {

	@Autowired
	ImageMap imageMap;

	@Override
	public int[][][] screenshot(Rectangle area) {
		return imageMap.map(getScreenshotImage(area))	;
	}

	public void leftClick(int x, int y) {
		log.info("Move to " + x + " " + y + " and left click");
		mouseClick(x, y, InputEvent.BUTTON1_DOWN_MASK);
	}

	public void rightClick(int x, int y) {
		log.info("Move to " + x + " " + y + " and right click");
		mouseClick(x, y, InputEvent.BUTTON3_DOWN_MASK);
	}

	private void mouseClick(int x, int y, int key) {
		try {
			mouseMove(0, 0);
			mouseMove(x, y);
			Robot robot = new Robot();
			robot.mousePress(key);
			sleep(250);
			robot.mouseRelease(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void mouseMove(int x, int y) {
		try {
			final int ITERATIONS = 200;
			for (int count = 0; (MouseInfo.getPointerInfo().getLocation().getX() != x
					|| MouseInfo.getPointerInfo().getLocation().getY() != y) && count < ITERATIONS; count++) {
				new Robot().mouseMove(x, y);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void saveImage(BufferedImage image, String filename) {
		try {
			ImageIO.write(image, "png", new File(filename));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void pressKey(int key) {
		try {
			Robot r = new Robot();
			r.keyPress(key);
			r.keyRelease(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void sleep(long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	protected BufferedImage getScreenshotImage(Rectangle area) {
		try {
			return new Robot().createScreenCapture(area);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static Logger log = LoggerFactory.getLogger(DefaultIOProxyImpl.class);
}
