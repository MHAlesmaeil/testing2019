package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;

import java.util.List;

public class SimulatedAnnealing implements ClusteringMethod {

    @Override
    public List<Cluster> computeCluster(int numberOfCluter, List<String[]> pointsToBeClustered, int distanceMethodNumber, boolean showResultInConsole) throws Exception {
        return null;
    }

    @Override
    public List<Cluster> computeCluster(int numberOfCluter, List<String[]> pointsToBeClustered, int numberOfItration, double startTemprature, double tempratureToStopTheProcess, double alphaValue, boolean showResultInConsole) throws Exception {
        return null;
    }
}
