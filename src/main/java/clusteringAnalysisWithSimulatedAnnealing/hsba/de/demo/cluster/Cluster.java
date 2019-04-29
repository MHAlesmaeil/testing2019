package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing.DataProcessing;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.*;

@Entity
public class Cluster {
    @Id @GeneratedValue
    private Long clusterId;
    private String clusterName;
    @ElementCollection
    @Column
    List<double[]> clusterPoints = new ArrayList<>();
    @ElementCollection
    @Column
    List<Double> clusterMean = new ArrayList<>();
    @ManyToOne(optional = false, targetEntity = DataProcessing.class)
    DataProcessing dataProcessing;
    private Double sseValue;

    // constructor


    public Cluster() {
    }

    public DataProcessing getDataProcessing() {
        return dataProcessing;
    }

    public void setDataProcessing(DataProcessing dataProcessing) {
        this.dataProcessing = dataProcessing;
    }

    public double getSseValue() {
        if (sseValue == null){
            ClusterSSE clusterSSE = new ClusterSSE();
            sseValue = clusterSSE.computeSSE(getClusterPoints());
        }
        return sseValue;
    }

    public void setSseValue(double sseValue) {
        this.sseValue = sseValue;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public List<double[]> getClusterPoints() {

        return clusterPoints;
    }

    public void setClusterPoints(List<double[]> clusterPoints) {
        this.clusterPoints = clusterPoints;
    }

    public List<Double> getClusterMean() {

        return clusterMean;
    }

    public void setClusterMean(List<Double> clusterMean) {
        this.clusterMean = clusterMean;
    }
    public void addPointToCluster(double [] pointToBeAdded){
        clusterPoints = getClusterPoints();
        clusterPoints.add(pointToBeAdded);
    }

}
