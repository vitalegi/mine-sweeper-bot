package it.vitalegi.minesweeper.util;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static int[] getRGB(BufferedImage image, int x, int y) {
		int clr = image.getRGB(x, y);
		int red = (clr & 0x00ff0000) >> 16;
		int green = (clr & 0x0000ff00) >> 8;
		int blue = clr & 0x000000ff;
		return new int[] { red, green, blue };
	}

	public static List<BufferedImage> load(String... resourceNames) {
		List<BufferedImage> images = new ArrayList<>();
		for (String resourceName : resourceNames) {
			images.add(loadResource(resourceName));
		}
		return images;
	}

	public static BufferedImage loadResource(String resourceName) {
		try (InputStream is = ImageUtil.class.getResourceAsStream(resourceName)) {
			return ImageIO.read(is);
		} catch (Exception e) {
			throw new RuntimeException("Errore caricando risorsa " + resourceName, e);
		}
	}
}
