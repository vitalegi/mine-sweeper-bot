package it.vitalegi.minesweeper.bot.image.recognition;

import java.awt.Rectangle;

public class GetImagePositionImpl {

	/**
	 * Ricerca image in target
	 *
	 * @param image
	 * @param target
	 * @return
	 */
	public Rectangle getImagePosition(int[][][] image, int[][][] target) {
		int width = image.length;
		int height = image[0].length;

		int count = 0;
		for (int x1 = 0; x1 < target.length - width; x1++) {
			for (int y1 = 0; y1 < target[0].length - height; y1++) {
				double distance = imageDistance.getDistance(image, 0, 0, target, x1, y1, width, height);
				count++;
				if ((count) % 100 == 0) {
					System.out.println("> " + (count) + " => " + ((target.length - width) * (target[0].length - height)));
				}
			}
		}
		return null;
	}

	public void setImageDistance(ImageDistance imageDistance) {
		this.imageDistance = imageDistance;
	}

	ImageDistance imageDistance;
}
