package it.vitalegi.minesweeper.bot.ai.neuroph;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.learning.BackPropagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import it.vitalegi.minesweeper.bot.ai.neuroph.action.NeuralNetworkFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NeuralNetworkTest {

	@Test
	public void testNetwork_2_4_4_1() {
		int count = 0;
		Map<String, Double> scores = new HashMap<>();
		for (int hiddenLayersCount = 2; hiddenLayersCount < 2; hiddenLayersCount++) {
			for (int[] hiddenLayers = new int[hiddenLayersCount]; //
					hiddenLayers.length == hiddenLayersCount; //
					hiddenLayers = nextConfiguration(hiddenLayers, 3)) {

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < hiddenLayers.length; i++) {
					sb.append(hiddenLayers[i]).append(" ");
				}
				double score = evaluateModel(100, 2, 1, hiddenLayers);
				scores.put(sb.toString(), score);
				count++;
				if (count % 100 == 0) {
					System.out.println("Current: " + count + " " + sb.toString());
				}
			}
		}
		scores.entrySet().stream().sorted(Comparator.comparing(Entry::getValue))
				.forEach(e -> System.out.println(e.getKey() + "\t" + e.getValue()));
	}

	private int[] nextConfiguration(int[] hiddenLayers, int max) {
		BigDecimal value = arrayToNumber(hiddenLayers, max);
		value = value.add(BigDecimal.ONE);
		int[] nextConfiguration = numberToArray(value, max);
		return fixZeros(paddingLeft(nextConfiguration, hiddenLayers.length));
	}

	private int[] paddingLeft(int[] arr, int size) {
		if (arr.length >= size) {
			return arr;
		}
		int[] newArr = new int[size];
		for (int i = 0; i < arr.length; i++) {
			newArr[size - arr.length + i] = arr[i];
		}
		return newArr;
	}

	private int[] fixZeros(int[] arr) {
		int[] newArr = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 0) {
				newArr[i] = 1;
			} else {
				newArr[i] = arr[i];
			}
		}
		return newArr;
	}

	private BigDecimal arrayToNumber(int[] values, int base) {
		BigDecimal value = BigDecimal.ZERO;
		for (int i = 0; i < values.length; i++) {
			value = value.multiply(BigDecimal.valueOf(base));
			value = value.add(BigDecimal.valueOf(values[i]));
		}
		return value;
	}

	private int[] numberToArray(BigDecimal value, int base) {
		List<Integer> values = new ArrayList<>();
		while (value.compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal v = value.remainder(BigDecimal.valueOf(base));
			values.add(0, v.intValue());
			value = value.divide(BigDecimal.valueOf(base), RoundingMode.DOWN);
		}
		return values.stream().mapToInt(i -> i).toArray();
	}

	private double evaluateModel(int attempts, int inputNeurons, int outputNeurons, int... hiddenNeurons) {

		DataSet ds = getDataset();

		int success = 0;
		for (int i = 0; i < attempts; i++) {
			NeuralNetwork<BackPropagation> ann = neuralNetworkFactory.createNeuralNetwork(inputNeurons, outputNeurons,
					hiddenNeurons);

			BackPropagation backPropagation = new BackPropagation();
			backPropagation.setMaxIterations(1000);
			ann.learn(ds, backPropagation);

			if (evaluate(ann)) {
				success++;
			}
		}
		return (double) success / attempts;
	}

	private boolean evaluate(NeuralNetwork<BackPropagation> ann) {
		try {
			Assert.assertEquals(1, calculate(ann, 1, 0));
			Assert.assertEquals(1, calculate(ann, 0, 1));
			Assert.assertEquals(0, calculate(ann, 1, 1));
			Assert.assertEquals(0, calculate(ann, 0, 0));
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}

	private DataSet getDataset() {
		DataSet ds = new DataSet(2, 1);

		ds.addRow(new DataSetRow(new double[] { 0, 1 }, new double[] { 1 }));
		ds.addRow(new DataSetRow(new double[] { 1, 1 }, new double[] { 0 }));
		ds.addRow(new DataSetRow(new double[] { 0, 0 }, new double[] { 0 }));
		ds.addRow(new DataSetRow(new double[] { 1, 0 }, new double[] { 1 }));

		return ds;
	}

	private int calculate(NeuralNetwork<BackPropagation> nn, int in1, int in2) {
		nn.setInput(in1, in2);
		nn.calculate();
		double[] networkOutput = nn.getOutput();
		return (int) Math.round(networkOutput[0]);
	}

	@Autowired
	NeuralNetworkFactory neuralNetworkFactory;
}
