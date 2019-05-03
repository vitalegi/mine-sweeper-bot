package it.vitalegi.minesweeper.bot;

import java.awt.Rectangle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.vitalegi.minesweeper.bot.action.Action;
import it.vitalegi.minesweeper.bot.action.GetNextMoveImpl;
import it.vitalegi.minesweeper.bot.action.NextMove;
import it.vitalegi.minesweeper.bot.ai.neuroph.action.NeuralNetworkNextMoveImpl;
import it.vitalegi.minesweeper.bot.context.GameStatus;
import it.vitalegi.minesweeper.bot.image.recognition.ImageMap;
import it.vitalegi.minesweeper.bot.io.IOProxy;

@Component
@Profile("!test")
public class MineSweeperBot implements ApplicationRunner {

	public static final Rectangle AREA_SCREENSHOT = new Rectangle(43, 353, 244, 244);

	@Override
	public void run(ApplicationArguments args) throws Exception {

		int cellX = 9;
		int cellY = 9;

		NextMove move = new GetNextMoveImpl();

		for (int i = 0; i < cellX * cellY; i++) {
			int[][][] image = ioProxy.screenshot(AREA_SCREENSHOT);

			GameStatus gameStatus = getGameStatus.getMatrix(image, cellX, cellY);
			log.info("Gameover? " + gameStatus.isGameOver());
			log.info("Has other moves? " + gameStatus.hasOtherMoves());
			log.info("Is win? " + gameStatus.isWin());
			if (gameStatus.isGameOver() || gameStatus.isWin()) {
				return;
			}

			Action action = move.getNextAction(gameStatus);

			if (action != null) {
				log.info("Next action: " + action.getX() + " " + action.getY() + " " + action.isLeftClick());
				int targetPixelX = getGameStatus.getXCoordinate(AREA_SCREENSHOT, action.getX(), cellX);
				int targetPixelY = getGameStatus.getYCoordinate(AREA_SCREENSHOT, action.getY(), cellY);
				if (action.isLeftClick()) {
					ioProxy.leftClick(targetPixelX, targetPixelY);
				} else {
					ioProxy.rightClick(targetPixelX, targetPixelY);
				}
			} else {
				log.info("No next action");
			}
			gameStatus.printStatus();
		}
	}

	@Autowired
	GetGameStatusService getGameStatus;

	@Autowired
	IOProxy ioProxy;

	@Autowired
	ImageMap imageMap;

	Logger log = LoggerFactory.getLogger(MineSweeperBot.class);
}
