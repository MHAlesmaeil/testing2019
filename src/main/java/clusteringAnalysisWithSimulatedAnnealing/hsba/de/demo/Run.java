package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.ChooseClusteringMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.ClusteringMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.SimulatedAnnealing;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.SingleLinkage;

import java.util.List;

public class Run {
    public static void main(String[] args) throws Exception {

        /**
         * Choose 1 for Single Linkage
         * Choose 2 for Simulated Annealing
        */
        int clusteringMethod = 2;
        /**
         * Only relevant if Single Linkage is selected
         * Choose 1 for Euclidean Distance
         * Choose 2 for Manhattan Distance
        */
        int distanceMehods = 2;
        /**
         * Choose a number, which is greater than 2
        */
        int numberOfClusters =5;
        /**
         * Choose the Path to the file, where the data exists
         * For example: C:/Java/TestFolder/MallCustomers.csv
        */
        boolean showResultInConsole = false;
        String pathToFileOfData = "C:/Java/TestFolder/MallCustomers1.csv";
        /**
         * After reading the file of data, the points of data will be
         * Saved as a List of String arrays
        */

        int numberOfIteration = 100;

        double temperature = 9;

        List<String[]> listOfPoints = new FactoryClass().execute(pathToFileOfData);



        if (clusteringMethod ==1){
            SingleLinkage singleLinkage= new SingleLinkage();
            singleLinkage.computeCluster(numberOfClusters,listOfPoints,distanceMehods,showResultInConsole);
        } else if (clusteringMethod ==2){
            // Implement Simulated Annealing
            SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
            simulatedAnnealing.computeCluster(numberOfClusters,listOfPoints,numberOfIteration,temperature,4,0.5,showResultInConsole);
        }




    }
}
