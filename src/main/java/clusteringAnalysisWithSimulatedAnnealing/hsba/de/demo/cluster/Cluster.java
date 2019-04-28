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
    List<double[]> clusterPoints ;
    @ElementCollection
    @Column
    List<Double> clusterMean ;
    @ManyToOne
    DataProcessing dataProcessing;

    // constructor


    public Cluster() {
    }

    public DataProcessing getDataProcessing() {
        return dataProcessing;
    }

    public void setDataProcessing(DataProcessing dataProcessing) {
        this.dataProcessing = dataProcessing;
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
        if (clusterPoints == null){
            clusterPoints = new ArrayList<>();
        }

        return clusterPoints;
    }

    public void setClusterPoints(List<double[]> clusterPoints) {
        this.clusterPoints = clusterPoints;
    }

    public List<Double> getClusterMean() {
        if (clusterMean == null){
            clusterMean = new ArrayList<>();
        }

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
