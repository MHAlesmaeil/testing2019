package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import java.util.List;
import java.util.Random;

public class SimulatedAnnealingMethods {
    /**
     * Generate the initial center
    */
    public double[] clusterCenterInitialization(List<Cluster> ListOfClsuters,List<double[]> listOfNormalizedPoints, boolean showResultInConsole){
        // number of attribute within a single point in the list. -1 is specified as the point has also a key #
        int numberOfAttribute = listOfNormalizedPoints.get(0).length-1;
        double[] generatedCenter = new double[numberOfAttribute];
        // Generate the first center
        for (int x = 0; x<numberOfAttribute;x++){
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
        System.out.println("New Centers have been generated");
    }
}
