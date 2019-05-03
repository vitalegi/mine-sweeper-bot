package it.vitalegi.minesweeper.bot.context;

public abstract class Cell {

	int x;
	int y;

	public static Cell emptyCell(int x, int y) {
		return new EmptyCell(x, y);
	}

	public static Cell newInstance(String label, int x, int y) {
		return new SimpleCell(label, x, y);
	}

	protected Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract boolean isClickableCell();

	public abstract boolean isNumericCell();

	public abstract int getNumericValue();

	public abstract boolean isFlagCell();

	public abstract boolean isDone();

	public abstract boolean isGameOver();

	public boolean isBomb() {
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
