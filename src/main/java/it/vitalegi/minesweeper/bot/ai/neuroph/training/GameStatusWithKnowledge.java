package it.vitalegi.minesweeper.bot.ai.neuroph.training;

import java.util.Random;

import it.vitalegi.minesweeper.bot.GameConstants;
import it.vitalegi.minesweeper.bot.context.GameStatus;

public class GameStatusWithKnowledge {

	protected static final int BOMB = -1;
	protected static final int EMPTY = 0;
	protected static final int VALUE1 = 1;
	protected static final int VALUE2 = 2;
	protected static final int VALUE3 = 3;
	protected static final int VALUE4 = 4;
	protected static final int FLAG = 100;

	VisitableCell[][] cells;

	protected GameStatusWithKnowledge(int width, int height) {
		cells = new VisitableCell[width][height];

		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				initCell(x, y);
			}
		}
	}

	public static GameStatusWithKnowledge newInstance(int width, int height, int bombs) {
		return RandomGameInitializer.randomGame(width, height, bombs);
	}

	public GameStatus getGameStatus() {
		String[][] status = new String[getWidth()][getHeight()];
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				status[x][y] = getLabel(x, y);
			}
		}
		return new GameStatus(status);
	}

	public boolean isBomb(int x, int y) {
		return cells[x][y].type == BOMB;
	}

	public void setBomb(int x, int y) {
		setCellValue(x, y, BOMB);
	}

	public boolean isValue(int x, int y) {
		return !isBomb(x, y);
	}

	public boolean isFlag(int x, int y) {
		return cells[x][y].type == FLAG;
	}

	public void setFlag(int x, int y) {
		cells[x][y].type = FLAG;
	}

	public void setValue(int x, int y, int value) {
		setCellValue(x, y, value);
	}

	public boolean isVisited(int x, int y) {
		return cells[x][y].visited;
	}

	protected String getLabel(int x, int y) {
		if (!isVisited(x, y)) {
			return GameConstants.IMAGE_EMPTY;
		}
		switch (cells[x][y].type) {
		case BOMB:
			return GameConstants.IMAGE_EXPLODED_BOMB;
		case EMPTY:
			return GameConstants.IMAGE_VALUE_0;
		case VALUE1:
			return GameConstants.IMAGE_VALUE_1;
		case VALUE2:
			return GameConstants.IMAGE_VALUE_2;
		case VALUE3:
			return GameConstants.IMAGE_VALUE_3;
		case VALUE4:
			return GameConstants.IMAGE_VALUE_4;
		case FLAG:
			return GameConstants.IMAGE_FLAG;
		default:
			return GameConstants.IMAGE_EMPTY;
		}
	}

	public int getWidth() {
		return cells.length;
	}

	public int getHeight() {
		return cells[0].length;
	}

	protected void setCellValue(int x, int y, int type) {
		initCell(x, y);
		cells[x][y].type = type;
	}

	public void setCellVisited(int x, int y, boolean visited) {
		initCell(x, y);
		cells[x][y].visited = visited;
	}

	private void initCell(int x, int y) {
		if (cells[x][y] == null) {
			cells[x][y] = new VisitableCell();
			cells[x][y].type = EMPTY;
			cells[x][y].visited = false;
		}
	}

	private static class VisitableCell {
		int type;
		boolean visited;
	}

	private static class RandomGameInitializer {

		public static GameStatusWithKnowledge randomGame(int width, int height, int bombs) {
			GameStatusWithKnowledge game = new GameStatusWithKnowledge(width, height);

			// set bombs

			for (int i = 0; i < bombs; i++) {
				placeBomb(game);
			}
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					calculateValue(game, x, y);
				}
			}
			return game;
		}

		protected static void placeBomb(GameStatusWithKnowledge game) {
			while (true) {
				int x = new Random().nextInt(game.getWidth());
				int y = new Random().nextInt(game.getHeight());

				if (!game.isBomb(x, y)) {
					game.setBomb(x, y);
					return;
				}
			}
		}

		protected static void calculateValue(GameStatusWithKnowledge game, int x, int y) {
			if (game.isBomb(x, y)) {
				return;
			}
			int bombCount = 0;
			for (int i = x - 1; i < x + 1; i++) {
				for (int j = y - 1; j < y + 1; j++) {
					if (game.isBomb(x, y)) {
						bombCount++;
					}
				}
			}
			game.setValue(x, y, bombCount);
		}
	}
}
