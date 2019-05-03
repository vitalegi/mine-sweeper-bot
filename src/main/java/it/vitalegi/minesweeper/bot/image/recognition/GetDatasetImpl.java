package it.vitalegi.minesweeper.bot.image.recognition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.minesweeper.bot.GameConstants;
import it.vitalegi.minesweeper.util.ImageUtil;

@Service
public class GetDatasetImpl implements GetDataset {

	@Override
	public Map<String, List<int[][][]>> getGridDataset() {
		Map<String, List<int[][][]>> dataset = new HashMap<>();
		dataset.put(GameConstants.IMAGE_VALUE_0, imageMap.map(ImageUtil.load("/cell_value_0.png")));
		dataset.put(GameConstants.IMAGE_VALUE_1, imageMap.map(ImageUtil.load("/cell_value_1.png")));
		dataset.put(GameConstants.IMAGE_VALUE_2, imageMap.map(ImageUtil.load("/cell_value_2.png")));
		dataset.put(GameConstants.IMAGE_VALUE_3, imageMap.map(ImageUtil.load("/cell_value_3.png")));
		dataset.put(GameConstants.IMAGE_VALUE_4, imageMap.map(ImageUtil.load("/cell_value_4.png")));
		dataset.put(GameConstants.IMAGE_BOMB, imageMap.map(ImageUtil.load("/cell_value_bomb.png")));
		dataset.put(GameConstants.IMAGE_EMPTY, imageMap.map(ImageUtil.load("/cell_value_empty.png")));
		dataset.put(GameConstants.IMAGE_EXPLODED_BOMB, imageMap.map(ImageUtil.load("/cell_value_exploded_bomb.png")));
		dataset.put(GameConstants.IMAGE_FLAG, imageMap.map(ImageUtil.load("/cell_value_flag.png")));
		dataset.put(GameConstants.IMAGE_WRONG_BOMB, imageMap.map(ImageUtil.load("/cell_value_wrong_bomb.png")));

		return dataset;
	}

	@Override
	public Map<String, List<int[][][]>> getGamePositionDataset() {
		Map<String, List<int[][][]>> dataset = new HashMap<>();
		dataset.put(GameConstants.GamePosition.BOTTOM_LEFT,
				imageMap.map(ImageUtil.load("/game_coordinates/margin_bottom_left.png")));
		dataset.put(GameConstants.GamePosition.BOTTOM_RIGHT,
				imageMap.map(ImageUtil.load("/game_coordinates/margin_bottom_right.png")));
		dataset.put(GameConstants.GamePosition.TOP_LEFT,
				imageMap.map(ImageUtil.load("/game_coordinates/margin_top_left.png")));
		dataset.put(GameConstants.GamePosition.TOP_RIGHT,
				imageMap.map(ImageUtil.load("/game_coordinates/margin_top_right.png")));

		return dataset;
	}

	@Autowired
	ImageMap imageMap;
}
