package it.vitalegi.minesweeper.bot.image.recognition;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.vitalegi.minesweeper.util.ImageUtil;

@Service
public class ImageMapImpl implements ImageMap {

	public List<int[][][]> map(List<BufferedImage> image) {
		return image.stream().map(this::map).collect(Collectors.toList());
	}

	@Override
	public int[][][] map(BufferedImage image) {
		int[][][] map = new int[image.getWidth()][image.getHeight()][3];
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				map[x][y] = ImageUtil.getRGB(image, x, y);
			}
		}
		return map;
	}

}
