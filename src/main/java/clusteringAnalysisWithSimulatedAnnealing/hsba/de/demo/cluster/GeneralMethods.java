package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.ChooseDistanceMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.DistanceMethod;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing.DataProcessing;

import java.util.ArrayList;
import java.util.List;

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
        // here, we need an array, which would save the smallest as well as the greatest number within a column
        // loop through each column starting from the number one, as the first number is key
        List<Double[]> listOfSmallestAndGreatestValues= new ArrayList<>();
        // By each double array, the first value will be the smallest values whereas the second one is the greatest
        for (int normalizing = 1; normalizing<temp.get(1).length;normalizing++){
            // loop through the list of element
            Double greatestValue=null; Double smallestValue = null;
            for (int listOfDouble = 0; listOfDouble<temp.size(); listOfDouble++){
                // loop through each element in the double array and check if
                if (normalizing==1 && listOfDouble==0){
                    // first solution
                    greatestValue = temp.get(listOfDouble)[normalizing];
                    smallestValue = temp.get(listOfDouble)[normalizing];
                }else{
                    Double temporal = temp.get(listOfDouble)[normalizing];
                    if (temporal<smallestValue){
                        // update the smallest value
                        smallestValue = temporal;
                    }else if (temporal>greatestValue){
                        // update the geatest value
                        greatestValue = temporal;
                    }
                }

            }
            Double[] tempo= {smallestValue,greatestValue};
            listOfSmallestAndGreatestValues.add(tempo);
            System.out.println("show Reault");
            // loop through list of smallest and greated values
            for (int x =0;x<listOfSmallestAndGreatestValues.size();x++){
                System.out.println("Column number "+ x);
                System.out.println("Smallest Value is "+ listOfSmallestAndGreatestValues.get(x)[0]);
                System.out.println("Greatest Value is "+ listOfSmallestAndGreatestValues.get(x)[1]);
            }
        }
        temp = normalizeDoubleList(temp);
        return temp;
    }
    // passed
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
                    temp = new double[] {new Double(x), oldValue};
                }else{
                    if (newValue<oldValue){
                        // update solution
                        oldValue = newValue;
                        temp = new double[] {new Double(x), oldValue};
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

    // return an array with the element index and the cluster number
  public List<Cluster> nextPointAndItsClusterAddAndDelete(List<Cluster> listOfClusters, List<double[]> listOfPoints, int numberOfCluster, int distanceMethodNumber, boolean showResultInConsole) throws Exception{
        DataProcessing dataProcessing = new DataProcessing();
        // create empty clusters

        // first numer im temp refer to the cluster and the second one refer to the point, which need to be clustered
        // for example [0,199]: it means the point 199 needs to be clustered in cluster number 0

        DistanceMethod distanceMethod = new ChooseDistanceMethod().distenceMethodSwitcher(distanceMethodNumber);
        // if cluster size is reached search in the current one
        if (allClustersHaveAtLeastOnePoint(listOfClusters,showResultInConsole)==true){
            double [] temp = whichPointToWhichClusterPlusFuncValue(listOfClusters,listOfPoints,distanceMethodNumber, showResultInConsole);
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
            // check which one gives the least value
            // if least value resulted from an item with in cluster, then repeat if statement
            if (bestValueCluster<bestValueList || bestValueCluster==bestValueList){
                double [] temp = whichPointToWhichClusterPlusFuncValue(listOfClusters,listOfPoints,distanceMethodNumber, showResultInConsole);
                int [] key = {new Double(temp[0]).intValue(),new Double(temp[1]).intValue()};
                addPointToAClusterAndDeleteFromList(key,listOfClusters,listOfPoints,showResultInConsole);
                if (showResultInConsole==true){
                    System.out.println("point added to an exists cluster");
                }
            }else{
                // find the next empty cluster
                double sometin = indexWithLeastValue(listOfPoints,1, showResultInConsole)[1];
                int into = new Double(sometin).intValue();
                int [] keyForAdding ={nextEmptyCluser(listOfClusters,showResultInConsole),new Double(indexWithLeastValue(listOfPoints,1, showResultInConsole)[0]).intValue()};
                addPointToAClusterAndDeleteFromList(keyForAdding,listOfClusters,listOfPoints,showResultInConsole);
                if (showResultInConsole==true){
                    System.out.println("point is added to a new one");
                }
            }
        }
        return dataProcessing.getClusters();

    }
    public void showFormedClusters (List<Cluster> listOfClusters, boolean showResultInConsole){
      if (showResultInConsole==true){
          // loop throug the clusters
          for (int x = 0; x<listOfClusters.size();x++){
              System.out.println("##Cluster number "+ x + " and points' keys are:");
              // loop through each point within each cluster
              for (int y=0; y<listOfClusters.get(x).getClusterPoints().size(); y++){
                  System.out.print(new Double(listOfClusters.get(x).getClusterPoints().get(y)[0]).intValue()+", ");
              }
              System.out.println();
              System.out.println("__________");
          }
      }
    }
    // passed
    public boolean allClustersHaveAtLeastOnePoint(List<Cluster> listOfClusters,boolean showResultInConsole ){
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
    // passed : assumption is that at least one cluster is empty
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
    // passed
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
    // passed
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

    public List<double[]> smallestAndGreatestValues(List<double[]> listOfDouble){
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
                    System.out.println("First Solution is "+ listOfDouble.get(y)[x]);
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
        for (int x =0; x<temp.size(); x++){
            System.out.println("Column "+x);
            System.out.println("Smallest value is "+ temp.get(x)[0]);
            System.out.println("Greatest value is "+ temp.get(x)[1]);
        }


        return temp;
    }
    public double normalizeMe(double smallestValue, double greatestValue, double doubleToBeNormalized){
        double temp = 0;
        temp = (doubleToBeNormalized-smallestValue)/(greatestValue-smallestValue);
        return temp;
    }
    public List<double[]> normalizeDoubleList(List<double[]> listToBeNormalized){
        List<double[]> temp = new ArrayList<>();
        List<double[]> smallAndGreatestValuesList = smallestAndGreatestValues(listToBeNormalized);
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
        for (int x = 0; x<temp.size();x++){
            System.out.println("Point # "+ temp.get(x)[0]);
            // loop through each element in the arrary
            for (int y = 1; y<temp.get(x).length;y++){
                System.out.print(temp.get(x)[y]+",");
            }
            System.out.println("__");
        }

        return temp;
    }
}
