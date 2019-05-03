package it.vitalegi.minesweeper.bot;

import java.awt.Rectangle;

import it.vitalegi.minesweeper.bot.context.GameStatus;

public interface GetGameStatusService {

	GameStatus getMatrix(int[][][] image, int cellX, int cellY);

	int getXCoordinate(Rectangle rectangle, int x, int cellX);

	int getYCoordinate(Rectangle rectangle, int y, int cellY);

}