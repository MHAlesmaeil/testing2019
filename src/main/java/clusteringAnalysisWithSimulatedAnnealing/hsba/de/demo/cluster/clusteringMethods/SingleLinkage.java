package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.GeneralMethods;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.ChooseDistanceMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.DistanceMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing.DataProcessing;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class SingleLinkage implements ClusteringMethod {
    private DataProcessing dataProcessing = new DataProcessing();
    private GeneralMethods generalMethods = new GeneralMethods();

    @Override
    public List<Cluster> computeCluster(int numberOfCluter, List<String[]> pointsToBeClustered, int numberOfItration, double startTemprature, double tempratureToStopTheProcess, double alphaValue, boolean showResultInConsole) throws Exception {
        return null;
    }

    @Override
    public List<Cluster> computeCluster(int numberOfCluter, List<String[]> pointsToBeClustered, int distanceMethodNumber, boolean showResultInConsole) throws Exception {
        Instant start = Instant.now();
        // create empty clusters
        dataProcessing.setCreatedClusters(numberOfCluter);
        // created clusters
        List<Cluster> listOfCreatedClusters = dataProcessing.getCreatedClusters();
        // empty clusters are created. Now, we need to call the points
        List<double[]> initialList = generalMethods.dataSetStringToDoubleWithoutHeaders(pointsToBeClustered,showResultInConsole);

        while (initialList.size()>1){
            generalMethods.nextPointAndItsClusterAddAndDelete(listOfCreatedClusters,initialList,numberOfCluter,distanceMethodNumber,false);
        }
        generalMethods.showFormedClusters(listOfCreatedClusters,showResultInConsole);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
        System.out.println("Execution Time is "+ timeElapsed+ " (ms)");
        return dataProcessing.getCreatedClusters();
    }

}
