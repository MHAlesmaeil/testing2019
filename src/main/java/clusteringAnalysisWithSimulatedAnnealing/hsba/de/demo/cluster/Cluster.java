package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.*;

@Component
public class Cluster {
    @Id @GeneratedValue
    private Long clusterId;
    private String clusterName;
    List<Double[]> clusterPoints;
    List<Double> clusterMean;

    // constructor


    public Cluster() {
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

    public List<Double[]> getClusterPoints() {
        if (clusterPoints==null){
            clusterPoints= new ArrayList<>();
        }
        return clusterPoints;
    }

    public void setClusterPoints(List<Double[]> clusterPoints) {
        this.clusterPoints = clusterPoints;
    }

    public List<Double> getClusterMean() {
        if (clusterMean==null){
            clusterMean= new ArrayList<>();
        }
        return clusterMean;
    }

    public void setClusterMean(List<Double> clusterMean) {
        this.clusterMean = clusterMean;
    }

}
