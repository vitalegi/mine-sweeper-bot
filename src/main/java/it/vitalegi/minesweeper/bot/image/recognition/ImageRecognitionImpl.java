package it.vitalegi.minesweeper.bot.image.recognition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageRecognitionImpl implements ImageRecognition {

	@Override
	public String categorizeImage(Map<String, List<int[][][]>> dataset, int[][][] image) {
		String best = null;
		double score = Double.MAX_VALUE;
		Map<String, Double> scoreMatrix = getScoreMatrix(dataset, image, 0, 0, image.length, image[0].length);
		for (Entry<String, Double> scoreEntry : scoreMatrix.entrySet()) {
			if (compare(scoreEntry.getValue(), score) < 0) {
				best = scoreEntry.getKey();
				score = scoreEntry.getValue();
			}
		}
		return best;
	}

	public String categorizeImage(Map<String, List<int[][][]>> dataset, int[][][] image, int x, int y, int width,
			int height) {
		String best = null;
		double score = Double.MAX_VALUE;
		Map<String, Double> scoreMatrix = getScoreMatrix(dataset, image, x, y, width, height);
		for (Entry<String, Double> scoreEntry : scoreMatrix.entrySet()) {
			if (compare(scoreEntry.getValue(), score) < 0) {
				best = scoreEntry.getKey();
				score = scoreEntry.getValue();
			}
		}
		return best;
	}

	@Override
	public Map<String, Double> getScoreMatrix(Map<String, List<int[][][]>> dataset, int[][][] image, int x, int y,
			int width, int height) {

		Map<String, Double> scoreMatrix = new HashMap<>();

		for (Entry<String, List<int[][][]>> entry : dataset.entrySet()) {
			double entryBestScore = Double.MAX_VALUE;
			for (int[][][] sample : entry.getValue()) {
				double entryScore = imageDistance.getDistance(sample, 0, 0, image, x, y, width, height);
				if (compare(entryScore, entryBestScore) < 0) {
					entryBestScore = entryScore;
				}
			}
			scoreMatrix.put(entry.getKey(), entryBestScore);
		}
		return scoreMatrix;
	}

	protected int compare(double score1, double score2) {
		if (score1 > score2) {
			return 1;
		}
		if (score1 < score2) {
			return -1;
		}
		return 0;
	}

	@Autowired
	ImageDistance imageDistance;

	@Autowired
	ImageMap imageMap;

	Logger log = LoggerFactory.getLogger(ImageRecognitionImpl.class);
}
