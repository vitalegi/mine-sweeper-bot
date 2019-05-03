package it.vitalegi.minesweeper.bot.action;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.vitalegi.minesweeper.bot.context.Cell;
import it.vitalegi.minesweeper.bot.context.GameStatus;

public class GetNextMoveImpl implements NextMove {

	public Action getNextAction(GameStatus gameStatus) {
		Action singleCompareAction = gameStatus.toStream()//
				.map(cell -> getNextActionSingleCompare(gameStatus, cell.getX(), cell.getY()))//
				.filter(Objects::nonNull).findFirst().orElse(null);

		if (singleCompareAction != null) {
			return singleCompareAction;
		}
		return getRandomCell(gameStatus);
	}

	protected Action getNextActionSingleCompare(GameStatus gameStatus, int x, int y) {
		GameStatus neighbors = gameStatus.getNeighbors(x, y, 1);
		if (neighbors.getClickableCellsCount() == 0) {
			log.info("Non ci sono celle cliccabili intorno a " + x + "-" + y);
			return null;
		}

		Cell cell = gameStatus.getValue(x, y);
		if (cell.isNumericCell()) {
			Cell firstClicable = neighbors.getClickableCells().get(0);
			int clickableCellsCount = neighbors.getClickableCellsCount();
			int flags = neighbors.getFlags();
			int remainingBombs = cell.getNumericValue() - flags;
			if (remainingBombs == 0) {
				log.info("Intorno a " + x + "-" + y + " non ci sono altre bombe, clicca");
				return Action.flagClick(firstClicable);
			}
			if (remainingBombs > 0 && remainingBombs == clickableCellsCount) {
				log.info("Intorno a " + x + "-" + y + " le posizioni libere sono tutte bombe, clicca");
				return Action.bombClick(firstClicable);
			}
		}

		return null;
	}

	protected Action getRandomCell(GameStatus gameStatus) {
		List<Cell> clickables = gameStatus.getClickableCells();
		if (clickables.isEmpty()) {
			return null;
		}
		int randomIndex = (int) (Math.random() * clickables.size());
		Cell cell = clickables.get(randomIndex);
		log.info("Non ho idea di cosa fare, click random in " + cell.getX() + "-" + cell.getY());
		return Action.flagClick(cell);
	}

	Logger log = LoggerFactory.getLogger(GetNextMoveImpl.class);
}
