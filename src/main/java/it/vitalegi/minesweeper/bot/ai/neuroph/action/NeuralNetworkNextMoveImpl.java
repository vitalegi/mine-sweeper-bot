package it.vitalegi.minesweeper.bot.ai.neuroph.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.learning.BackPropagation;

import it.vitalegi.minesweeper.bot.action.Action;
import it.vitalegi.minesweeper.bot.action.NextMove;
import it.vitalegi.minesweeper.bot.context.Cell;
import it.vitalegi.minesweeper.bot.context.GameStatus;

public class NeuralNetworkNextMoveImpl implements NextMove {

	protected boolean flagClick;
	protected NeuralNetwork<BackPropagation> nn;

	public NeuralNetworkNextMoveImpl(boolean flagClick, NeuralNetwork<BackPropagation> nn) {
		super();
		this.flagClick = flagClick;
		this.nn = nn;
	}

	@Override
	public Action getNextAction(GameStatus gameStatus) {
		List<ActionNN> actions = new ArrayList<>();

		for (int x = NeuralNetworkUtil.NETWORK_AWARENESS; //
				x < gameStatus.getWidth() - NeuralNetworkUtil.NETWORK_AWARENESS; //
				x++) {

			for (int y = NeuralNetworkUtil.NETWORK_AWARENESS; //
					y < gameStatus.getHeight() - NeuralNetworkUtil.NETWORK_AWARENESS; //
					y++) {
				actions.add(getNextAction(gameStatus, x, y));
			}
		}
		return getBestAction(gameStatus, actions);
	}

	protected ActionNN getBestAction(GameStatus gameStatus, List<ActionNN> actions) {
		double bestScore = 0;
		ActionNN bestAction = null;
		for (ActionNN action : actions) {
			double score = getScore(getMaxIndex(action.getNetworkOutput()), action.getNetworkOutput());
			if (score > bestScore) {
				bestScore = score;
				bestAction = action;
			}
		}
		if (bestAction != null) {
			System.out.println("Action: " + bestAction);
			return bestAction;
		}
		ActionNN action = new ActionNN(Action.flagClick(new Random().nextInt(gameStatus.getWidth()),
				new Random().nextInt(gameStatus.getHeight())), null, null);
		System.out.println("Random action: " + action);
		return action;

	}

	protected ActionNN getNextAction(GameStatus gameStatus, int x, int y) {
		double[] inputs = getInputNeurons(gameStatus, x, y);
		nn.setInput(inputs);
		nn.calculate();
		double[] outputs = nn.getOutput();
		return new ActionNN(getAction(outputs), inputs, outputs);
	}

	public double[] getInputNeurons(GameStatus gameStatus, int x, int y) {

		double[] inputs = new double[NeuralNetworkUtil.SIDE_SIZE * NeuralNetworkUtil.SIDE_SIZE];

		for (int xAware = 0; (xAware < NeuralNetworkUtil.SIDE_SIZE); xAware++) {
			for (int yAware = 0; (yAware < NeuralNetworkUtil.SIDE_SIZE); yAware++) {

				double v = getValue(gameStatus.getValue(x + xAware - NeuralNetworkUtil.NETWORK_AWARENESS,
						y + yAware - NeuralNetworkUtil.NETWORK_AWARENESS));
				inputs[NeuralNetworkUtil.getIndex(xAware, yAware)] = v;
			}
		}
		return inputs;
	}

	protected double getValue(Cell cell) {
		if (cell.isNumericCell()) {
			return getValue(String.valueOf(cell.getNumericValue()));
		}
		if (cell.isFlagCell()) {
			return getValue("F");
		}
		if (cell.isClickableCell()) {
			return getValue("?");
		}
		return getValue("?");
	}

	public static double getValue(String slimCode) {
		if ("0".equals(slimCode)) {
			return 0;
		}
		if ("1".equals(slimCode)) {
			return 1;
		}
		if ("2".equals(slimCode)) {
			return 2;
		}
		if ("3".equals(slimCode)) {
			return 3;
		}
		if ("4".equals(slimCode)) {
			return 4;
		}
		if ("5".equals(slimCode)) {
			return 5;
		}
		if ("F".equals(slimCode)) {
			return -1;
		}
		if ("?".equals(slimCode)) {
			return -2;
		}
		return -2;
	}

	protected Action getAction(double[] networkOutput) {
		int maxIndex = getMaxIndex(networkOutput);

		int x = NeuralNetworkUtil.getXFromIndex(maxIndex);
		int y = NeuralNetworkUtil.getYFromIndex(maxIndex);
		if (flagClick) {
			return Action.flagClick(x, y);
		} else {
			return Action.bombClick(x, y);
		}
	}

	protected int getMaxIndex(double[] networkOutput) {
		int maxIndex = new Random().nextInt(networkOutput.length);
		for (int i = 0; i < networkOutput.length; i++) {
			if (networkOutput[i] > networkOutput[maxIndex]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	protected double sum(double[] arr) {
		double sum = 0;
		for (double n : arr) {
			sum += n;
		}
		return sum;
	}

	protected double getScore(int index, double[] networkOutput) {
		int max = getMaxIndex(networkOutput);
		double sum = sum(networkOutput);
		if (sum < 0.1) {
			return -100000d;
		}
		return max / sum;
	}
}