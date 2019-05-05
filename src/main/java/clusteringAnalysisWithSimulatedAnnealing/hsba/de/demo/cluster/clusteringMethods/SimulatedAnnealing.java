package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.GeneralMethods;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.SimulatedAnnealingMethods;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing.DataProcessing;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class SimulatedAnnealing {
    private DataProcessing dataProcessing = new DataProcessing();
    private GeneralMethods generalMethods = new GeneralMethods();
    private SimulatedAnnealingMethods simAnMethods = new SimulatedAnnealingMethods();

    public List<Cluster> computeCluster(int numberOfCluter, List<String[]> pointsToBeClustered, int  singleMarkovChainLength, double acceptanceTemperatureT0, double mutationFactor, boolean showResultInConsole) throws Exception {
        Instant start = Instant.now();
        // create empty clusters
        dataProcessing.setCreatedClusters(numberOfCluter);
        // created clusters
        List<Cluster> listOfCreatedClusters = dataProcessing.getCreatedClusters();

        // empty clusters are created. Now, we need to call the points
        List<double[]> initialList = generalMethods.dataSetStringToDoubleWithoutHeaders(pointsToBeClustered,showResultInConsole);
        initialList = generalMethods.normalizeDoubleList(initialList, showResultInConsole);

        simAnMethods.generateInitialClusterCeneters(listOfCreatedClusters,initialList,showResultInConsole);
        double oldvalue = simAnMethods.costFunctionOfClusterList(listOfCreatedClusters);
        while (singleMarkovChainLength>1){
            simAnMethods.chooseRandomCenterAndAlterIt(listOfCreatedClusters,initialList,acceptanceTemperatureT0,mutationFactor,showResultInConsole);

            singleMarkovChainLength = singleMarkovChainLength-1;
        }
        generalMethods.showFormedClusters(listOfCreatedClusters);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
        System.out.println("Execution Time is "+ timeElapsed+ " (ms)");
        System.out.println("Old value is " + oldvalue);
        return dataProcessing.getCreatedClusters();
    }
}
