package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.EuclideanDistance;

import java.util.List;
import java.util.Random;

public class SimulatedAnnealingMethods {
    EuclideanDistance euclideanDistance = new EuclideanDistance();
    ClusterSSE clusterSSE = new ClusterSSE();
    /**
     * Generate the initial center
    */
    public double[] clusterCenterInitialization(List<Cluster> ListOfClsuters,List<double[]> listOfNormalizedPoints, boolean showResultInConsole){
        // number of attribute within a single point in the list. -1 is specified as the point has also a key #
        int numberOfAttribute = listOfNormalizedPoints.get(0).length;
        double[] generatedCenter = new double[numberOfAttribute];
        // center does not need to process the id, however it will be given a value of 1
        generatedCenter[0]= 1;
        // Generate the first center
        for (int x = 1; x<numberOfAttribute;x++){
            generatedCenter[x] = new Random().nextDouble();
        }
        if (showResultInConsole){
            for (int x = 0; x<numberOfAttribute;x++){
                System.out.println("Generated double for attribute "+x+ " is "+ generatedCenter[x]);
            }
        }
        // check that no outlier was generated as a center of the cluster
        // This passes if a center has at least one point assigned, else generate a new one

        return generatedCenter;
    }
    public void injectNewCenterInCluster(List<Cluster> clusterList, int clusterNumber, double[] centerToBeInjected, boolean showResultInConsole){
        clusterList.get(clusterNumber).setCluserCenter(centerToBeInjected);
        if (showResultInConsole){
            System.out.println("Cluster # "+ clusterNumber+ " has a new center ");
        }
    }
    public void generateInitialClusterCeneters(List<Cluster> listOfClsuters,List<double[]> listOfNormalizedPoints, boolean showResultInConsole){
        // loop through the clusters and inject a value from the function clusterCenterInitialization
        for (int x = 0; x<listOfClsuters.size(); x++){
            injectNewCenterInCluster(listOfClsuters,x,clusterCenterInitialization(listOfClsuters,listOfNormalizedPoints,showResultInConsole), showResultInConsole);

        }
        // assign points to clusters based on the clusters' centers
        System.out.println("New Centers have been generated");
    }
    public void assignPointsToClusters(List<Cluster> listOfClusters, List<double[]> listOfPoints, boolean showResultInConsole){
        // loop through the points in the list of arrays
        int indexOfPointInListOfPoints = -1;
        int bestClusterCenter = -1;
        double oldValue = -1;
        for (int x = 0; x<listOfPoints.size();x++ ){
            // loop through the centers of clusters
            for (int y = 0; y<listOfClusters.size(); y++){
                double tempDouble = euclideanDistance.computeDistance(listOfPoints.get(x),listOfClusters.get(y).getCluserCenter());
                if ( y == 0){
                    // first solution
                    oldValue = tempDouble;
                    indexOfPointInListOfPoints = x;
                    bestClusterCenter = y;
                }else{
                    if (tempDouble<oldValue){
                        // new solution is found, so update the solution
                        oldValue = tempDouble;
                        indexOfPointInListOfPoints = x;
                        bestClusterCenter = y;
                    }
                }
            }
            // inject the solution of the point of array in the cluster and check the next point
            injectPointIntoCluster(listOfClusters,bestClusterCenter,listOfPoints,indexOfPointInListOfPoints,showResultInConsole);
        }
    }
    public void injectPointIntoCluster(List<Cluster> listOfClusters, int clusterNumber, List<double[]> listOfPoints, int positionOfPointInListOfPoints, boolean showResultInConsole){
        listOfClusters.get(clusterNumber).addPointToCluster(listOfPoints.get(positionOfPointInListOfPoints));
        if (showResultInConsole){
            System.out.println("A point has been added to the cluster # "+ clusterNumber);
        }
    }
    public double costFunctionOfClusterList(List<Cluster> listOfClusters){
        double temp = 0;
        // loop through the clusters
        for (int x = 0; x<listOfClusters.size();x++){
            // if a cluster does not have any point, then escape it
            if (listOfClusters.get(x).getClusterPoints().size()!=0){
            temp += clusterSSE.computeSSE(listOfClusters.get(x).getClusterPoints());
            }
        }
        return temp;
    }
}
