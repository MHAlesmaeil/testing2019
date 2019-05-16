package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;


import java.util.List;
/**
 * Class Description:
 * ClusterSSE class is to calculate the Error Sum of Squares of a single cluster through calculating its points' values
 */
public class ClusterSSE {
    private ClusterMean clusterMean = new ClusterMean();
    public double computeSSEBasedOnMeanOfCluster(List<double[]> cluster){
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
                try {
                    computeSSETemp += Math.pow(temp[pointCounter]-clusterMean[pointCounter],2);
                }catch (Exception e){ }
            }
        }
        return computeSSETemp;
    }

    /**
     * compute the SSE of single cluster
    */
    public double computeSSEOfSingleClusterBasedOnCenterOfCluster(Cluster clusterToBeComputed){
        // getting the center of the cluster
        double[] clusterCenter = clusterToBeComputed.getClusterCenter();
        // initiate a variable with 0
        double computeSSETemp=0;
        // loop through cluster points
        for (int computeSSECounter=0; computeSSECounter<clusterToBeComputed.getClusterPoints().size(); computeSSECounter++){
            // get cluster point at computerSSECounter value
            double[] temp =  clusterToBeComputed.getClusterPoints().get(computeSSECounter);
            // looping through each point's attribute and sum it SSE in comparision with average
            // first point which is id need to be escaped
            for (int pointCounter =1; pointCounter<temp.length; pointCounter++){
                try {
                    computeSSETemp += Math.pow(temp[pointCounter]-clusterCenter[pointCounter],2);
                }catch (Exception e){}
            }
        }
        return computeSSETemp;
    }

    /**
     * Compute SSE of list of list based on the center of each cluster
    */
    public double computeSSEOfListOfClustersBasedOnCenters(List<Cluster> listOfClusters){
        // initiate a variable with 0
        double computeSSETemp=0;
        // loop through clusters and calculate their SSE
        for (int x = 0; x<listOfClusters.size();x++){
            try {
                computeSSETemp += computeSSEOfSingleClusterBasedOnCenterOfCluster(listOfClusters.get(x));
            }catch (Exception e){}
        }
        return computeSSETemp;
    }

    /**
     * Compute the SSE of list of cluster based on the mean of each cluster
    */
    public double computeSSEOfListOfClustersBasedOnMeans(List<Cluster> listOfClsuters){
        // initiate a variable with 0
        double computeSSETemp=0;
        // loop through clusters and calculate their SSE
        for (int x = 0; x<listOfClsuters.size();x++){
            try {
                computeSSETemp += computeSSEBasedOnMeanOfCluster(listOfClsuters.get(x).getClusterPoints());
            }catch (Exception e){}
        }
        return computeSSETemp;
    }

}
