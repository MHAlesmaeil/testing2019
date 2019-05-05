package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataPreperation;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;



import java.util.ArrayList;
import java.util.List;
/**
 * Class Description:
 * DataProcessing class creates the list of clusters
 */
public class DataProcessing {
    private List<Cluster> createdClusters;
    // dataset number is to be used to generate empty Clusters
    public List<Cluster> getCreatedClusters() {
        return createdClusters;
    }
    // this method will be called one time from the constructor when the data comes there
    public void setCreatedClusters(int clusterNumber) {
        // create a for loop to generate createdClusters, which is equal to clusterNumber and inject each instance in the createdClusters
        createdClusters = new ArrayList<>();
        for (int x = 1;x<=clusterNumber;x++){
            Cluster cluster= new Cluster();
            createdClusters.add(cluster);
        }
    }
}
