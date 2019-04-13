package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.distanceMethods;

public class ManhattenDistance implements DistanceMethod {
    public ManhattenDistance() {
        System.out.println("ManhattenDisence instance has been created");
    }

    @Override
    public double computeDistance(double[] firstArray, double[] secondArray) {
        double temp=0;
        // starting from the second variable as to the assumption that the first one is an ID
        for (int variableToCompute =1; variableToCompute<firstArray.length; variableToCompute ++ ){
            temp +=Math.abs(firstArray[variableToCompute]-secondArray[variableToCompute]);
        }
        return temp;
    }
}
