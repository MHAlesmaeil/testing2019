package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.clusteringMethods;

public class ChooseClusteringMethod {

    // in the implementation class, a new created instance can be set to this method
    public ClusteringMethod distenceMethodSwitcher(int chosenMethod){
        ClusteringMethod clusteringMethod = null;
        // if 1, create a new SingleLinkage instance
        if (chosenMethod==1){
            clusteringMethod = new SingleLinkage();
        }
        // if 2, create Simulated annealing instance
        else if(chosenMethod==2){
            clusteringMethod = new SimulatedAnnealing();
        }
        return clusteringMethod;

    }
}
