package it.vitalegi.minesweeper.bot.image.recognition;

public interface ColorDistance {

	public double getDistance(int[] color1, int[] color2);

	public double getMaxDistance();
}