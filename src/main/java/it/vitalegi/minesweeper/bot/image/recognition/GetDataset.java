package it.vitalegi.minesweeper.bot.image.recognition;

import java.util.List;
import java.util.Map;

public interface GetDataset {

	Map<String, List<int[][][]>> getGridDataset();

	Map<String, List<int[][][]>> getGamePositionDataset();

}