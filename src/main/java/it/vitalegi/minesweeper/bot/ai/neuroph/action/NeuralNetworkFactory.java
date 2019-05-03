package it.vitalegi.minesweeper.bot.ai.neuroph.action;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.ConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class NeuralNetworkFactory {

	public NeuralNetwork<BackPropagation> createMineSweeperNN(int inputNeurons, int... hiddenNeurons) {
		return createNeuralNetwork(inputNeurons, inputNeurons, hiddenNeurons);
	}

	public NeuralNetwork<BackPropagation> createNeuralNetwork(int inputNeurons, int outputNeurons,
			int... hiddenNeurons) {
		Layer inputLayer = createLayer(inputNeurons);

		Layer outputLayer = createLayer(outputNeurons);

		NeuralNetwork<BackPropagation> ann = new NeuralNetwork<>();
		ann.addLayer(0, inputLayer);

		for (int hiddenLayer = 0; hiddenLayer < hiddenNeurons.length; hiddenLayer++) {
			int layer = hiddenLayer + 1;
			ann.addLayer(layer, createLayer(hiddenNeurons[hiddenLayer]));
			ConnectionFactory.fullConnect(ann.getLayerAt(layer - 1), ann.getLayerAt(layer));
		}
		ann.addLayer(hiddenNeurons.length + 1, outputLayer);
		ConnectionFactory.fullConnect(ann.getLayerAt(hiddenNeurons.length), ann.getLayerAt(hiddenNeurons.length + 1));

		ConnectionFactory.fullConnect(ann.getLayerAt(0), ann.getLayerAt(ann.getLayersCount() - 1), false);

		ann.setInputNeurons(ann.getLayerAt(0).getNeurons());
		ann.setOutputNeurons(outputLayer.getNeurons());

		return ann;
	}

	private Layer createLayer(int neurons) {

		Layer layer = new Layer();
		for (int i = 0; i < neurons; i++) {
			layer.addNeuron(new Neuron());
		}
		return layer;
	}

}
