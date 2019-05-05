package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.GeneralMethods;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.SimulatedAnnealingMethods;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataPreperation.DataProcessing;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Class Description:
 * Simulated Annealing class is an aggregated class for the methods in this project
*/

public class SimulatedAnnealing {
    private DataProcessing dataProcessing = new DataProcessing();
    private GeneralMethods generalMethods = new GeneralMethods();
    private SimulatedAnnealingMethods simAnMethods = new SimulatedAnnealingMethods();

    public void computeCluster(int numberOfCluter, List<String[]> pointsToBeClustered, int  singleMarkovChainLength, double acceptanceTemperatureT0, double mutationFactor, boolean showResultInConsole) throws Exception {
        // start timer
        Instant start = Instant.now();
        // create empty clusters
        dataProcessing.setCreatedClusters(numberOfCluter);
        // created clusters
        List<Cluster> listOfCreatedClusters = dataProcessing.getCreatedClusters();
        // empty clusters are created. Now, we need to call the points
        List<double[]> initialList = generalMethods.dataSetStringToDoubleWithoutHeaders(pointsToBeClustered,showResultInConsole);
        // normalize the values of imported list
        initialList = generalMethods.normalizeDoubleList(initialList, showResultInConsole);
        // generate clusters' centers
        simAnMethods.generateInitialClusterCeneters(listOfCreatedClusters,initialList,showResultInConsole);
        // measure SSE of the first solution
        double oldvalue = simAnMethods.costFunctionOfClusterList(listOfCreatedClusters);
        // Start optimizing
        while (singleMarkovChainLength>1){
            // call mean method of Simulated Annealing
            simAnMethods.chooseRandomCenterAndAlterIt(listOfCreatedClusters,initialList,acceptanceTemperatureT0,mutationFactor,showResultInConsole);
            // decrease the number of iteration by one
            singleMarkovChainLength = singleMarkovChainLength-1;
        }
        // Stop timer
        Instant finish = Instant.now();
        // Show result after finishing
        generalMethods.showFormedClusters(listOfCreatedClusters);
        // calculate the time
        long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
        // Print out
        System.out.println("Execution Time is "+ timeElapsed+ " (ms)");
        System.out.println("Old value is " + oldvalue);
    }
}
