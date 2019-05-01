package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.*;


public class Cluster {

    List<double[]> clusterPoints = new ArrayList<>();
    double[] cluserCenter ;

    // constructor
    public Cluster() {
    }

    public List<double[]> getClusterPoints() {

        return clusterPoints;
    }
    public void setClusterPoints(List<double[]> clusterPoints) {
        this.clusterPoints = clusterPoints;
    }

    public double[] getCluserCenter() {
        return cluserCenter;
    }

    public void setCluserCenter(double[] cluserCenter) {
        this.cluserCenter = cluserCenter;
    }

    public void addPointToCluster(double [] pointToBeAdded){
        clusterPoints = getClusterPoints();
        clusterPoints.add(pointToBeAdded);
    }

}
