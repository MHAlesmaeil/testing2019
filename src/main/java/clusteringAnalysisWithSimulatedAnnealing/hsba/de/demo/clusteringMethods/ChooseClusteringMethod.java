package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.clusteringMethods;

public class ChooseClusteringMethod {

    // in the implementation class, a new created instance can be set to this method
    public ClusteringMethod distenceMethodSwitcher(int chosenMethod){
        ClusteringMethod clusteringMethod = null;
        if (chosenMethod==1){
            clusteringMethod = new SingleLinkage();
        }
        else if(chosenMethod==2){
            clusteringMethod = new SimulatedAnnealing();
        }
        return clusteringMethod;

    }
}
