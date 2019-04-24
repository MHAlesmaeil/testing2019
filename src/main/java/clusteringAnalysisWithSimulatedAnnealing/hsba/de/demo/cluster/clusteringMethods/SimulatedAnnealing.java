package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;

import java.util.List;

public class SimulatedAnnealing implements ClusteringMethod {
    @Override
    public List<Cluster> computeCluster(int numberOfCluter, List<String[]> pointsToBeClustered, int distanceMethodNumber) {
        // distance method in simulated annealing is not relevant, as euclideanDistance is going to be used.
        return null;
    }
}
