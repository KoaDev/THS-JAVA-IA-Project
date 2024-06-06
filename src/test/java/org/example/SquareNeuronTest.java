package org.example;

import org.example.utils.NeuronUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SquareNeuronTest {

    private final SquareNeuron squareNeuron = new SquareNeuron();

    @Test
    public void testRecognizeGeneratedSquareSignal() {
        // Générer un signal carré que le neurone est censé reconnaître
        float[] signal = NeuronUtil.genererSignalCarre(44100);
        squareNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit proche de 1 pour les signaux connus
        assertTrue(squareNeuron.neurone.sortie() > 0.9, "Le neurone n'a pas reconnu le signal carré généré.");
    }

    @Test
    public void testRecognizeNoisySignal() {
        // Générer un signal carré et ajouter du bruit
        float[] signal = NeuronUtil.genererSignalCarre(44100);
        float[] noisySignal = NeuronUtil.addNoise(new float[][]{signal}, NeuronUtil.noiseLevel)[0];
        squareNeuron.evaluateStream(noisySignal);

        // On s'attend à ce que la sortie soit encore raisonnablement haute pour les signaux bruités
        assertTrue(squareNeuron.neurone.sortie() > 0.5, "Le neurone n'a pas bien reconnu le signal bruité.");
    }

    @Test
    public void testNonRecognizedSignal() {
        // Générer un signal sinusoïdal que le neurone n'est pas censé reconnaître
        float[] signal = NeuronUtil.genererSignalSinus(44100);
        squareNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit basse pour les signaux inconnus
        assertTrue(squareNeuron.neurone.sortie() < 0.5, "Le neurone a mal classé le signal sinusoïdal comme carré.");
    }
}
