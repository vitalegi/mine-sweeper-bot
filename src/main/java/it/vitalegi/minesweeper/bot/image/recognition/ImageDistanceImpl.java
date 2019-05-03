package it.vitalegi.minesweeper.bot.image.recognition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageDistanceImpl implements ImageDistance {

	public double getDistance(int[][][] image1, int x1, int y1, int[][][] image2, int x2, int y2, int width,
			int height) {

		int count = 0;
		double sum = 0;
		for (int offsetX = 0; //
				offsetX < width && (x1 + offsetX < image1.length) && (x2 + offsetX < image2.length); //
				offsetX++) {

			for (int offsetY = 0; //
					offsetY < height && (y1 + offsetY < image1[0].length) && (y2 + offsetY < image2[0].length); //
					offsetY++) {

				int[] color1 = image1[x1 + offsetX][y1 + offsetY];
				int[] color2 = image2[x2 + offsetX][y2 + offsetY];
				count++;
				sum += colorDistance.getDistance(color1, color2) / colorDistance.getMaxDistance();
			}
		}
		return sum / count;
	}

	@Override
	public double getDistance(int[][][] image1, int[][][] image2) {
		int count = 0;
		double sum = 0;
		for (int x = 0; x < image1.length; x++) {
			for (int y = 0; y < image1[0].length; y++) {

				int x2 = (int) ((1.0 * x) / image1.length * image2.length);
				int y2 = (int) ((1.0 * y) / image1[0].length * image2[0].length);
				int[] color1 = image1[x][y];
				int[] color2 = image2[x2][y2];
				count++;
				sum += colorDistance.getDistance(color1, color2) / colorDistance.getMaxDistance();
			}
		}
		return sum / count;
	}

	@Autowired
	ColorDistance colorDistance;

	Logger log = LoggerFactory.getLogger(ImageDistanceImpl.class);

}
