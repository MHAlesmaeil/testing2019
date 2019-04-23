package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.get;

public class ClusterSSE {

    public double computeSSE(List<double[]> cluster, Double [] average){
        // initiate a variable with 0
        double computeSSETemp=0;
        // loop through cluster points
        for (int computeSSECounter=0; computeSSECounter<cluster.size(); computeSSECounter++){
            // get cluster point at computerSSECounter value
            double[] temp =  cluster.get(computeSSECounter);
            // looping through each point's attribute and sum it SSE in comparision with average
            // first point which is id need to be escaped
            for (int pointCounter =1; pointCounter<temp.length; pointCounter++){
                computeSSETemp += Math.pow(temp[pointCounter]-average[pointCounter],2);
            }
        }
        return computeSSETemp;
    }

}
