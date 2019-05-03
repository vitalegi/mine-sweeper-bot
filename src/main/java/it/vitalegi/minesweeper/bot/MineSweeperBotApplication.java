package it.vitalegi.minesweeper.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MineSweeperBotApplication {

	static {
		System.setProperty("java.awt.headless", "false");
	}

	public static void main(String[] args) {
		SpringApplication.run(MineSweeperBotApplication.class, args);
	}

}
