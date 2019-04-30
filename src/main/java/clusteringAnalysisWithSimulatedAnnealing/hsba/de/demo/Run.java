package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.ChooseClusteringMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.ClusteringMethod;

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
        int numberOfClusters =3;
        /**
         * Choose the Path to the file, where the data exists
         * For example: C:/Java/TestFolder/MallCustomers.csv
        */
        boolean showResultInConsole = true;
        String pathToFileOfData = "C:/Java/TestFolder/MallCustomers.csv";
        /**
         * After reading the file of data, the points of data will be
         * saved as a List of String arrays
        */
        List<String[]> listOfPoints = new FactoryClass().execute(pathToFileOfData);

        ClusteringMethod clusteringMethod1 = new ChooseClusteringMethod().distenceMethodSwitcher(clusteringMethod);

        if (clusteringMethod ==1){
            clusteringMethod1.computeCluster(numberOfClusters,listOfPoints,distanceMehods,showResultInConsole);
        } else if (clusteringMethod ==2){
            // Implement Simulated Annealing
            //clusteringMethod1.computeCluster();
        }




    }
}
