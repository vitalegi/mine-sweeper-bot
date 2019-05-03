package it.vitalegi.minesweeper.bot.context;

public class EmptyCell extends Cell {

	protected EmptyCell(int x, int y) {
		super(x, y);
	}

	public boolean isEmptyCell() {
		return true;
	}

	@Override
	public boolean isClickableCell() {
		return false;
	}

	@Override
	public boolean isNumericCell() {
		return false;
	}

	@Override
	public int getNumericValue() {
		return 0;
	}

	@Override
	public boolean isFlagCell() {
		return false;
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public boolean isGameOver() {
		return false;
	}

}
