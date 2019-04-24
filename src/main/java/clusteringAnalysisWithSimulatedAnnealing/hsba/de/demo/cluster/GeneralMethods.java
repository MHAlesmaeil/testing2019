package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing.DataProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneralMethods {
    // to compute the values
    public List<double[]> dataSetStringToDoubleWithoutHeaders(List<String[]> dataSetToBeConverted ) throws Exception{
        List<double[]> temp = new ArrayList<>();
        List<String []> tempString = dataSetToBeConverted;
        // for loop to call each row in the string dataset
        // first row will be escaped as the header might not be a double
        for (int dataSetRowCounter=1; dataSetRowCounter<tempString.size(); dataSetRowCounter++){
            String [] singleRowString = tempString.get(dataSetRowCounter);
            double [] singleRowDouble = new double[singleRowString.length];
            // for loop to call every single attribute in the row and save it in the return List<double []>
            for (int singleRowStringCounter=0; singleRowStringCounter<singleRowString.length; singleRowStringCounter++){
                singleRowDouble[singleRowStringCounter]=Double.valueOf(singleRowString[singleRowStringCounter]);
            }
            temp.add(singleRowDouble);
        }
        return temp;
    }

    public List<Cluster> initialSolution(int numberOfClusters, List<String[]> dataSet) throws Exception {
        // change the List<String[]> to List<Double[]> to start the process
        List<double[]> dataSetDouble = dataSetStringToDoubleWithoutHeaders(dataSet);
        // create an instance of dataProcessing
        DataProcessing dataProcessing = new DataProcessing();
        // Set the Number of Cluster to generate the empty ones
        dataProcessing.setCreatedClusters(numberOfClusters);
        // Looping through the dataset and assign each point to one cluster randomly
        for (int x = 0; x<dataSet.size()-1; x++){
            // through a random between 1 and number of cluster and inject the point from dataset in the cluster
            int randomNum = new Random().nextInt(numberOfClusters);
            dataProcessing.getCreatedClusters().get(randomNum).addPointToCluster(dataSetDouble.get(x));
            // test
            // once a point is added, we would like to see the name of the cluster and number of assigned points
            System.out.println("cluster name is "+ dataProcessing.getCreatedClusters().get(randomNum).getClusterName()+ " and number of point of this cluster is "+ dataProcessing.getCreatedClusters().get(randomNum).getClusterPoints().size());
        }
        return dataProcessing.getCreatedClusters();
    }
    // the values of the list will be converted to new one between 0 und 1
    /*public List<Cluster> normalizeIt (List<String[]> dataSetToBeNormalized)throws Exception{
        List<double[]> dataSetDouble = dataSetStringToDoubleWithoutHeaders(dataSetToBeNormalized);

        DataProcessing dataProcessing = new DataProcessing();

    }*/
}
