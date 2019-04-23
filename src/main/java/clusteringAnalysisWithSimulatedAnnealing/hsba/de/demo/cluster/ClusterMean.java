package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import java.util.List;

public class ClusterMean {
    public double [] computeClusterMean(List<double[]> cluster){
        // initiate array variable with specific length
        double [] computeClusterMeanTemp = new double[cluster.get(1).length];
        // Id will not be computed, therefore it will be given the number 1
        computeClusterMeanTemp[0]= 1;
        for (int computeClusterMeanCounter=0; computeClusterMeanCounter<cluster.size(); computeClusterMeanCounter++){
            double [] clusterPoint = cluster.get(computeClusterMeanCounter);
            // escape the first element as it does not need to be computed
            for (int computeClusterSingleElement=1; computeClusterSingleElement<clusterPoint.length;computeClusterSingleElement++){
                computeClusterMeanTemp[computeClusterSingleElement]+=clusterPoint[computeClusterSingleElement];
            }
        }
        // when calculation is done, averages can be computer based on te list size.
        for (int computeClusterMeanAverage=1;computeClusterMeanAverage<computeClusterMeanTemp.length; computeClusterMeanAverage++ ){
            // do division
            computeClusterMeanTemp[computeClusterMeanAverage]= computeClusterMeanTemp[computeClusterMeanAverage]/cluster.size();
        }
        return computeClusterMeanTemp;
    }
}
