package it.vitalegi.minesweeper.bot.image.recognition;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import it.vitalegi.minesweeper.bot.image.recognition.ImageMap;
import it.vitalegi.minesweeper.bot.image.recognition.ImageRecognition;
import it.vitalegi.minesweeper.util.ImageUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ImageRecognitionTest {

	private Map<String, List<int[][][]>> dataset;

	protected static final String IMAGE_VALUE_1 = "value1";
	protected static final String IMAGE_VALUE_2 = "value2";
	protected static final String IMAGE_VALUE_3 = "value3";
	protected static final String IMAGE_BOMB = "bomb";
	protected static final String IMAGE_EMPTY = "empty";
	protected static final String IMAGE_EXPLODED_BOMB = "exploded-bomb";
	protected static final String IMAGE_FLAG = "flag";

	@Before
	public void setup() {

		dataset = new HashMap<>();
		dataset.put(IMAGE_VALUE_1, imageMap.map(ImageUtil.load("/cell_value_1.png")));
		dataset.put(IMAGE_VALUE_2, imageMap.map(ImageUtil.load("/cell_value_2.png")));
		dataset.put(IMAGE_VALUE_3, imageMap.map(ImageUtil.load("/cell_value_3.png")));
		dataset.put(IMAGE_BOMB, imageMap.map(ImageUtil.load("/cell_value_bomb.png")));
		dataset.put(IMAGE_EMPTY, imageMap.map(ImageUtil.load("/cell_value_empty.png")));
		dataset.put(IMAGE_EXPLODED_BOMB, imageMap.map(ImageUtil.load("/cell_value_exploded_bomb.png")));
		dataset.put(IMAGE_FLAG, imageMap.map(ImageUtil.load("/cell_value_flag.png")));
	}

	@Test
	public void testRecognizeSelf() {
		assertEquals(IMAGE_VALUE_1, imageRecognition.categorizeImage(dataset, dataset.get(IMAGE_VALUE_1).get(0)));
		assertEquals(IMAGE_VALUE_2, imageRecognition.categorizeImage(dataset, dataset.get(IMAGE_VALUE_2).get(0)));
		assertEquals(IMAGE_VALUE_3, imageRecognition.categorizeImage(dataset, dataset.get(IMAGE_VALUE_3).get(0)));
		assertEquals(IMAGE_BOMB, imageRecognition.categorizeImage(dataset, dataset.get(IMAGE_BOMB).get(0)));
		assertEquals(IMAGE_EMPTY, imageRecognition.categorizeImage(dataset, dataset.get(IMAGE_EMPTY).get(0)));
		assertEquals(IMAGE_EXPLODED_BOMB,
				imageRecognition.categorizeImage(dataset, dataset.get(IMAGE_EXPLODED_BOMB).get(0)));
		assertEquals(IMAGE_FLAG, imageRecognition.categorizeImage(dataset, dataset.get(IMAGE_FLAG).get(0)));
	}

	@Autowired
	ImageRecognition imageRecognition;

	@Autowired
	ImageMap imageMap;
}
