package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;

import java.util.List;

public interface ClusteringMethod {
    // as the list of points is imported as String[], a List<String[]> is as parameter created
    List<Cluster> computeCluster(int numberOfCluter,List<String[]> pointsToBeClustered, int distanceMethodNumber) throws Exception;
}