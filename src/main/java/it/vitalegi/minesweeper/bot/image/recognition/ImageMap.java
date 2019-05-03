package it.vitalegi.minesweeper.bot.image.recognition;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageMap {

	public List<int[][][]> map(List<BufferedImage> image);

	public int[][][] map(BufferedImage image);

}