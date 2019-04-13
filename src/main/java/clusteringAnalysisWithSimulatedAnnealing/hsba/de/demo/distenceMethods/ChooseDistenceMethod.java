package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.distenceMethods;

public class ChooseDistenceMethod {

    // in the implementation class, a new created instance can be set to this method
    public DistenceMethod distenceMethodSwitcher(int chosenMethod){
        DistenceMethod distenceMethod = null;
        if (chosenMethod==1){
            distenceMethod= new EuclideanDistance();
        }
        else if(chosenMethod==2){
            distenceMethod= new ManhattenDistence();
        }
        return distenceMethod;

    }
}
