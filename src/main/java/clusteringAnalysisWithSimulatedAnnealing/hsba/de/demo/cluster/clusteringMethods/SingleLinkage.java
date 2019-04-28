package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.GeneralMethods;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing.DataProcessing;

import java.util.List;

public class SingleLinkage implements ClusteringMethod {
    private DataProcessing dataProcessing = new DataProcessing();
    private GeneralMethods generalMethods = new GeneralMethods();

    @Override
    public List<Cluster> computeCluster(int numberOfCluter, List<String[]> pointsToBeClustered, int distanceMethodNumber) throws Exception {
        // create empty clusters

        List<Cluster> clusterList = dataProcessing.getClusters();
        // created clusters
        List<Cluster> listOfCreatedClusters = dataProcessing.getClusters();
        // empty clusters are created. Now, we need to call the points
        List<double[]> initialList = generalMethods.dataSetStringToDoubleWithoutHeaders(pointsToBeClustered);

        while (initialList.size()>0){
            generalMethods.nextPointAndItsClusterAddAndDelete(listOfCreatedClusters,initialList,numberOfCluter,distanceMethodNumber,false);
        }
        generalMethods.showFormedClusters(listOfCreatedClusters,true);


        return dataProcessing.getClusters();
    }

}
