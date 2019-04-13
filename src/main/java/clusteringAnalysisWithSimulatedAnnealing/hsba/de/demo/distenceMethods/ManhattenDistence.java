package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.distenceMethods;

public class ManhattenDistence implements DistenceMethod {
    double [] something1={01,3,5,10};
    double [] something2={02,6,5,10};

    @Override
    public double computeDistance(double[] firstArray, double[] secondArray) {
        double temp=0;
        // starting from the second variable as to the assumption that the first one is an ID
        for (int variableToCompute =1; variableToCompute<firstArray.length; variableToCompute ++ ){
            temp +=firstArray[variableToCompute]-secondArray[variableToCompute];
        }
        return temp;
    }
}
