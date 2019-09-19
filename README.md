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

# Topology:
input layer: 4,
hidden layer: 9,
hidden layer: 8,
hidden layer: 7,
output layer: 2,

# Training data:
	Inputs						Outputs	
	{ 0.0, 0.0, 0.0, 0.0 },	{ 0.0f, 1.0f },
	{ 0.0, 0.0, 0.0, 1.0 },	{ 0.0f, 0.0f },
	{ 0.0, 0.0, 1.0, 0.0 },	{ 0.0f, 0.0f },
	{ 0.0, 0.0, 1.0, 1.0 },	{ 0.0f, 0.0f },
	{ 0.0, 1.0, 0.0, 0.0 },	{ 0.0f, 0.0f },
	{ 0.0, 1.0, 0.0, 1.0 },	{ 0.0f, 0.0f },
	{ 0.0, 1.0, 1.0, 0.0 },	{ 1.0f, 0.0f },
	{ 0.0, 1.0, 1.0, 1.0 },	{ 1.0f, 0.0f },
	{ 1.0, 0.0, 0.0, 0.0 },	{ 0.0f, 0.0f },
	{ 1.0, 0.0, 0.0, 1.0 },	{ 0.0f, 0.0f },
	{ 1.0, 0.0, 1.0, 0.0 },	{ 0.0f, 0.0f },
	{ 1.0, 0.0, 1.0, 1.0 },	{ 0.0f, 0.0f },
	{ 1.0, 1.0, 0.0, 0.0 },	{ 0.0f, 0.0f },
	{ 1.0, 1.0, 0.0, 1.0 },	{ 0.0f, 0.0f },
	{ 1.0, 1.0, 1.0, 0.0 },	{ 1.0f, 0.0f },
	{ 1.0, 1.0, 1.0, 1.0 },	{ 1.0f, 1.0f },

# Weights:
0.89037772
-0.47266329
0.1889922
0.82230507
-1.01858903
-0.04974523
0.619329
0.00046078
...
