package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Class Description:
 * Simulated Annealing class is an aggregated class for the methods in this project
*/

public class SimulatedAnnealingMMC {
    private DataProcessing dataProcessing = new DataProcessing();
    private GeneralMethods generalMethods = new GeneralMethods();
    private SimulatedAnnealingMethods simAnMethods = new SimulatedAnnealingMethods();
    private ClusterSSE clusterSSE = new ClusterSSE();

    public void computeCluster(int numberOfCluter, List<String[]> pointsToBeClustered,double initialTemperature, double finalTemperature, double alphaValue, double mutationFactor, int numberOfInteraionsPerGivenTemperature,  boolean showResultInConsole) throws Exception {
        // start timer
        Instant start = Instant.now();
        // create empty clusters
        dataProcessing.setCreatedClusters(numberOfCluter);
        // created clusters
        List<Cluster> listOfCreatedClusters = dataProcessing.getCreatedClusters();
        // empty clusters are created. Now, we need to call the points
        List<double[]> listOfPOints = generalMethods.dataSetStringToDoubleWithoutHeaders(pointsToBeClustered,showResultInConsole);
        // normalize the values of imported list
        listOfPOints = generalMethods.normalizeDoubleList(listOfPOints, showResultInConsole);
        // generate clusters' centers
        simAnMethods.generateInitialClusterCeneters(listOfCreatedClusters,listOfPOints,showResultInConsole);
        // measure SSE of the first solution
        double  initialValueCenter = clusterSSE.computeSSEOfListOfClustersBasedOnCenters(listOfCreatedClusters);
        double initialValueMean = clusterSSE.computeSSEOfListOfClustersBasedOnMeans(listOfCreatedClusters);

        // Start optimizing
        while (initialTemperature>finalTemperature){
            // call main method of Simulated Annealing and do the number of iteration per given temperature
            for (int x = 0 ; x<numberOfInteraionsPerGivenTemperature; x++){
                simAnMethods.chooseRandomCenterAndAlterItSMC(listOfCreatedClusters,listOfPOints,initialTemperature,mutationFactor,showResultInConsole);
            }
            // decreasing the initial monotonically
            initialTemperature *= 1-alphaValue;
        }
        // inject the final result of the best found centers
        simAnMethods.injectCentersListInCluster(listOfCreatedClusters,listOfPOints,showResultInConsole);

        // Stop timer
        Instant finish = Instant.now();
        // Show result after finishing
        generalMethods.showFormedClustersSimulatedAnnealing(dataProcessing.getCreatedClusters());
        // calculate the time
        long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
        // Print out
        System.out.println("Execution Time is "+ timeElapsed+ " (ms)");
        System.out.println("Initial SEE-Means value is " + initialValueMean+ " and initial SSE-Center value is "+ initialValueCenter);
        System.out.println("########################################");
        System.out.println("########################################");
        System.out.println("");

    }
}
