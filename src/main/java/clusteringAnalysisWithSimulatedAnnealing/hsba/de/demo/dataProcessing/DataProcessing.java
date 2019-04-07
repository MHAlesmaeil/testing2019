package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataProcessing;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dataProcessing")
@Component
public class DataProcessing {
    @Id
    @GeneratedValue
    private Long processingId;
    private String processingName;
    private int clusteringMethod;
    private int distanceCalMethod;
    private int numberOfCluster;
    private int numberOfIteration;

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
}
