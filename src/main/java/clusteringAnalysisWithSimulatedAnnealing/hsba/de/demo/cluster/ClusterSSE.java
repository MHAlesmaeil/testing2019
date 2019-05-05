package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;


import java.util.List;
/**
 * Class Description:
 * ClusterSSE class is to calculate the Error Sum of Squares of a single cluster through calculating its points' values
 */
public class ClusterSSE {
    private ClusterMean clusterMean = new ClusterMean();
    public double computeSSE(List<double[]> cluster){
        // calculating the mean of the cluster
        double[] clusterMean = this.clusterMean.computeClusterMean(cluster);
        // initiate a variable with 0
        double computeSSETemp=0;
        // loop through cluster points
        for (int computeSSECounter=0; computeSSECounter<cluster.size(); computeSSECounter++){
            // get cluster point at computerSSECounter value
            double[] temp =  cluster.get(computeSSECounter);
            // looping through each point's attribute and sum it SSE in comparision with average
            // first point which is id need to be escaped
            for (int pointCounter =1; pointCounter<temp.length; pointCounter++){
                computeSSETemp += Math.pow(temp[pointCounter]-clusterMean[pointCounter],2);
            }
        }
        return computeSSETemp;
    }

}
