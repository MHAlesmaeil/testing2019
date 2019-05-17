package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.SimulatedAnnealingMMC;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.SimulatedAnnealingSMC;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.clusteringMethods.SingleLinkage;
import com.opencsv.CSVReader;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParametersReader {
    public void run() throws Exception{

        List<String[]> listOfPoints = null;
        /*System.out.println("Welcome to Cluster Analysis");
        System.out.println("You can enter '-1' or type 'Stop' to stop the processes");
        System.out.println("Please enter the path to the data-file (e.g. C:/Java/xxx.csv");*/
        boolean stopRunning = false;

        while (stopRunning==false){
            System.out.println("Enter 1 to read the files from the default folder or 0 to enter the path manually");
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine();
            if (inputString.equals("1")){
                // read the file fromt he path C:/Java/points.csv and C:/Java/parameters.csv
                String filePath = "c:/java/points.csv";
                String parameterPath = "C:/Java/parameters.csv";
                if (checkPath(filePath)){
                    listOfPoints= execute(filePath);
                    System.out.println("File 'Points' has been found and accepted. Total number of points is: "+ (listOfPoints.size()-1));
                    if (checkPath(parameterPath)){
                       List<String[]> listOfParameters = execute(parameterPath);
                        System.out.println("File 'Parameters' has been found and total number of read parameters is "+ (listOfParameters.size()-1));

                        executreOrderInParametersFile(listOfParameters,listOfPoints);


                    }else{
                        System.out.println("File 'Parameters' was not found. Please check again in 'C:/Java/Parameters'");
                    }
                }else{
                    System.out.println("File 'Points' was not found. Please check again in 'C:/Java/Points.csv'");
                }

            }else if (inputString.equals("0")){
                System.out.println("Please enter the path to the dataset. The file extension must '.csv'");
                inputString = scanner.nextLine();
                if (inputString.equals("-1")||inputString.toLowerCase().contentEquals("stop")){
                    stopRunning = true;
                    break;
                }
                if (checkPath(inputString)){
                    System.out.println("Path is correct and has been accepted");
                    listOfPoints= execute(inputString);
                    if (listOfPoints.size()!=0){
                        System.out.println("File has been read and number of points is: "+ (listOfPoints.size()-1));
                        System.out.println("Please enter the path to the parameters file");
                        inputString = scanner.nextLine();
                        if (checkPath(inputString)){
                            List<String[]> listOfParameters = execute(inputString);
                            System.out.println("Parameters file has been read");
                            executreOrderInParametersFile(listOfParameters,listOfPoints);

                        }else{
                            System.out.println("Parameters file is not compatible ");
                        }
                    }else{
                        System.out.println("There is no points in the selected file");
                    }
                }else{
                    System.out.println("Path is not correct, please try again");
                }
            }else{
                System.out.println("Invalid choice, please try again");
            }
        }
    }


    public List<String[]> execute(String pathToDataFile)throws Exception {
        Reader reader1 = Files.newBufferedReader(Paths.get(pathToDataFile));
        CSVReader csvReader = new CSVReader(reader1);
        List<String[]> listOfStrings = new ArrayList<>();
        listOfStrings = csvReader.readAll();
        reader1.close();
        csvReader.close();
        return listOfStrings;

    }
    public boolean checkPath(String pathToDataFile)throws Exception{

        String SAMPLE_CSV_FILE_PATH = pathToDataFile;
        if (!SAMPLE_CSV_FILE_PATH.endsWith(".csv")){
            System.out.println("The file-extension is not '.csv'");
            return false;
        }else{
            try {
                Reader reader1 = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader1);
                boolean temp= csvReader.verifyReader();
                return temp;
            }catch (Exception e){
                return false;
            }
        }
    }
    public void printOutParatmers(List<String []> listOfParametersToBePrintout){
        System.out.println("The Next Parameter were given and will be applied");
        for (int x= 1; x<listOfParametersToBePrintout.size();x++){
            System.out.println(x+"- "+listOfParametersToBePrintout.get(x)[0] +" is set to "+listOfParametersToBePrintout.get(x)[1] );
        }
    }
    public void executreOrderInParametersFile(List<String[]> listOfParameters, List<String[]> listOfPoints)throws Exception{
        printOutParatmers(listOfParameters);
        SimulatedAnnealingMMC simulatedAnnealingMMC = new SimulatedAnnealingMMC();
        SimulatedAnnealingSMC simulatedAnnealingSMC = new SimulatedAnnealingSMC();
        SingleLinkage singleLinkage= new SingleLinkage();
        int clusteringMethod = new Integer(listOfParameters.get(1)[1]);
        int distanceMehods = new Integer(listOfParameters.get(3)[1]);
        int numberOfClusters = new Integer(listOfParameters.get(2)[1]);
        boolean showResultInConsole = new Boolean(listOfParameters.get(10)[1]);
        int numberOfIteration = new Integer(listOfParameters.get(4)[1])*listOfPoints.size();
        double acceptanceTemperature = new Double(listOfParameters.get(5)[1]).doubleValue();
        double initialTemperature = new Double(listOfParameters.get(6)[1]).doubleValue();
        double mutationFactor = new Double(listOfParameters.get(9)[1]).doubleValue();
        double finalTemperature = new Double(listOfParameters.get(7)[1]).doubleValue();
        double alphaValue = new Double(listOfParameters.get(8)[1]).doubleValue();
        int numberOfIterationPerGivenTemperature = new Integer(listOfParameters.get(11)[1]);
        if (clusteringMethod==1){
            // execute single linkage
            System.out.println("Single Linkage is executing");

            singleLinkage.computeCluster(numberOfClusters,listOfPoints,distanceMehods,showResultInConsole);
        }else if (clusteringMethod ==2){
            // execute simulated annealing SMC
            System.out.println("Simulated Annealing SMC is executing");

            simulatedAnnealingSMC.computeCluster(numberOfClusters,listOfPoints,numberOfIteration, acceptanceTemperature,mutationFactor,showResultInConsole);
        }else if (clusteringMethod ==3 ){
            // execute simulated annealing MMC
            System.out.println("Simulated Annealing MMC is executing");

            simulatedAnnealingMMC.computeCluster(numberOfClusters,listOfPoints,initialTemperature,finalTemperature,alphaValue,mutationFactor,numberOfIterationPerGivenTemperature,showResultInConsole);
        }else{
            System.out.println("All methods will be executed");
            System.out.println("Single Linkage is executing");
            singleLinkage.computeCluster(numberOfClusters,listOfPoints,distanceMehods,showResultInConsole);
            System.out.println("Simulated Annealing SMC is executing");
            simulatedAnnealingSMC.computeCluster(numberOfClusters,listOfPoints,numberOfIteration, acceptanceTemperature,mutationFactor,showResultInConsole);
            System.out.println("Simulated Annealing MMC is executing");
            simulatedAnnealingMMC.computeCluster(numberOfClusters,listOfPoints,initialTemperature,finalTemperature,alphaValue,mutationFactor,numberOfIterationPerGivenTemperature,showResultInConsole);
        }
    }

























}
