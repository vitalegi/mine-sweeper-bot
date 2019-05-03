package it.vitalegi.minesweeper.bot.image.recognition;

import java.util.List;
import java.util.Map;

public interface ImageRecognition {

	public String categorizeImage(Map<String, List<int[][][]>> dataset, int[][][] image);

	public String categorizeImage(Map<String, List<int[][][]>> dataset, int[][][] image, int x, int y, int width,
			int height);

	public Map<String, Double> getScoreMatrix(Map<String, List<int[][][]>> dataset, int[][][] image, int x, int y,
			int width, int height);

}