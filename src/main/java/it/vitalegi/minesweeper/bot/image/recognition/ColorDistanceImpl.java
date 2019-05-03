package it.vitalegi.minesweeper.bot.image.recognition;

import org.springframework.stereotype.Service;

@Service
public class ColorDistanceImpl implements ColorDistance {

	public double getDistance(int[] color1, int[] color2) {
		int diffs = 0;
		for (int i = 0; i < color1.length; i++) {
			diffs += (color1[i] - color2[i]) * (color1[i] - color2[i]);
		}
		return Math.sqrt(diffs);
	}

	public double getMaxDistance() {

		return getDistance(new int[] { 0, 0, 0 }, new int[] { 255, 255, 255 });
	}
}
