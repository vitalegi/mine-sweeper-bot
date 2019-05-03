package it.vitalegi.minesweeper.bot.action;

import it.vitalegi.minesweeper.bot.context.Cell;

public class Action {

	int x;
	int y;
	boolean leftClick;

	public static Action bombClick(int x, int y) {
		return new Action(x, y, false);
	}

	public static Action flagClick(int x, int y) {
		return new Action(x, y, true);
	}

	public static Action bombClick(Cell cell) {
		return new Action(cell.getX(), cell.getY(), false);
	}

	public static Action flagClick(Cell cell) {
		return new Action(cell.getX(), cell.getY(), true);
	}

	protected Action(int x, int y, boolean leftClick) {
		super();
		this.x = x;
		this.y = y;
		this.leftClick = leftClick;
	}

	@Override
	public String toString() {
		return "Action [x=" + x + ", y=" + y + ", leftClick=" + leftClick + "]";
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isLeftClick() {
		return leftClick;
	}

	public void setLeftClick(boolean leftClick) {
		this.leftClick = leftClick;
	}
}
