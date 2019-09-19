# FFNN
Feed Forward Neural Network in JAVA

This Neural Network has supervised training.
Training data are in "res/training.txt".
Tolopogy is located in "res/topology.txt".
Weights are located in "res/weights.txt".

If the weights count calculated from the topology does not match with weights count found in the file "weights.txt",
the neural network starts the training, saves the new weights and then switches to run mode.

If the weights count calculated from the topology matches with weights count found in the file "weights.txt",
the neural network skips the training, loads weights, and then goes to run mode.

So if you change the topology, or delete the weights, the neural net retrains itself.
