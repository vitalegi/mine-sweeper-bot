package it.vitalegi.minesweeper.bot.ai.neuroph.action;

public class NeuralNetworkUtil {

	public static final int NETWORK_AWARENESS = 1;
	public static final int SIDE_SIZE = NeuralNetworkUtil.NETWORK_AWARENESS * 2 + 1;

	public static int getIndex(int x, int y) {
		return x + (y * SIDE_SIZE);
	}

	public static int getXFromIndex(int index) {
		return index / SIDE_SIZE;
	}

	public static int getYFromIndex(int index) {
		return index % SIDE_SIZE;
	}
}
