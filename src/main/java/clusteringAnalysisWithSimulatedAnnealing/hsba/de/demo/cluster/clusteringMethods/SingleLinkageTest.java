package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.GeneralMethods;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods.EuclideanDistance;

import java.util.ArrayList;
import java.util.List;

public class SingleLinkageTest {
    public List<Cluster> computeCluasters(List<String[]> points) throws Exception {
        List<Cluster> tempCluster = new ArrayList<>();
        List<double[]> pointsDouble = new GeneralMethods().dataSetStringToDoubleWithoutHeaders(points);
        System.out.println("list has been created");
        EuclideanDistance euclideanDistance = new EuclideanDistance();
        // go though the list the find the one which shows the less distance



        while (pointsDouble.size()>0){
            int firstIndex= -1;
            int clusterNumber = -1;
            Double leastValuepoints=null;
            Double leastcluster=null;
            Double newValue = null;
            // Check the point against the ones in the clusters
            for (int x = 0; x<pointsDouble.size(); x++){
                System.out.println("cluster list size  "+ tempCluster.size());
                // measure against the points in the cluster, if any exists
                for (int y = 0; y<tempCluster.size();y++){
                    if (tempCluster.get(y).getClusterPoints().size()!=0){
                        // loop through the points of a cluster to
                        for (int z=0; z<tempCluster.get(y).getClusterPoints().size();z++){
                            newValue = euclideanDistance.computeDistance(pointsDouble.get(x),tempCluster.get(y).getClusterPoints().get(z));
                            if (leastcluster==null){
                                leastcluster = newValue;
                            }else {
                                if (newValue<leastcluster){
                                    // leave msg
                                    System.out.println("new Value has been found");
                                    leastcluster = newValue;
                                    // remember the index of the point in the list
                                    firstIndex = x;
                                    // rememeber the cluster number
                                    clusterNumber = y;
                                }
                            }
                        }
                    }
                }
            }

            // check the point against the other in the list
            for (int x = 0; x<pointsDouble.size(); x++){
                // measure against the points in the cluster, if any exists
                for (int y = x+1; y<pointsDouble.size(); y++){
                    if (x==0 && y==1){
                        // do first solution
                        System.out.println("First solution "+ x+" "+y+" their value "+ euclideanDistance.computeDistance(pointsDouble.get(x),pointsDouble.get(y)));
                        leastValuepoints = euclideanDistance.computeDistance(pointsDouble.get(x), pointsDouble.get(y));
                        firstIndex = x;
                    }else{
                        newValue = euclideanDistance.computeDistance(pointsDouble.get(x), pointsDouble.get(y));
                        if (newValue<leastValuepoints){
                            leastValuepoints = newValue;
                            firstIndex = x;
                            System.out.println("New solution "+ x+" "+y+" their value "+ leastValuepoints);
                        }
                    }
                }
            }
            // action
            // create first cluster if none exists
            if (clusterNumber==-1){
                Cluster cluster = new Cluster();
                cluster.addPointToCluster(pointsDouble.get(firstIndex));
                tempCluster.add(cluster);

                System.out.println("list size before removing the selected items is " + pointsDouble.size());
                pointsDouble.remove(pointsDouble.get(firstIndex));

                System.out.println("List size after removing the selected items is " + pointsDouble.size());

                System.out.println("new cluster has been created and a point has been added .. new size is" + cluster.getClusterPoints().size());
            }else{
                System.out.println("value of least cluster "+ leastcluster);
                System.out.println("value of least points "+ leastValuepoints);
                if (leastValuepoints != null){
                    if (leastcluster<leastValuepoints){
                        // add point to the cluster
                        tempCluster.get(clusterNumber).addPointToCluster(pointsDouble.get(firstIndex));
                        // remove point from the list
                        System.out.println("list size before removing the selected items is " + pointsDouble.size());
                        pointsDouble.remove(pointsDouble.get(firstIndex));
                    }else{

                        Cluster cluster1 = new Cluster();
                        cluster1.addPointToCluster(pointsDouble.get(firstIndex));
                        tempCluster.add(cluster1);
                        System.out.println("cluster size " + tempCluster.size());

                        System.out.println("list size before removing the selected items is " + pointsDouble.size());
                        pointsDouble.remove(pointsDouble.get(firstIndex));
                    }
                }else{
                    tempCluster.get(clusterNumber).addPointToCluster(pointsDouble.get(firstIndex));
                    // remove point from the list

                    pointsDouble.remove(pointsDouble.get(firstIndex));
                    System.out.println("list size after removing the selected items is " + pointsDouble.size());

                }


            }
        }
        // loop through the clusters and show its points
        for (int x= 0; x<tempCluster.size();x++){
            System.out.println("## cluster number "+ x);
            // elements of the cluster
            for (int y = 0 ; y<tempCluster.get(x).getClusterPoints().size(); y++){
                System.out.println(tempCluster.get(x).getClusterPoints().get(y)[0]+ ",");
            }
        }

        return tempCluster;
    }
}
