package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.ChooseDistanceMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.DistanceMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataPreperation.DataProcessing;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Description:
 * GeneralMethods class has the main methods which used to implement Single Linkage (Section One) as well as some methods which also have been used while implementing Simulated Annealing (Section two)
 */
public class GeneralMethods {
    /**
     * xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
     * Section One
     * xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    */

    /**
     * This method checks a list of arrays of double and return an array of double, which has the least SSE value, when it compared with the rest of array of double within the list
     *
     * Distance Method: 1 for Euclidean Distance and 2 for Manhattan Distance
     */
    public double [] indexWithLeastValue(List<double[]> listToCheck, int distanceMethodNumber, boolean showResultInConsole){
        DistanceMethod distanceMethod = new ChooseDistanceMethod().distenceMethodSwitcher(distanceMethodNumber);
        double [] temp = new double[listToCheck.size()];
        double oldValue = -1;
        double newValue;
        // loop through the list of items
        for (int x = 0; x<listToCheck.size(); x++){
            for (int y = x+1; y<listToCheck.size();y++){
                newValue = distanceMethod.computeDistance(listToCheck.get(x),listToCheck.get(y));

                if (oldValue==-1){
                    // first solution
                    oldValue = newValue;
                    temp = new double[] {new Double(x), oldValue, new Double(y)};
                }else{
                    if (newValue<oldValue){
                        // update solution
                        oldValue = newValue;
                        temp = new double[] {new Double(x), oldValue, new Double(y)};
                    }
                }
            }
        }
        if (showResultInConsole==true){
            System.out.println("The index in the list "+ temp[0]);
            System.out.println("The Least value "+ temp[1]);
        }
        return temp;
    }
    /**
     * The main method in the Single Linkage
     * This method checks a list of arrays of double and decide which array from this list will be injected into which cluster and delete it from the list of arrays of double
     */
    // return an array with the element index and the cluster number
    public List<Cluster> nextPointAndItsClusterAddAndDelete(List<Cluster> listOfClusters, List<double[]> listOfPoints, int numberOfCluster, int distanceMethodNumber, boolean showResultInConsole){
        DataProcessing dataProcessing = new DataProcessing();
        // create empty clusters
        dataProcessing.setCreatedClusters(numberOfCluster);
        // first numer im temp refer to the cluster and the second one refer to the point, which need to be clustered
        // for example [0,199]: it means the point 199 needs to be clustered in cluster number 0

        DistanceMethod distanceMethod = new ChooseDistanceMethod().distenceMethodSwitcher(distanceMethodNumber);
        // if cluster size is reached search in the current one
        if (areAllClustersHavingAtLeastOnePoint(listOfClusters,showResultInConsole)==true){
            double [] temp = whichPointToWhichClusterPlusFuncValue(listOfClusters,listOfPoints,distanceMethodNumber,showResultInConsole);
            int [] key = {new Double(temp[0]).intValue(),new Double(temp[1]).intValue()};
            addPointToAClusterAndDeleteFromList(key,listOfClusters,listOfPoints,showResultInConsole);
            if (showResultInConsole==true){
                System.out.println("No empty cluster");
            }
        }
        // if cluster size is not reached, check if you need to let the cluster join an exists one or a new one
        else {

            double bestValueCluster= whichPointToWhichClusterPlusFuncValue(listOfClusters,listOfPoints,1, showResultInConsole)[2];
            double bestValueList=indexWithLeastValue(listOfPoints,1, showResultInConsole)[0];
            /**
             * In the first iteration when no cluster has no points, value of bestValueCluster is 0
             * which would cause of clustering the first point in a cluster, it would
             * not be probably fitting the single linkage method
             */
            if (areAllClusterHavingNoPointsAtAll(listOfClusters,showResultInConsole)==true){
                bestValueCluster = 10*bestValueList;
            }
            // check which one gives the least value
            // if least value resulted from an item with in cluster, then repeat if statement
            if (bestValueCluster<bestValueList || bestValueCluster==bestValueList){
                double [] temp = whichPointToWhichClusterPlusFuncValue(listOfClusters,listOfPoints,distanceMethodNumber, showResultInConsole);
                int [] key = {new Double(temp[0]).intValue(),new Double(temp[1]).intValue()};
                if (showResultInConsole==true){
                    System.out.println("point "+ listOfPoints.get(new Double(temp[1]).intValue())[0] +"added to cluster "+ temp[0]);
                }
                addPointToAClusterAndDeleteFromList(key,listOfClusters,listOfPoints,showResultInConsole);
            }else{
                // find the next empty cluster and add two points at once
                double sometin = indexWithLeastValue(listOfPoints,1, showResultInConsole)[1];
                int into = new Double(sometin).intValue();
                int nextEmptyCluster = nextEmptyCluser(listOfClusters,showResultInConsole);
                int [] keyForAddingX ={nextEmptyCluster,new Double(indexWithLeastValue(listOfPoints,1, showResultInConsole)[0]).intValue()};
                addPointToAClusterAndDeleteFromList(keyForAddingX,listOfClusters,listOfPoints,showResultInConsole);
                if (showResultInConsole==true){
                    System.out.println("point is added to a new one");
                }
            }
        }
        return dataProcessing.getCreatedClusters();

    }

