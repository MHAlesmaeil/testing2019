package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.GeneralMethods;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.ChooseDistanceMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.DistanceMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing.DataProcessing;

import java.util.List;

public class SingleLinkage implements ClusteringMethod {
    private DataProcessing dataProcessing = new DataProcessing();
    private GeneralMethods generalMethods = new GeneralMethods();

    @Override
    public List<Cluster> computeCluster(int numberOfCluter, List<String[]> pointsToBeClustered, int distanceMethodNumber) throws Exception {
        // create empty clusters
        dataProcessing.setCreatedClusters(numberOfCluter);
        System.out.println("number of cluster created "+ dataProcessing.getCreatedClusters().size());
        // as to the given number of the distance method number an instance will be created
        DistanceMethod distanceMethod = new ChooseDistanceMethod().distenceMethodSwitcher(distanceMethodNumber);
        // empty clusters are created. Now, we need to call the points
        List<double[]> initialList = generalMethods.dataSetStringToDoubleWithoutHeaders(pointsToBeClustered);
        System.out.println("number of poins in the list without header is " + initialList.size());
        // if true, check points in the initialList against the points in the list as well as in the clusters
        boolean expandedTest = true;
        // the points in the initialList need to be distributed on the created clusters
        while (initialList.size()>0){
            // local variables for test
            // initialize int variables with value that cannot really be used as index
            int indexOfCandidatePoint= -1;
            int indexOfCluster = -1;
            double oldValue=0;
            double newValue=0;

            // do check
                // if each cluster has at least one element/point we just need to measure the points in the initialList against the points in the cluster
                if (expandedTest==true){
                    System.out.println("Expanded Test is selected");
                    // check against all points in the list and in the clusters
                    for (int pointToBeChecked = 0; pointToBeChecked<initialList.size(); pointToBeChecked++){
                        // check against the other points in the list
                        for (int pointToBeCheckedAgainst = 0; pointToBeCheckedAgainst<initialList.size(); pointToBeCheckedAgainst ++){
                            // point does not need to check against itself
                            if (pointToBeChecked!=pointToBeCheckedAgainst){
                                newValue = distanceMethod.computeDistance(initialList.get(pointToBeChecked), initialList.get(pointToBeCheckedAgainst));
                                // for first solution
                                if (pointToBeChecked==0&&pointToBeCheckedAgainst==1){
                                    // accept the new value as an old on
                                    oldValue = newValue;
                                    // get the index of the element
                                    indexOfCandidatePoint = initialList.indexOf(initialList.get(pointToBeChecked));
                                }else{
                                    // update the best solution, if the newValue shows better result
                                    if (newValue<oldValue){
                                        // update the old value
                                        oldValue=newValue;
                                        // update the index of the point
                                        indexOfCandidatePoint = initialList.indexOf(initialList.get(pointToBeChecked));
                                    }
                                }
                            }
                        }
                        // check against the points in the cluster :: looping in the clusters
                        for (int clusterIdCheck = 0; clusterIdCheck<dataProcessing.getCreatedClusters().size();clusterIdCheck++){
                            // looping in the points for the clusters
                            for (int pointsOfClusterToBeChecked =0; pointsOfClusterToBeChecked<dataProcessing.getCreatedClusters().get(clusterIdCheck).getClusterPoints().size(); pointsOfClusterToBeChecked++){
                                newValue= distanceMethod.computeDistance(initialList.get(pointToBeChecked),dataProcessing.getCreatedClusters().get(clusterIdCheck).getClusterPoints().get(pointsOfClusterToBeChecked));
                                // if newValue smaller than the oldValue
                                if (newValue<oldValue){
                                    // update old value
                                    oldValue = newValue;
                                    // update index of best candidate
                                    indexOfCandidatePoint = initialList.indexOf(initialList.get(pointToBeChecked));
                                    // set the index of cluster to injected later on
                                    indexOfCluster = clusterIdCheck;
                                }
                            }

                        }
                    }

                }else{
                    // search for the best suitable cluster
                    // check against all points in the clusters
                    for (int pointToBeChecked = 0; pointToBeChecked<initialList.size(); pointToBeChecked++){

                        // check against the points in the cluster :: looping in the clusters
                        for (int clusterIdCheck = 0; clusterIdCheck<dataProcessing.getCreatedClusters().size();clusterIdCheck++){
                            // looping in the points for the clusters
                            for (int pointsOfClusterToBeChecked =0; pointsOfClusterToBeChecked<dataProcessing.getCreatedClusters().get(clusterIdCheck).getClusterPoints().size(); pointsOfClusterToBeChecked++){
                                newValue= distanceMethod.computeDistance(initialList.get(pointToBeChecked),dataProcessing.getCreatedClusters().get(clusterIdCheck).getClusterPoints().get(pointsOfClusterToBeChecked));
                                // if newValue smaller than the oldValue
                                if (newValue<oldValue){
                                    // update old value
                                    oldValue = newValue;
                                    // update index of best candidate
                                    indexOfCandidatePoint = initialList.indexOf(initialList.get(pointToBeChecked));
                                    // set the index of cluster to injected later on
                                    indexOfCluster = clusterIdCheck;
                                }
                            }
                        }
                    }
                }

            // add the point to a cluster and remove it from the initialList "reduce the list size"
            // if index of Cluster has value that is greater than 0, it means that the best solution was found with a point which is localed in on of the cluster and so the point will be injected in this cluster
            if (indexOfCluster<-1){
                // add the point to the cluster
                dataProcessing.getCreatedClusters().get(indexOfCluster).addPointToCluster(initialList.get(indexOfCandidatePoint));
                // remove the point from the list
                initialList.remove(indexOfCandidatePoint);
                // info msg
                System.out.println("Point number "+ indexOfCandidatePoint + "were added to cluster "+ indexOfCluster );
            }else{
                // if best combination was not found in the cluster, check if there is an empty cluster to inject the element/point into
                for (int checkEmptyCluster =0; checkEmptyCluster<dataProcessing.getCreatedClusters().size(); checkEmptyCluster ++){
                    // if cluster has no points, inject the point into it
                    if (dataProcessing.getCreatedClusters().get(checkEmptyCluster).getClusterPoints().size()==0){
                        dataProcessing.getCreatedClusters().get(checkEmptyCluster).addPointToCluster(initialList.get(indexOfCandidatePoint));
                        // test
                        System.out.println("Point number "+ indexOfCandidatePoint + "were added to cluster "+ checkEmptyCluster );
                        // once point is added, remove from the list
                        initialList.remove(indexOfCandidatePoint);
                    }
                }
            }

            // if all clusters have at least one point, checkJustInClusters = true
            if (expandedTest!= false){
                for (int x = 0; x<dataProcessing.getCreatedClusters().size(); x++) {
                    // if any has the size of 0
                    if (dataProcessing.getCreatedClusters().get(x).getClusterPoints().size() == 0) {
                        System.out.println("Expanded TEst is selected");
                        expandedTest=true;
                        break;
                    }else {
                        System.out.println("expaneded TEst is disabled");
                        expandedTest=false;
                    }
                }
            }
        }
        return dataProcessing.getCreatedClusters();
    }

}
