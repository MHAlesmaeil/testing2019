package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import java.util.*;

public class Cluster {
    /**
     * Each Cluster has cluster center and number of points by default
    */
    List<double[]> clusterPoints;
    double[] clusterCenter;

    // constructor
    public Cluster() {
    }
    public void addPointToCluster(double [] pointToBeAdded){
        clusterPoints = getClusterPoints();
        clusterPoints.add(pointToBeAdded);
    }

    /**
     * Getter and Setter
    */
    public List<double[]> getClusterPoints() {
        if (clusterPoints==null){
            clusterPoints = new ArrayList<>();
        }
        return clusterPoints;
    }
    public void setClusterPoints(List<double[]> clusterPoints) {
        this.clusterPoints = clusterPoints;
    }

    public double[] getClusterCenter() {
        return clusterCenter;
    }

    public void setClusterCenter(double[] clusterCenter) {
        this.clusterCenter = clusterCenter;
    }



}
