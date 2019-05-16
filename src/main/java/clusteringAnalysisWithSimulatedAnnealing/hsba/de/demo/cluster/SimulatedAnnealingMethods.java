package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.EuclideanDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Class Description:
 * SimulatedAnnealingMethods class has the main methods which used to implement Simulated Annealing
 */
public class SimulatedAnnealingMethods {
    EuclideanDistance euclideanDistance = new EuclideanDistance();
    ClusterSSE clusterSSE = new ClusterSSE();
    Random random = new Random();
    GeneralMethods generalMethods = new GeneralMethods();
    /**
     * Generate the initial centers for each cluster
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
        // show if requested
        if (showResultInConsole){
            for (int x = 0; x<numberOfAttribute;x++){
                System.out.println("Generated double for attribute "+x+ " is "+ generatedCenter[x]);
            }
        }
        return generatedCenter;
    }
    /**
     * The method is used to inject a center into a cluster
    */
    public void injectNewCenterInCluster(List<Cluster> clusterList, int clusterNumber, double[] centerToBeInjected, boolean showResultInConsole){
        clusterList.get(clusterNumber).setClusterCenter(centerToBeInjected);
        // show if requested
        if (showResultInConsole){
            System.out.println("Cluster # "+ clusterNumber+ " has a new center ");
        }
    }
    /**
     * // TODO: 16.05.2019 we need to describe this function
    */
    public void injectCentersListInCluster(List<Cluster> clusterList, List<double[]> listOfPionts, boolean showResultInConsole){
        List<double[]> centersToBeInjected = getBestCenters();
        // loop through the clusters
        for (int x =0; x<clusterList.size(); x++){
            injectNewCenterInCluster(clusterList,x,centersToBeInjected.get(x),showResultInConsole);
            clusterList.get(x).setClusterCenter(centersToBeInjected.get(x));
        }
        // assign the points to the clusters
        assignPointsToClusters(clusterList,listOfPionts,showResultInConsole);
    }
    /**
     * This method injects the generated centers into their cluster and check if at least one point is
     * assigned to each cluster based on the generated centers. if not, repeat the generation of clusters'
     * until the last condition is fulfilled
    */
    public void generateInitialClusterCeneters(List<Cluster> listOfClsuters,List<double[]> listOfNormalizedPoints, boolean showResultInConsole){
        // loop through the clusters and inject a value from the function clusterCenterInitialization
        for (int x = 0; x<listOfClsuters.size(); x++){
            injectNewCenterInCluster(listOfClsuters,x,clusterCenterInitialization(listOfClsuters,listOfNormalizedPoints,showResultInConsole), showResultInConsole);

        }
        // assign points to clusters based on the clusters' centers
        assignPointsToClusters(listOfClsuters,listOfNormalizedPoints,showResultInConsole);
        // check that every cluster has at least one point, otherwise generate the centers again
        if (generalMethods.areAllClustersHavingAtLeastOnePoint(listOfClsuters,showResultInConsole)==false){
            if (showResultInConsole){
                System.out.println("At least one cluster does not have the minimum amount of points");
            }
            // The method call itself again
            generateInitialClusterCeneters(listOfClsuters,listOfNormalizedPoints,showResultInConsole);
        }
    }
    /**
     * Based on the generated centers, the points will be assigned to the nearest center
    */
    public void assignPointsToClusters(List<Cluster> listOfClusters, List<double[]> listOfPoints, boolean showResultInConsole){
        // loop through the clusters and set the points to NULL
        for (int x = 0; x<listOfClusters.size();x++){
            listOfClusters.get(x).setClusterPoints(null);
        }
        int indexOfPointInListOfPoints = -1;
        int bestClusterCenter = -1;
        double oldValue = -1;
        for (int x = 0; x<listOfPoints.size();x++ ){
            // Loop through the centers of clusters
            for (int y = 0; y<listOfClusters.size(); y++){
                // Measure the new distance
                double tempDouble = euclideanDistance.computeDistance(listOfPoints.get(x),listOfClusters.get(y).getClusterCenter());
                // First solution
                if ( y == 0){
                    oldValue = tempDouble;
                    indexOfPointInListOfPoints = x;
                    bestClusterCenter = y;
                }else{
                    // Optimizing the current solution
                    if (tempDouble<oldValue){
                        // New solution is found, so update the solution
                        oldValue = tempDouble;
                        indexOfPointInListOfPoints = x;
                        bestClusterCenter = y;
                    }
                }
            }
            // Inject the solution of the point in the cluster
            injectPointIntoCluster(listOfClusters,bestClusterCenter,listOfPoints,indexOfPointInListOfPoints,showResultInConsole);
        }
    }
    /**
     * This method injects a point into certain cluster
    */
    public void injectPointIntoCluster(List<Cluster> listOfClusters, int clusterNumber, List<double[]> listOfPoints, int positionOfPointInListOfPoints, boolean showResultInConsole){
        listOfClusters.get(clusterNumber).addPointToCluster(listOfPoints.get(positionOfPointInListOfPoints));
        // show if requested
        if (showResultInConsole){
            System.out.println("A point has been added to the cluster # "+ clusterNumber);
        }
    }
    /**
     * costFunctionOfClusterList calculates how good the solution is based on SSE
    */
    public double costFunctionOfClusterList(List<Cluster> listOfClusters){
        double temp = 0;
        // loop through the clusters
        for (int x = 0; x<listOfClusters.size();x++){
            // if a cluster does not have any point, it will through an exception
            try {
                temp += clusterSSE.computeSSEBasedOnMeanOfCluster(listOfClusters.get(x).getClusterPoints());
            }catch (Exception e){}

        }
        return temp;
    }
    /**
     * chooseRandomCenterAndAlterIt chooses a center random to start altering it
    */
    List<double[]> bestCenters;