    /**
     * This method to check if there is no points in any cluster
     * to generate the first solution from comparing the list of arrays of double
     */
    public boolean areAllClusterHavingNoPointsAtAll(List<Cluster> listOfClusters, boolean showResultInConsole) {
        boolean temp = true;
        for (int x =0; x<listOfClusters.size();x++){
            if (listOfClusters.get(x).getClusterPoints().size()!=0){
                temp = false;
                break;
            }
        }
        if (showResultInConsole == true){
            if (temp==true){
                System.out.println("All clusters have no points Value "+ temp);
            }else {
                System.out.println("At least on cluster has points. Value "+ temp);
            }
        }
        return temp;
    }
    /**
     * This method return the next empty cluster. Empty here means that the cluster has no points
     * If the boolean showResultInConsole is true, then the result will be shown in the console
     */
    public int nextEmptyCluser(List<Cluster> listOfClusters, boolean showResultInConsole){
        Integer temp = null;
        // loop through the clusters and return the first empty cluster
        for (int x =0; x<listOfClusters.size();x++){
            if (listOfClusters.get(x).getClusterPoints().size()==0){
                temp = x;
                break;
            }
        }
        if (showResultInConsole==true){
            System.out.println("Next empty cluster is "+ temp);
        }
        return temp;
    }
    /**
     * This method adds an array to a cluster from the list of arrays of double
     * If the boolean showResultInConsole is true, then the result will be shown in the console
     */
    public void addPointToAClusterAndDeleteFromList(int[] keyClusterAndPoint, List<Cluster> listOfClusters,List<double[]> listOfPoints, boolean showResultInConsole){
        int [] keyCluster = keyClusterAndPoint;
        int clusterNumber = keyCluster[0];
        int pointNumber = keyCluster[1];
        int sizeOfListBeforeDeletingAnElement = listOfPoints.size();
        int numberOfPointsBeforeAdding = listOfClusters.get(clusterNumber).getClusterPoints().size();
        listOfClusters.get(clusterNumber).addPointToCluster(listOfPoints.get(pointNumber));
        listOfPoints.remove(listOfPoints.get(pointNumber));
        if (showResultInConsole == true){
            System.out.println("Point # "+pointNumber +" has been successfully added to cluster " + clusterNumber + " and deleted from the original list");
            System.out.println("Size of the list before deletion is "+ sizeOfListBeforeDeletingAnElement+ " and after deletion is " + listOfPoints.size());
            System.out.println("Number of Points in cluster "+ clusterNumber + " before adding a new point " + numberOfPointsBeforeAdding + " and after adding the new point " +listOfClusters.get(clusterNumber).getClusterPoints().size() );
        }
    }
    /**
     * This Method decides which array within list of arrays of double must injected in which cluster
     * If the boolean showResultInConsole is true, then the result will be shown in the console
     */
    public double [] whichPointToWhichClusterPlusFuncValue(List<Cluster> listOfClusters,List<double[]> listOfPoints, int distanceMethodNumber, boolean showResultInConsole){
        DistanceMethod distanceMethod = new ChooseDistanceMethod().distenceMethodSwitcher(distanceMethodNumber);
        // initiate the variables
        double oldValue = -1;
        double newValue;
        // first numer im temp refer to the cluster and the second one refer to the point, which need to be clustered
        // for example [0,199]: it means the point 199 needs to be clustered in cluster number 0
        double [] temp = new double[listOfPoints.size()];

        // loop through listOfPoints
        for (int x = 0; x<listOfPoints.size();x++){
            // Loop through the list of clusters
            for (int y = 0; y<listOfClusters.size();y++){
                // loop through the points those within a cluster
                for (int z=0; z<listOfClusters.get(y).getClusterPoints().size();z++){
                    // calculate the value
                    newValue = distanceMethod.computeDistance(listOfPoints.get(x), listOfClusters.get(y).getClusterPoints().get(z));
                    if (oldValue==-1){
                        // first solution
                        oldValue = newValue;
                        temp = new double[]{y,x, oldValue};

                    }else{
                        if (newValue<oldValue){
                            // update solution
                            oldValue = newValue;
                            temp = new double[]{y,x, oldValue};
                        }
                    }
                }
            }
            if (oldValue==0){
                break;
            }
        }
        if (showResultInConsole==true){
            System.out.println("Cluster # "+temp[0]);
            System.out.println("Point index in the list "+temp[1]);
            System.out.println("Value of function "+temp[2]);
        }
        return temp;
    }

