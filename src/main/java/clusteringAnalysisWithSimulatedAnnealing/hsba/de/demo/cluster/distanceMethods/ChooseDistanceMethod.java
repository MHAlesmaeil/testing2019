package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods;

public class ChooseDistanceMethod {

    // in the implementation class, a new created instance can be set to this method
    public DistanceMethod distenceMethodSwitcher(int chosenMethod){
        DistanceMethod distanceMethod = null;
        if (chosenMethod==1){
            distanceMethod = new EuclideanDistance();
        }
        else if(chosenMethod==2){
            distanceMethod = new ManhattanDistance();
        }
        return distanceMethod;

    }
}
