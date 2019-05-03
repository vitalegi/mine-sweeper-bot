package it.vitalegi.minesweeper.bot.action;

import it.vitalegi.minesweeper.bot.context.GameStatus;

public interface NextMove {

	public Action getNextAction(GameStatus gameStatus);
}