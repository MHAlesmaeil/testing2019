package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods;

/**
 * Class Description:
 * ChooseDistanceMethod class is a switcher for creating either Euclidean Distance instance or Manhattan Distance
 */
public class ChooseDistanceMethod {

    // in the implementation class, a new created instance can be set to this method
    public DistanceMethod distenceMethodSwitcher(int chosenMethod){
        DistanceMethod distanceMethod = null;
        // if 1, create an Euclidean Distance instance
        if (chosenMethod==1){
            distanceMethod = new SquaredEuclideanDistance();
        }
        // if 2, create a Manhattan Distance
        else if(chosenMethod==2){
            distanceMethod = new ManhattanDistance();
        }else{
            // else, create Euclidean Distance as default method
            distanceMethod = new SquaredEuclideanDistance();
        }
        return distanceMethod;
    }
}
