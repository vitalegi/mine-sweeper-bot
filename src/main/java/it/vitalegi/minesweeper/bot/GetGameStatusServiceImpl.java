package it.vitalegi.minesweeper.bot;

import java.awt.Rectangle;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.minesweeper.bot.context.GameStatus;
import it.vitalegi.minesweeper.bot.image.recognition.GetDataset;
import it.vitalegi.minesweeper.bot.image.recognition.ImageRecognition;

@Service
public class GetGameStatusServiceImpl implements GetGameStatusService {

	@Autowired
	ImageRecognition imageRecognition;

	@Autowired
	GetDataset getDataset;

	@Override
	public GameStatus getMatrix(int[][][] image, int cellX, int cellY) {

		double cellWidth = 1.0 * image.length / cellX;
		double cellHeight = 1.0 * image[0].length / cellY;

		String[][] values = new String[cellX][cellY];
		Map<String, List<int[][][]>> dataset = getDataset.getGridDataset();

		for (int x = 0; x < cellX; x++) {
			for (int y = 0; y < cellY; y++) {
				String category = imageRecognition.categorizeImage(dataset, image, (int) (x * cellWidth),
						(int) (y * cellHeight), (int) (cellWidth), (int) (cellHeight));
				values[x][y] = category;
			}
		}
		return new GameStatus(values);
	}

	@Override
	public int getXCoordinate(Rectangle rectangle, int x, int cellX) {
		double ref = rectangle.getX();
		double cellWidth = 1.0 * rectangle.getWidth() / cellX;
		double xFromRef = x * cellWidth;
		double cellCenter = cellWidth / 2;
		log.info("X: " + ref + " + " + xFromRef + " (" + cellWidth + " * " + x + ") + " + cellCenter);
		return (int) (ref + xFromRef + cellCenter);
	}

	@Override
	public int getYCoordinate(Rectangle rectangle, int y, int cellY) {
		double ref = rectangle.getY();
		double cellHeight = 1.0 * rectangle.getHeight() / cellY;
		double yFromRef = y * cellHeight;
		double cellCenter = cellHeight / 2;
		log.info("Y: " + ref + " + " + yFromRef + " (" + cellHeight + " * " + y + ") + " + cellCenter);
		return (int) (ref + yFromRef + cellCenter);
	}

	Logger log = LoggerFactory.getLogger(GetGameStatusServiceImpl.class);
}
