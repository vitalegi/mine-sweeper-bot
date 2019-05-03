package it.vitalegi.minesweeper.bot.context;

import it.vitalegi.minesweeper.bot.GameConstants;

public class SimpleCell extends Cell {

	int value;
	boolean isNumeric;
	boolean isClickable;
	boolean isGameover;
	boolean isFlag;
	boolean isBomb;

	protected SimpleCell(String label, int x, int y) {
		super(x, y);
		processLabel(label);
	}

	protected void processLabel(String label) {
		value = 0;
		isNumeric = false;
		isClickable = false;
		isGameover = false;
		isFlag = false;
		isBomb = false;

		if (GameConstants.IMAGE_VALUE_0.equals(label)) {
			value = 0;
			isNumeric = true;
		}
		if (GameConstants.IMAGE_VALUE_1.equals(label)) {
			value = 1;
			isNumeric = true;
		}
		if (GameConstants.IMAGE_VALUE_2.equals(label)) {
			value = 2;
			isNumeric = true;
		}
		if (GameConstants.IMAGE_VALUE_3.equals(label)) {
			value = 3;
			isNumeric = true;
		}
		if (GameConstants.IMAGE_VALUE_4.equals(label)) {
			value = 4;
			isNumeric = true;
		}
		if (GameConstants.IMAGE_BOMB.equals(label)) {
			isBomb = true;
		}
		if (GameConstants.IMAGE_EMPTY.equals(label)) {
			isClickable = true;
		}
		if (GameConstants.IMAGE_EXPLODED_BOMB.equals(label)) {
			isGameover = true;
			isBomb = true;
		}
		if (GameConstants.IMAGE_WRONG_BOMB.equals(label)) {

		}
		if (GameConstants.IMAGE_FLAG.equals(label)) {
			value = -1;
			isNumeric = true;
			isFlag = true;
		}
	}

	public boolean isClickableCell() {
		return isClickable;
	}

	public boolean isNumericCell() {
		return isNumeric;
	}

	public int getNumericValue() {
		return value;
	}

	public boolean isFlagCell() {
		return isFlag;
	}

	public boolean isBomb() {
		return isBomb;
	}

	public boolean isDone() {
		return !isClickable;
	}

	public boolean isGameOver() {
		return isGameover;
	}
}
