package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.EuclideanDistance;

import java.util.List;
import java.util.Random;

public class SimulatedAnnealingMethods {
    EuclideanDistance euclideanDistance = new EuclideanDistance();
    ClusterSSE clusterSSE = new ClusterSSE();
    Random random = new Random();
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
        for (int x = 0; x<listOfClusters.size();x++){
            listOfClusters.get(x).setClusterPoints(null);
        }
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
        // TODO: 01.05.2019 check if there any cluster withno points and try to identify them to adjust their centers to get at least one point
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
            // if a cluster does not have any point, it will through an exception
            try {
                temp += clusterSSE.computeSSE(listOfClusters.get(x).getClusterPoints());
            }catch (Exception e){}

        }
        return temp;
    }
    // choose a center randomly
    public void chooseRandomCenterAndAlterIt(List<Cluster> listOfClusters, List<double[]> listOfNormalizedPoints,double acceptanceTemperature,double mutationFactor, boolean showResultInConsole){
        int randomCluster = random.nextInt(listOfClusters.size());
        // save old cluster values in case, that a revise is necessary
        double [] chosenCenterOldValues = listOfClusters.get(randomCluster).getCluserCenter();
        double sseValueOld = costFunctionOfClusterList(listOfClusters);
        System.out.println("SSE Old is "+ sseValueOld);
        double [] alteredCenter = new double[chosenCenterOldValues.length];
        // alter the chosen cluster center and start from second values, as the one is not set to be optimized
        for (int x = 1; x<alteredCenter.length;x++){
            alteredCenter[x] = chosenCenterOldValues[x] + random.nextGaussian()* mutationFactor;
            System.out.println("Old value is "+ chosenCenterOldValues[x]+" new value "+alteredCenter[x]);
        }
        // inject the new center instead the old one and calculate the sse value
        injectNewCenterInCluster(listOfClusters,randomCluster,alteredCenter,showResultInConsole);
        // remove all points from clusters

        assignPointsToClusters(listOfClusters,listOfNormalizedPoints,showResultInConsole);
        double sseValueNew = costFunctionOfClusterList(listOfClusters);
        System.out.println("SSE new is "+ sseValueNew);
        // if seeValueNew gives a greater value than the old one then reverse the process and later on accept it as to Metropolis criterion
        if (sseValueNew>sseValueOld){

            System.out.println("No improvement and the chance to accept it is ");
            // check if the solution can be accepted
            if (canNewSolutionBeAccepted(sseValueOld,sseValueNew,acceptanceTemperature)){
                System.out.println("However, the solution has been accepted");
            }else{
                injectNewCenterInCluster(listOfClusters,randomCluster,chosenCenterOldValues,showResultInConsole);
                assignPointsToClusters(listOfClusters,listOfNormalizedPoints,showResultInConsole);
                System.out.println("Solution has been reversed");
            }
        }else if (sseValueNew<sseValueOld){
            System.out.println("An improvement has been detected for cluster "+ randomCluster);
        }else{
            System.out.println("Both are equal, so keep changes");

        }
    }
    public boolean canNewSolutionBeAccepted(double oldValue, double newValue,double acceptanceTemperature){
        double randomToMeasureAgainst = random.nextDouble();
        double probabilityOfAcceptingNewSolution = Math.exp(-(Math.abs(oldValue-newValue)/acceptanceTemperature));
        if (randomToMeasureAgainst>probabilityOfAcceptingNewSolution){
            return false;
        }else{
            return true;
        }
    }
}
