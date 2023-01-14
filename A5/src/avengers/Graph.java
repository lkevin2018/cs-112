package avengers;

/******************************************************************************
 *  Compilation:  javac Graph.java
 *  Execution:    java Graph 
 * 
 *  Dependencies: None
 *
 *  A basic Graph<Integer> class implementation for the avengers package.
 *  Note: There is no Driver class associated with this class.
 * 
 *  @authors: Kevin Joseph
 *            kbj24@rutgers.edu
 *            kbj24
 ******************************************************************************/

import java.util.*;

public class Graph{
    private Map<Integer, List<Integer>> graph = new HashMap<>();
    private Map<Integer, Double> functionality = new HashMap<>();
    private Map<Integer, Integer> eu = new HashMap<>();

    private int[][] energyMatrix;

    public void addVertex(Integer v){
        graph.put(v, new LinkedList<Integer>());
    }

    public void addVertex(Integer v, double weight){
        graph.put(v, new LinkedList<Integer>());
        functionality.put(v, weight);
    }

    public void addVertex(Integer v, int EU){
        graph.put(v, new LinkedList<Integer>());
        eu.put(v, EU);
    }

    public void addEdge(Integer source, Integer destination, boolean directed){
        if(!graph.containsKey(source)){
            addVertex(source);
        }
        if(!graph.containsKey(destination)){
            addVertex(destination);
        }

        graph.get(source).add(destination);
        if(!directed){
            graph.get(destination).add(source);
        }
    }

    public void addEnergy(Integer source, Integer destination, Integer energy){
        if(energyMatrix == null){
            int vtxCount = getVertices();
            energyMatrix = new int[vtxCount][vtxCount];
        }
        else{
            energyMatrix[source][destination] = energy;
        }
    }

    public boolean hasVertex(Integer v){
        if(graph.containsKey(v)){
            return true;
        }
        else{
            return false;
        }
    }

    public int getVertices(){
        ArrayList<Integer> temp = new ArrayList<Integer>(graph.keySet());
        return temp.size();
    }

    private int getKey(int obj){
        ArrayList<Integer> temp = new ArrayList<Integer>(graph.keySet());
        for(int i = 0; i < temp.size(); i++){
            if(temp.get(i) == obj){
                return i;
            }
        }
        return -1;
    }

    private int getOGKey(int key){
        ArrayList<Integer> temp = new ArrayList<Integer>(graph.keySet());
        return temp.get(key);
    }

    public void deleteVertex(Integer v){
        if(hasVertex(v)){
            graph.remove(v);
            for(Integer key : graph.keySet()){
                for(Integer value: graph.get(key)){
                    if(value.equals(v)){
                        graph.get(key).remove(v);
                        break;
                    }
                }
            }       
        }
    }

    public boolean isConnected(){
        boolean[] isVisited = new boolean[getVertices()];
        dfs(0, isVisited);
        for(boolean idx : isVisited){
            if(!idx){
                return false;
            }
        }
        return true;
    }

    private void dfs(int source, boolean[] visited){
        visited[source] = true;
        for(int i = 0; i < graph.get(getOGKey(source)).size(); i++){
            int successor = graph.get(getOGKey(source)).get(i);
            int idx = getKey(successor);
            if(idx != -1 && !visited[idx]){
                dfs(idx, visited);
            }
        }
    }

    private void calculateEnergyCost(){
        for(int i = 0; i < energyMatrix.length; i++){
            for(int j = 0; j < energyMatrix[0].length; j++){
                if(energyMatrix[i][j] != 0){
                    double dividend = functionality.get(i) * functionality.get(j);
                    int result = (int) (energyMatrix[i][j] / dividend);
                    energyMatrix[i][j] = result;
                }
            }
        }
    }

    private int minDist(int[] minCostArr, boolean[] dSet){
        int min = Integer.MAX_VALUE;
        int idx = -1;
        int vtxCount = getVertices();

        for(int i = 0; i < vtxCount; i++){
            if(!dSet[i] && minCostArr[i] <= min){
                min = minCostArr[i];
                idx = i;
            }
        }
        return idx;
    }

    public int dijkstraFind(){
        calculateEnergyCost();
        int vtxCount = getVertices();
        int[] minCost = new int[vtxCount];
        boolean[] dijkstraSet = new boolean[vtxCount];
        for(int i = 0; i < minCost.length; i++){
            minCost[i] = Integer.MAX_VALUE;
        }
        minCost[0] = 0;
        for(int i = 0; i < vtxCount -1; i++){
            int temp = minDist(minCost, dijkstraSet);
            dijkstraSet[temp] = true;
            for(int j = 0; j < vtxCount; j++){
                if(!dijkstraSet[j] && energyMatrix[temp][j] != 0 && minCost[temp] != Integer.MAX_VALUE && minCost[temp] + energyMatrix[temp][j] < minCost[j]){
                    minCost[j] = minCost[temp] + energyMatrix[temp][j];
                }
            }
        }
        return minCost[vtxCount-1];
    }

    public int totalTimelines(){
        ArrayList<Integer> timelines = new ArrayList<>();
        possibleTimelines(timelines, 0, eu.get(0));
        timelines.add(0, eu.get(0));
        return timelines.size();
    }

    private void possibleTimelines(ArrayList<Integer> result, int n, int m){
        if(graph.get(n).size() == 0){
            return;
        }
        for(int edge: graph.get(n)){
            result.add(m + eu.get(edge));
            possibleTimelines(result, edge, m + eu.get(edge));
        }
    }

    public int validTimelines(int threshold){
        ArrayList<Integer> timelines = new ArrayList<>();
        possibleTimelines(timelines, 0, eu.get(0));
        timelines.add(0, eu.get(0));
        int counter = 0;
        for(int timeline: timelines){
            if(timeline >= threshold){
                counter++;
            }
        }
        return counter;
    }
}