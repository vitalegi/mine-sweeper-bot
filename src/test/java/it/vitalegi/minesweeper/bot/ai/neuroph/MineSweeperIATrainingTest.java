package it.vitalegi.minesweeper.bot.ai.neuroph;

import java.util.ArrayList;
import java.util.List;

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

import it.vitalegi.minesweeper.bot.action.Action;
import it.vitalegi.minesweeper.bot.action.NextMove;
import it.vitalegi.minesweeper.bot.ai.neuroph.action.ActionNN;
import it.vitalegi.minesweeper.bot.ai.neuroph.action.NeuralNetworkFactory;
import it.vitalegi.minesweeper.bot.ai.neuroph.action.NeuralNetworkNextMoveImpl;
import it.vitalegi.minesweeper.bot.ai.neuroph.training.GameStatusWithKnowledge;
import it.vitalegi.minesweeper.bot.context.Cell;
import it.vitalegi.minesweeper.bot.context.GameStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MineSweeperIATrainingTest {

	@Test
	public void testRandomGame() {
		NeuralNetwork<BackPropagation> nn = factory.createMineSweeperNN(9, 5, 7, 8, 8, 7, 5);

		NextMove nextMove = new NeuralNetworkNextMoveImpl(true, nn);

		List<ActionNN> actions = new ArrayList<>();

		for (int attempt = 0; attempt < 20; attempt++) {
			GameStatusWithKnowledge game = GameStatusWithKnowledge.newInstance(9, 9, 10);
			for (int i = 0; i < 20 * 20; i++) {
				ActionNN action = (ActionNN) nextMove.getNextAction(game.getGameStatus());

				if (!game.isBomb(action.getX(), action.getY()) && validClick(game, action)) {
					actions.add(action);
				}
				game.setCellVisited(action.getX(), action.getY(), true);
			}
		}
		System.out.println("Dataset:");
		actions.forEach(a -> System.out.println(a));

		System.out.println("Learning");

		BackPropagation backPropagation = new BackPropagation();
		backPropagation.setMaxIterations(1000);
		nn.learn(getDataset(actions), backPropagation);

		System.out.println("Done learning");

		int ko = 0;
		int ok = 0;
		int timeout = 0;
		for (int attempt = 0; attempt < 10; attempt++) {
			try {
				System.out.println("Attempt " + attempt);
				boolean result = applyNN(nextMove, 9, 9, 10);
				if (result) {
					ok++;
				} else {
					ko++;
				}
			} catch (RuntimeException e) {
				timeout++;
			}
		}
		System.out.println("OK: " + ok);
		System.out.println("ko: " + ko);
		System.out.println("Timeout: " + timeout);
	}

	@Test
	public void testSupervisedGame() {
		NeuralNetwork<BackPropagation> nn = factory.createMineSweeperNN(9, 5, 7, 8, 8, 7, 5);

		NextMove nextMove = new NeuralNetworkNextMoveImpl(true, nn);

		System.out.println("Dataset:");
		DataSet dataset = new DataSet(9, 9);

		addDatasetRow(dataset, "0000100?0", "000000010");
		addDatasetRow(dataset, "0?00200?0", "010000010");
		addDatasetRow(dataset, "?????????", "000010000");
		addDatasetRow(dataset, "?????????", "000010000");
		addDatasetRow(dataset, "?????????", "000010000");
		addDatasetRow(dataset, "?????????", "000010000");
		addDatasetRow(dataset, "?????????", "000010000");
		addDatasetRow(dataset, "?????????", "000010000");

		System.out.println("Learning");

		BackPropagation backPropagation = new BackPropagation();
		backPropagation.setMaxIterations(1000);
		nn.learn(dataset, backPropagation);

		System.out.println("Done learning");

		int ko = 0;
		int ok = 0;
		int timeout = 0;
		for (int attempt = 0; attempt < 10; attempt++) {
			try {
				System.out.println("Attempt " + attempt);
				boolean result = applyNN(nextMove, 9, 9, 10);
				if (result) {
					ok++;
				} else {
					ko++;
				}
			} catch (RuntimeException e) {
				timeout++;
			}
		}
		System.out.println("OK: " + ok);
		System.out.println("ko: " + ko);
		System.out.println("Timeout: " + timeout);
	}

	protected void addDatasetRow(DataSet dataset, String input, String output) {
		dataset.addRow(getArray(input), getArray(output));
	}

	protected double[] getArray(String codes) {
		double[] arr = new double[codes.length()];
		for (int i = 0; i < codes.length(); i++) {
			arr[i] = NeuralNetworkNextMoveImpl.getValue("" + codes.charAt(i));
		}

		return arr;
	}

	protected double getValue(Cell cell) {
		if (cell.isNumericCell()) {
			return cell.getNumericValue();
		}
		if (cell.isFlagCell()) {
			return -5;
		}
		if (cell.isClickableCell()) {
			return -1;
		}
		return -5;
	}

	protected boolean applyNN(NextMove nextMove, int width, int height, int bombs) {
		int step = 0;
		GameStatusWithKnowledge game = GameStatusWithKnowledge.newInstance(width, height, bombs);
		for (int i = 0; i < width * height; i++) {
			step++;
			GameStatus gameStatus = game.getGameStatus();
			ActionNN action = (ActionNN) nextMove.getNextAction(gameStatus);
			game.setCellVisited(action.getX(), action.getY(), true);

			gameStatus.printStatus();

			if (game.getGameStatus().isGameOver()) {
				System.out.println("Step: " + step);
				return false;
			}
			if (game.getGameStatus().isWin()) {
				System.out.println("Step: " + step);
				return true;
			}
		}
		throw new RuntimeException();
	}

	protected DataSet getDataset(List<ActionNN> actions) {
		DataSet dataset = new DataSet(9, 9);
		for (ActionNN action : actions) {
			dataset.addRow(new DataSetRow(action.getNetworkInput(), action.getNetworkOutput()));
		}
		return dataset;
	}

	protected boolean validClick(GameStatusWithKnowledge game, Action action) {
		return !game.isVisited(action.getX(), action.getY());
	}

	@Autowired
	NeuralNetworkFactory factory;
}
