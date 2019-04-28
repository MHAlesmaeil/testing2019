package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.GeneralMethods;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    @ElementCollection
    @Column
    private List<String[]> listOfPoints;

    public void setClusters(List<Cluster> clusters) {
        System.out.println("String oints has been set");
        this.clusters = clusters;
    }

    public List<String[]> getListOfPoints() {
        return listOfPoints;
    }

    public void setListOfPoints(List<String[]> listOfPoints) {
        this.listOfPoints = listOfPoints;
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

    public List<Cluster> getClusters() throws Exception {
        if (clusters == null){
            clusters = new ArrayList<>();
        }
        List<Cluster> clusters = new ArrayList<>();

        for (int x = 1;x<=getNumberOfCluster();x++){
            Cluster cluster= new Cluster();
            cluster.setClusterName("cluster"+x);
            clusters.add(cluster);
        }
        clusters = randomMe(clusters,getListOfPoints());


        return clusters;
    }
    public List<Cluster> randomMe(List<Cluster> clusters, List<String[]> listOfPoints)throws Exception{
        GeneralMethods generalMethods = new GeneralMethods();
        List<double []> doubleList= generalMethods.dataSetStringToDoubleWithoutHeaders(listOfPoints);
        List<Cluster> clusterss = clusters;
        for (int x =0; x<doubleList.size();x++){
            int random = new Random().nextInt(clusterss.size());
            clusterss.get(random).addPointToCluster(doubleList.get(x));
        }
        return clusterss;
    }

}
