package FFNN;

import java.util.ArrayList;
import static FFNN.Variables.*;
import static FFNN.FileManagement.*;
import static FFNN.GeneralFunctions.*;

public class Neuron {
    public Neuron(int numOutputs, int myIndex)
    {
        m_outputWeights = new ArrayList<Connection>();
        m_outputWeights.clear();

        for (int c = 0; c < numOutputs; c++)
        {
            m_outputWeights.add(new Connection());
            m_outputWeights.get(m_outputWeights.size()-1).weight = randomWeight();
        }

        m_myIndex = myIndex;
    }

    public void setOutputValue(float value) { m_outputValue = value; }
    public float getOutputValue() { return m_outputValue; }
    public void feedForward(Layer prevLayer)
    {
        float sum = 0.0f;

        // Sum the previous layer's outputs (which are inputs)
        // Include the bias node from the previous layer.

        for (int n = 0; n < prevLayer.size(); n++)
        {
            sum += prevLayer.get(n).getOutputValue() * prevLayer.get(n).m_outputWeights.get(m_myIndex).weight;
        }

        m_outputValue = Neuron.transferFunction(sum);
    }

    public void calcOutputGradients(float targetValue)
    {
        float delta = targetValue - m_outputValue;
        m_gradient = delta * transferFunctionDerivative(m_outputValue);
    }

    public void calcHiddenGradients(Layer nextLayer)
    {
        float dow = sumDOW(nextLayer);
        m_gradient = dow * transferFunctionDerivative(m_outputValue);
    }

    public void updateInputWeights(Layer prevLayer)
    {
        // The weights to updated are in the Connection container
        // in the neurons in the preceding layer
        for (int n = 0; n < prevLayer.size(); n++)
        {
            Neuron neuron = prevLayer.get(n);
            float oldDeltaWeight = neuron.m_outputWeights.get(m_myIndex).deltaWeight;

            float newDeltaWeight =
                    // Individual input, magnified by the gradient and train rate:
                    eta // 0.0==slowlearner; 0.2==medium learner; 1.0==reckless learner
                            * neuron.getOutputValue()
                            * m_gradient
                            // Also add momentum = a fraction of the previous delta weight
                            + alpha // 0.0==no momentum; 0.5==moderate momentum
                            * oldDeltaWeight;
            neuron.m_outputWeights.get(m_myIndex).deltaWeight = newDeltaWeight;
            neuron.m_outputWeights.get(m_myIndex).weight += newDeltaWeight;
        }
    }

    public void saveInputWeights(Layer prevLayer)
    {
        // The weights to updated are in the Connection container
        // in the neurons in the preceding layer

        for (int n = 0; n < prevLayer.size(); n++)
        {
            Neuron neuron = prevLayer.get(n);
            weights.set(neuronIndex, neuron.m_outputWeights.get(m_myIndex).weight);
            neuronIndex++;
        }

        if (neuronIndex == weights.size())
        {
            //save weights from Weights[] to a file
            String strWeights = new String();

            for (int index = 0; index < weights.size(); index++)
            {
                strWeights += (formatFloatToString12(weights.get(index)) + "\n");
            }
            writeToFile("res\\weights.txt", strWeights);
        }
    }

    public void loadInputWeights(Layer prevLayer)
    {
        // The weights to updated are in the Connection container
        // in the neurons in the preceding layer

        //load weights from a file to Weights[]
        ArrayList<String> fileContent = new ArrayList<>(readOrCreateFile("res\\weights.txt"));

        if(fileContent.size()==0 || fileContent==null)
        {
            System.out.println("Cannot open weights.txt");
            System.exit(-10);
        }

        for (int index = 0; index < weights.size(); index++)
        {
            if(fileContent.get(index).length()!=0)
            {
                weights.set(index, Float.parseFloat(fileContent.get(index)));
            }
        }

        for (int n = 0; n < prevLayer.size(); n++)
        {
            Neuron neuron = prevLayer.get(n);
            neuron.m_outputWeights.get(m_myIndex).weight = weights.get(neuronIndex);
            neuronIndex++;
        }
    }

    private static float eta = velocity; // [0.0..1.0] overall network training rate
    private static float alpha = momentum; // [0.0..n] multiplier of last weight change (momentum)

    private float sumDOW(Layer nextLayer)
    {
        float sum = 0.0f;

        // Sum our contributions of the errors at the nodes we feed
        for (int n = 0; n < nextLayer.size() - 1; n++)
        {
            sum += m_outputWeights.get(n).weight * nextLayer.get(n).m_gradient;
        }

        return sum;
    }

    private static float transferFunction(float x)
    {
        // tanh - output range [-1.0..1.0]
        return (float)Math.tanh(x);
    }

    private float transferFunctionDerivative(float x)
    {
        // tanh derivative
        return (float) (1.0f - (float)Math.pow(Math.tanh(x), 2.0));// approximation return 1.0 - x*x;
    }

    private float randomWeight()
    {
        return (float)Math.random() - 0.5f;
    }

    private float m_outputValue;
    private ArrayList<Connection> m_outputWeights;
    private int m_myIndex;
    private float m_gradient;
}