    /**
     * xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
     * Section Two
     * xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
     */
    /**
     * This method returns an array of double with the greatest value and smallest value of each column and it helps to normalize the list of arrays of double
     * If the boolean showResultInConsole is true, then the result will be shown in the console
     */
    public List<double[]> smallestAndGreatestValues(List<double[]> listOfDouble, boolean showResultInConsole){
        List<double[]> temp = new ArrayList<>();
        // add a 0 column instead of the key of elements
        temp.add(new double[]{0,0});
        // go though each column and start from 1
        for (int x =1;x<listOfDouble.get(0).length;x++){
            double smallestValue =0;
            double greatestValue =0;
            // loop through the elements of the column
            for (int y=0; y<listOfDouble.size();y++){
                double tempoo = listOfDouble.get(y)[x];
                if (y==0){
                    // first solution
                    smallestValue = listOfDouble.get(y)[x];
                    greatestValue = listOfDouble.get(y)[x];

                }else{
                    if (tempoo<smallestValue){
                        // update the smallest value
                        smallestValue = tempoo;
                    }else if (tempoo>greatestValue){
                        // update the greatest value
                        greatestValue = tempoo;
                    }
                }
            }
            // build an array from the least and greatest value
            double[] tempArray = {smallestValue,greatestValue};
            // add it to the list of double array
            temp.add(tempArray);
        }
        // show the result
        if (showResultInConsole==true){
            for (int x =0; x<temp.size(); x++){
                System.out.println("Column "+x);
                System.out.println("Smallest value is "+ temp.get(x)[0]);
                System.out.println("Greatest value is "+ temp.get(x)[1]);
            }
        }


        return temp;
    }
    /**
     * This method returns the normalized value (between 0 and 1) of a double after giving the greatest and smallest value
     */
    public double normalizeMe(double smallestValue, double greatestValue, double doubleToBeNormalized){
        double temp = 0;
        temp = (doubleToBeNormalized-smallestValue)/(greatestValue-smallestValue);
        return temp;
    }
    /**
     * This methods converts a list with double arrays to a normalized one
     * If the boolean showResultInConsole is true, then the result will be shown in the console
     */
    public List<double[]> normalizeDoubleList(List<double[]> listToBeNormalized, boolean showResultInConsole){
        List<double[]> temp = new ArrayList<>();
        List<double[]> smallAndGreatestValuesList = smallestAndGreatestValues(listToBeNormalized,showResultInConsole);
        // loop through the elements
        for (int x=0; x<listToBeNormalized.size();x++){
            // loop through columns
            double [] elemetToBeaddedToTheArray = new double[listToBeNormalized.get(x).length];
            for (int y = 0; y<listToBeNormalized.get(x).length;y++){
                double smallestValue = smallAndGreatestValuesList.get(y)[0];
                double greatestValue = smallAndGreatestValuesList.get(y)[1];
                if (y==0){
                    // for the first column, copy it as no changes are needed
                    elemetToBeaddedToTheArray[y] = listToBeNormalized.get(x)[y];
                }else{
                    // normlize the value through the function
                    elemetToBeaddedToTheArray[y] = normalizeMe(smallestValue,greatestValue,listToBeNormalized.get(x)[y]);
                }
            }
            temp.add(elemetToBeaddedToTheArray);
        }
        // Loop through the list for test purposes
        if (showResultInConsole== true){
            for (int x = 0; x<temp.size();x++){
                System.out.println("Point # "+ temp.get(x)[0]);
                // loop through each element in the arrary
                for (int y = 1; y<temp.get(x).length;y++){
                    System.out.print(temp.get(x)[y]+",");
                }
                System.out.println("__");
            }
        }

        return temp;
    }
    /**
     * This Method shows the formed clusters in the console if the boolean showResultInConsole is true.
     */
    public void showFormedClusters (List<Cluster> listOfClusters){
        ClusterSSE clusterSSE = new ClusterSSE();
        double totalSSE =0;
        if (true){
            // loop throug the clusters
            for (int x = 0; x<listOfClusters.size();x++){
                if (listOfClusters.get(x).getClusterPoints().size()==0 ){
                    System.out.println("__________");
                    System.out.println("##Cluster number "+ x + " has no points to be shown");
                }else if(listOfClusters.get(x).getClusterPoints().size()==1){
                    System.out.println("__________");
                    System.out.println("##Cluster number "+ x + " has just one point");
                    for (int y=0; y<listOfClusters.get(x).getClusterPoints().size(); y++){
                        System.out.print(new Double(listOfClusters.get(x).getClusterPoints().get(y)[0]).intValue()+", ");
                    }


                    System.out.println();
                }
                else{
                    totalSSE += clusterSSE.computeSSE(listOfClusters.get(x).getClusterPoints());
                    System.out.println("__________");
                    System.out.println("##Cluster number "+ x + " and its SSE value is "+ clusterSSE.computeSSE(listOfClusters.get(x).getClusterPoints()));
                    // loop through each point within each cluster
                    for (int y=0; y<listOfClusters.get(x).getClusterPoints().size(); y++){
                        System.out.print(new Double(listOfClusters.get(x).getClusterPoints().get(y)[0]).intValue()+", ");
                    }
                    System.out.println();

                }
            }
            System.out.println("\n");
            System.out.println("################################");
            System.out.println("Total SSE is " + totalSSE);
        }
    }
    public List<Cluster> normalizeListOfClusters (List<Cluster> listOfClusters){
        List<Cluster> tempList= listOfClusters;
        List<double[]> listOfDouble = new ArrayList<>();
        // loop through the clusters and the points to the list of points
        for (int x=0; x<tempList.size();x++){
            listOfDouble.addAll(listOfClusters.get(x).getClusterPoints());
        }
        listOfDouble = normalizeDoubleList(listOfDouble,false);
        // loop through each cluster and then each point to replaced with a normlized value
        for (int x=0; x<tempList.size();x++){
            for (int y =0; y<tempList.get(x).getClusterPoints().size();y++){
                // through the key value add and replace each point in teh cluster with a normalized one from the normalize one
                double pointId = tempList.get(x).clusterPoints.get(y)[0];
                // remove the point from the cluster


            }
        }
        return null;

    }
    /**
     * This function checks if all created clusters have at least one point
     * If the boolean showResultInConsole is true, then the result will be shown in the console
     */
    public boolean areAllClustersHavingAtLeastOnePoint(List<Cluster> listOfClusters, boolean showResultInConsole ){
        boolean temp = true;
        for (int x =0; x<listOfClusters.size();x++){
            if (listOfClusters.get(x).getClusterPoints().size()==0){
                temp = false;
                break;
            }
        }
        if (showResultInConsole == true){
            if (temp==true){
                System.out.println("All clusters have at least one point. Value "+ temp);
            }else {
                System.out.println("At least on cluster has no points. Value "+ temp);
            }
        }
        return temp;
    }
    /**
     * This method convert a list of array of strings and return a list of array of double
     */
    public List<double[]> dataSetStringToDoubleWithoutHeaders(List<String[]> dataSetToBeConverted, boolean showResultInConsole ) throws Exception{
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
        // the next line need to be uncommented if a normalized values are wished
        //temp = normalizeDoubleList(temp,showResultInConsole );
        return temp;
    }

}
