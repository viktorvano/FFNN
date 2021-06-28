package FFNN;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static FFNN.Variables.*;
import static FFNN.Weights.*;
import static FFNN.GeneralFunctions.*;

public class FFNN {
    public static void main(String[] args)
    {
        TrainingData trainData = new TrainingData();
        loadTopology();
        if (topology.size() < 3)
        {
            System.out.println("Topology ERROR:\nTopology is too short, may miss some layer.");
            return;
        }

        NeuralNetwork myNet = new NeuralNetwork(topology);

        input = new ArrayList<>();
        target = new ArrayList<>();
        result = new ArrayList<>();
        input.clear();
        target.clear();
        result.clear();

        if(weights.size() != get_number_of_weights_from_file())
        {
            load_training_data_from_file();

            System.out.println("Training started\n");
            while (true)
            {
                trainingPass++;
                System.out.println("Pass: " + trainingPass);

                //Get new input data and feed it forward:
                trainData.getNextInputs(input);
                showVectorValues("Inputs:", input);
                myNet.feedForward(input);

                // Train the net what the outputs should have been:
                trainData.getTargetOutputs(target);
                showVectorValues("Targets: ", target);
                assert(target.size() == topology.get(topology.size()-1));
                myNet.backProp(target);//This function alters neurons

                // Collect the net's actual results:
                myNet.getResults(result);
                showVectorValues("Outputs: ", result);


                // Report how well the training is working, averaged over recent samples:
                System.out.println("Net recent average error: " + myNet.getRecentAverageError() + "\n\n");

                if (myNet.getRecentAverageError() < 0.001)
                {
                    System.out.println("Exit due to low error :D\n\n");
                    myNet.saveNeuronWeights();
                    break;
                }
            }
            System.out.println("Training done.\n");
        }else
        {
            myNet.loadNeuronWeights();
            System.out.println("Weights were loaded from file.\n");
        }


        System.out.println("Run mode begin\n");
        trainingPass = 0;
        while (true)
        {
            trainingPass++;
            System.out.println("Run: " + trainingPass);

            //Get new input data and feed it forward:
            //Make sure that your input data are the same size as InputNodes
            input.clear();
            for(int i = 0; i < inputNodes; i++)
            {
                input.add((float)(Math.round(Math.random())));
            }
            showVectorValues("Inputs:", input);
            myNet.feedForward(input);

            // Collect the net's actual results:
            myNet.getResults(result);
            showVectorValues("Outputs: ", result);

            try
            {
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e)
            {
                System.out.println("Timeout went wrong at training pass: " + trainingPass);
            }
        }

    }
}
