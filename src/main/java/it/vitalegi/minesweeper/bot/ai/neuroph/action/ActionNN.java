package it.vitalegi.minesweeper.bot.ai.neuroph.action;

import java.util.Arrays;

import it.vitalegi.minesweeper.bot.action.Action;

public class ActionNN extends Action {

	double[] networkInput;
	double[] networkOutput;

	public ActionNN(Action a, double[] networkInput, double[] networkOutput) {
		super(a.getX(), a.getY(), a.isLeftClick());
		this.networkInput = networkInput;
		this.networkOutput = networkOutput;
	}

	public double[] getNetworkInput() {
		return networkInput;
	}

	public double[] getNetworkOutput() {
		return networkOutput;
	}

	@Override
	public String toString() {
		return "ActionNN [networkInput=" + Arrays.toString(networkInput) + " networkOutput="
				+ Arrays.toString(networkOutput) + " - " + super.toString() + "]";
	}
}
