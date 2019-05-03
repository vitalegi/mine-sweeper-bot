package it.vitalegi.minesweeper.bot.context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameStatus {

	protected Cell[][] status;

	public GameStatus(String[][] status) {
		this.status = new Cell[status.length][status[0].length];
		for (int x = 0; x < status.length; x++) {
			for (int y = 0; y < status[0].length; y++) {
				this.status[x][y] = Cell.newInstance(status[x][y], x, y);
			}
		}
	}

	public GameStatus(Cell[][] status) {
		this.status = status;
	}

	public static int getWidth(Cell[][] status) {
		return status.length;
	}

	public int getWidth() {
		return getWidth(status);
	}

	public static int getHeight(Cell[][] status) {
		return status[0].length;
	}

	public int getHeight() {
		return getHeight(status);
	}

	public int getClickableCellsCount() {
		return (int) toStream().filter(Cell::isClickableCell).count();
	}

	public List<Cell> getClickableCells() {
		return toStream().filter(Cell::isClickableCell).collect(Collectors.toList());
	}

	public Cell getValue(int x, int y) {
		return getValue(status, x, y);
	}

	public static Cell getValue(Cell[][] status, int x, int y) {
		if (x < 0) {
			return Cell.emptyCell(x, y);
		}
		if (y < 0) {
			return Cell.emptyCell(x, y);
		}
		if (x >= getWidth(status)) {
			return Cell.emptyCell(x, y);
		}
		if (y >= getHeight(status)) {
			return Cell.emptyCell(x, y);
		}
		return status[x][y];
	}

	public boolean isEnded() {
		return isGameOver() || isWin();
	}

	public boolean isGameOver() {
		return toStream().anyMatch(Cell::isGameOver);
	}

	public boolean hasOtherMoves() {
		return toStream().anyMatch(Cell::isClickableCell);
	}

	public boolean isWin() {
		if (hasOtherMoves()) {
			return false;
		}
		if (isGameOver()) {
			return false;
		}
		return true;
	}

	public int getFlags() {
		return (int) toStream().filter(Cell::isFlagCell).count();
	}

	public Stream<Cell> toStream() {
		List<Cell> points = new ArrayList<>();
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				points.add(status[x][y]);
			}
		}
		return points.stream();
	}

	public GameStatus getNeighbors(int x, int y, int distance) {

		int size = 2 * distance + 1;
		Cell[][] neighbors = new Cell[size][size];

		for (int offsetX = -distance; offsetX <= distance; offsetX++) {
			for (int offsetY = -distance; offsetY <= distance; offsetY++) {
				neighbors[distance + offsetX][distance + offsetY] = getValue(x + offsetX, y + offsetY);
			}
		}
		return new GameStatus(neighbors);
	}

	public void printStatus() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				String v;
				Cell cell = getValue(x, y);
				if (cell.isNumericCell()) {
					v = "" + cell.getNumericValue();
				} else if (cell.isClickableCell()) {
					v = "?";
				} else {
					v = " ";
				}
				sb.append(v);
			}
			sb.append("\n");
		}
		log.info("Status: \n" + sb.toString());
	}

	Logger log = LoggerFactory.getLogger(GameStatus.class);
}