    public List<double[]> getBestCenters() {
        if (bestCenters==null){
            bestCenters = new ArrayList<>();
        }
        return bestCenters;
    }

    public void setBestCenters(List<double[]> bestCenters) {

        this.bestCenters = bestCenters;
    }
    // SSE value of the bestFoundcenter
    double bestFoundCenterSEEValue =-1;


    public double getBestFoundCenterSEEValue() {
        return bestFoundCenterSEEValue;
    }

    public void setBestFoundCenterSEEValue(double bestFoundCenterSEEValue) {
        this.bestFoundCenterSEEValue = bestFoundCenterSEEValue;
    }

    public void chooseRandomCenterAndAlterIt(List<Cluster> listOfClusters, List<double[]> listOfNormalizedPoints, double acceptanceTemperature, double mutationFactor, boolean showResultInConsole){
        // TODO: 15.05.2019 we need to add some lines that would 1. calculate SEE based on the altered centers 2. we need to let this function to remember the best result
        // if a list of centers is empty, then inject the current centers
        if (getBestCenters().size()==0){
            for (int x = 0; x<listOfClusters.size(); x++){
                // add the centers to the center list
                getBestCenters().add(listOfClusters.get(x).getClusterCenter());
            }
        }
        int randomCluster = random.nextInt(listOfClusters.size());
        // save old cluster values in case, that a revise is necessary
        double [] chosenCenterOldValues = listOfClusters.get(randomCluster).getClusterCenter();
        // double value to make a comparision
        double sseValueOld = clusterSSE.computeSSEOfListOfClustersBasedOnCenters(listOfClusters);
        // show if requested
        if (showResultInConsole){
            System.out.println("SSE Old is "+ sseValueOld);
        }
        // initializing new double array to be the altered version of chosenCenterOldValues
        double [] alteredCenter = new double[chosenCenterOldValues.length];
        // alter the chosen cluster center and start from second values, as the one is not set to be optimized
        for (int x = 1; x<alteredCenter.length;x++){
            alteredCenter[x] = chosenCenterOldValues[x] + random.nextGaussian()* mutationFactor;
            if (showResultInConsole){
                System.out.println("Old value is "+ chosenCenterOldValues[x]+" new value "+alteredCenter[x]);
            }
        }
        // inject the new center instead the old one and calculate the sse value
        injectNewCenterInCluster(listOfClusters,randomCluster,alteredCenter,showResultInConsole);
        // assign the points to the clusters
        assignPointsToClusters(listOfClusters,listOfNormalizedPoints,showResultInConsole);
        // double value to make a comparision
        double sseValueNew = clusterSSE.computeSSEOfListOfClustersBasedOnCenters(listOfClusters);
        // show if requested
        if (showResultInConsole){
            System.out.println("SSE new is "+ sseValueNew);
        }
        // if seeValueNew shows a greater value than the old one then accept it as to Metropolis criterion

        // if new value is smaller than the old one, then accept the new solution
        if (sseValueNew<sseValueOld){
            // show if requested
            if (showResultInConsole){
                System.out.println("An improvement has been detected for cluster "+ randomCluster);
            }
            // check if the bestFoundcenter need to be updated
            if (getBestFoundCenterSEEValue()==-1){
                // first solution
                setBestFoundCenterSEEValue(sseValueNew);
                // remove all exist centers
                setBestCenters(null);
                for (int x = 0; x<listOfClusters.size(); x++){
                    // add the centers to the center list
                    getBestCenters().add(listOfClusters.get(x).getClusterCenter());
                }
            }else{
                // check if the new solution give a smaller SSE value in comparision with the current best solution
                if (sseValueNew<getBestFoundCenterSEEValue()){
                    // update bestFoundValue
                    setBestFoundCenterSEEValue(sseValueNew);
                    // update centers
                    setBestCenters(null);
                    for (int x = 0; x<listOfClusters.size(); x++){
                        // add the centers to the center list
                        getBestCenters().add(listOfClusters.get(x).getClusterCenter());
                    }
                }

            }

        }else{

                // show if requested
                if (showResultInConsole){
                    System.out.println("No improvement has been detected ");
                }
                // check if the solution can be accepted
                if (canNewSolutionBeAccepted(sseValueOld,sseValueNew,acceptanceTemperature)){
                    // show if requested
                    if (showResultInConsole){
                        System.out.println("However, the solution has been accepted");
                    }
                }else{
                    // solution has been rejected, so inject the old values again
                    injectNewCenterInCluster(listOfClusters,randomCluster,chosenCenterOldValues,showResultInConsole);
                    assignPointsToClusters(listOfClusters,listOfNormalizedPoints,showResultInConsole);
                    // show if requested
                    if (showResultInConsole){
                        System.out.println("Solution has been reversed");
                    }
                }
        }
    }
    /**
     * Check if the new "not optimal" solution can be accepted
    */
    public boolean canNewSolutionBeAccepted(double oldValue, double newValue,double acceptanceTemperature){
        // throw z value randomly
        double randomToMeasureAgainst = random.nextDouble();
        // calculate the Metropolis
        double probabilityOfAcceptingNewSolution = Math.exp(-((newValue-oldValue)/acceptanceTemperature));
        if (randomToMeasureAgainst>probabilityOfAcceptingNewSolution){
            return false;

        }else{
            return true;
        }
    }
}
