package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dataProcessing")
@Component
public class DataProcessing {
    @Id
    @GeneratedValue
    private Long processingId;
    private Long dataSetNumber;
    private String processingName;
    private int clusteringMethod;
    private int distanceCalMethod;
    private int numberOfCluster;
    private int numberOfIteration;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "dataProcessing", targetEntity = Cluster.class)
    private List<Cluster> clusters;
    /*@ElementCollection
    @Column
    private List<String> something;*/

    public DataProcessing() {

    }

    public Long getDataSetNumber() {
        return dataSetNumber;
    }

    public void setDataSetNumber(Long dataSetNumber) {
        this.dataSetNumber = dataSetNumber;
    }

    public Long getProcessingId() {
        return processingId;
    }

    public void setProcessingId(Long processingId) {
        this.processingId = processingId;
    }

    public String getProcessingName() {
        return processingName;
    }

    public void setProcessingName(String processingName) {
        this.processingName = processingName;
    }

    public int getClusteringMethod() {
        return clusteringMethod;
    }

    public void setClusteringMethod(int clusteringMethod) {
        this.clusteringMethod = clusteringMethod;
    }

    public int getDistanceCalMethod() {
        return distanceCalMethod;
    }

    public void setDistanceCalMethod(int distanceCalMethod) {
        this.distanceCalMethod = distanceCalMethod;
    }

    public int getNumberOfCluster() {
        return numberOfCluster;
    }

    public void setNumberOfCluster(int numberOfCluster) {
        this.numberOfCluster = numberOfCluster;
    }

    public int getNumberOfIteration() {
        return numberOfIteration;
    }

    public void setNumberOfIteration(int numberOfIteration) {
        this.numberOfIteration = numberOfIteration;
    }

    // dataset number is to be used to generate empty Clusters

    public List<Cluster> getClusters() {
        return clusters;
    }
    // this method will be called one time from the constructor when the data comes there
    public void setClusters(int clusterNumber) {
        // create a for loop to generate clusters, which is equal to clusterNumber and inject each instance in the clusters
        clusters = new ArrayList<>();
        for (int x = 1;x<=clusterNumber;x++){
            Cluster cluster= new Cluster();
            cluster.setClusterName("cluster"+x);
            clusters.add(cluster);
        }
    }
}
