package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.distanceMethods;

public class EuclideanDistance implements DistanceMethod {

    @Override
    public double computeDistance(double[] firstArray, double[] secondArray) {

        double temp=0;
        // starting from the second variable as to the assumption that the first one is an ID
        for (int variableToCompute =1; variableToCompute<firstArray.length; variableToCompute ++ ){
            temp +=Math.pow(firstArray[variableToCompute]-secondArray[variableToCompute],2);
        }
        return temp;
    }
}
