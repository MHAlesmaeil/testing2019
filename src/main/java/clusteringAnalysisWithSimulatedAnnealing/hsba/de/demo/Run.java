package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.SimulatedAnnealing;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.SingleLinkage;

import java.util.List;

public class Run {
    public static void main(String[] args) throws Exception {

        /**
         * Choose 1 for Single Linkage
         * Choose 2 for Simulated Annealing
        */
        int clusteringMethod = 1;
        /**
         * Only relevant if Single Linkage is selected
         * Choose 1 for Euclidean Distance
         * Choose 2 for Manhattan Distance
        */
        int distanceMehods = 1;
        /**
         * Choose a number, which is greater than 2
        */
        int numberOfClusters = 10;
        /**
         * Choose the Path to the file, where the data exists
         * For example: C:/Java/TestFolder/MallCustomers.csv
        */
        boolean showResultInConsole = false;
        /**
         * https://vincentarelbundock.github.io/Rdatasets/datasets.html
        */
        String pathToFileOfData = "C:/Java/TestFolder/mallcustomers.csv";
        /**
         * After reading the file of data, the points of data will be
         * Saved as a List of String arrays
        */

        int numberOfIteration = 2000;

        double acceptanceTemperature = 0.001;

        List<String[]> listOfPoints = new FactoryClass().execute(pathToFileOfData);


        if (clusteringMethod ==1){
            SingleLinkage singleLinkage= new SingleLinkage();
            singleLinkage.computeCluster(numberOfClusters,listOfPoints,distanceMehods,showResultInConsole);
        } else if (clusteringMethod ==2){
            // Implement Simulated Annealing
            SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
            simulatedAnnealing.computeCluster(numberOfClusters,listOfPoints,numberOfIteration, acceptanceTemperature,0.05,false);
        }
    }
}
