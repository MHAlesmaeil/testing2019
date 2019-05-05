package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import java.util.List;
/**
 * Class Description:
 * ClusterMean class is to calculate the mean of a single cluster through calculating its points' values
 */
public class ClusterMean {
    public double [] computeClusterMean(List<double[]> clusters){
        // initiate array variable with specific length
        double [] computeClusterMeanTemp = new double[clusters.get(0).length];
        // Id will not be computed, therefore it will be given the number 1
        computeClusterMeanTemp[0]= 1;
        // calcute the values of each column
        for (int computeClusterMeanCounter=0; computeClusterMeanCounter<clusters.size(); computeClusterMeanCounter++){
            // get row
            double [] clusterPoint = clusters.get(computeClusterMeanCounter);
            // get column but escape the first one which is the ID
            for (int computeClusterSingleElement=1; computeClusterSingleElement<clusterPoint.length;computeClusterSingleElement++){
                // add the value to the current one
                computeClusterMeanTemp[computeClusterSingleElement]+=clusterPoint[computeClusterSingleElement];
            }
        }
        // when calculation is done, averages can be computed based on te list size.
        for (int computeClusterMeanAverage=1;computeClusterMeanAverage<computeClusterMeanTemp.length; computeClusterMeanAverage++ ){
            // do division
            computeClusterMeanTemp[computeClusterMeanAverage]= computeClusterMeanTemp[computeClusterMeanAverage]/clusters.size();
        }
        return computeClusterMeanTemp;
    }
}
