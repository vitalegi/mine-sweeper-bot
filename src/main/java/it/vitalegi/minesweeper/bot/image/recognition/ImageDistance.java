package it.vitalegi.minesweeper.bot.image.recognition;

public interface ImageDistance {

	public double getDistance(int[][][] image1, int[][][] image2);

	public double getDistance(int[][][] image1, int x1, int y1, int[][][] image2, int x2, int y2, int width,
			int height);
}