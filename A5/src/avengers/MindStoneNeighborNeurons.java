package avengers;
/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of the 
 * vertices that connect to the Mind Stone. 
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 *    1. v (int): number of neurons (vertices in the graph)
 *    2. v lines, each with a String referring to a neuron's name (vertex name)
 *    3. e (int): number of synapses (edges in the graph)
 *    4. e lines, each line refers to an edge, each line has 2 (two) Strings: from to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as args[1]
 * Identify the vertices that connect to the Mind Stone vertex. 
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are 
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone neuron (vertex)
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 *         Kevin Joseph
 *         kbj24@rutgers.edu
 *         kbj24
 */


public class MindStoneNeighborNeurons {
    
    public static void main (String [] args) {
        
    	if ( args.length < 2 ) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }
        
    	StdIn.setFile(args[0]);
        int neuronSize = StdIn.readInt();
        String[] neurons = new String[neuronSize];
        for(int i = 0; i < neurons.length; i++){
            String neuron = StdIn.readString();
            neurons[i] = neuron;
        }

        int edges = StdIn.readInt();
        String [][] synapses = new String[edges + 1][neurons.length];

        for(int i = 0; i < synapses[0].length; i++){
            synapses[0][i] = neurons[i];
        }

        for(int i = 0; i < edges; i++){
            String syn1 = StdIn.readString();
            String syn2 = StdIn.readString();
            for(int k = 0; k < synapses[0].length; k++){
                if(synapses[0][k].equals(syn2)){
                    for(int l = 1; l < synapses.length; l++){
                        if(synapses[l][k] == null){
                            synapses[l][k] = syn1;
                            break;
                        }
                    }
                    break;
                }
            }
            for(int j = 0; j < neurons.length; j++){
                if(neurons[j] != null && neurons[j].equals(syn1)){
                    neurons[j] = null;
                    break;
                }
            }
        }
        
        int key = -1;
        for(int i = 0; i < neurons.length; i++){
            if(neurons[i] != null){
                key = i;
            }
        }

        StdOut.setFile(args[1]);
        for(int j = 1; j < synapses.length; j++){
            if(synapses[j][key] != null){
                StdOut.println(synapses[j][key]);
            }
            else{
                break;
            }
        }

    }
}
