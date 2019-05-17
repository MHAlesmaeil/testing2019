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

public class FactoryClass {

    public void run() throws Exception{
        /**
         * Choose 1 for Single Linkage
         * Choose 2 for Simulated Annealing
         */
        int clusteringMethod = 2;
        /**
         * Only relevant if Single Linkage is selected
         * Choose 1 for Euclidean Distance
         * Choose 2 for Manhattan Distance
         */
        int distanceMehods = 1;
        /**
         * Choose a number, which is greater than 2
         */
        int numberOfClusters = 10;
        /**
         * Choose the Path to the file, where the data exists
         * For example: C:/Java/TestFolder/MallCustomers.csv
         */
        boolean showResultInConsole = false;
        /**
         * https://vincentarelbundock.github.io/Rdatasets/datasets.html
         */
        String pathToFileOfData = "C:/Java/TestFolder/amis.csv";
        /**
         * After reading the file of data, the points of data will be
         * Saved as a List of String arrays
         */

        int numberOfIteration = 8230;

        double acceptanceTemperature = 0.001;

        double initialTemperature;
        double finalTemperature;
        double alphaValue;
        int numberOfIterationPerGivenTemperature;

        List<String[]> listOfPoints = null;
        System.out.println("Welcome to Cluster Analysis");
        System.out.println("You can enter '-1' or type 'Stop' to stop the processes");
        System.out.println("Please enter the path to the data-file (e.g. C:/Java/xxx.csv");
        boolean stopRunning = false;

        while (stopRunning==false){
            System.out.println("Please notice that file extension must be '.csv'");
            Scanner scanner = new Scanner(System.in);

            String inputString = scanner.nextLine();
            if (inputString.equals("-1")||inputString.toLowerCase().contentEquals("stop")){
                stopRunning = true;
                break;
            }
            if (checkPath(inputString)){
                System.out.println("Path is correct and has been accepted");
                listOfPoints= execute(inputString);
                if (listOfPoints.size()!=0){
                    System.out.println("File has been read and number of points is: "+ (listOfPoints.size()-1));
                    boolean stopRunning2 = false;
                    while (stopRunning2==false){
                        System.out.println("Enter 1 to select Single Linkage, 2 to select Simulated Annealing, type 'back' to go to the previous options or 'stop' to stop the process");
                        inputString = scanner.nextLine();
                        if (inputString.equals("-1")||inputString.toLowerCase().contentEquals("stop")){
                            stopRunning= true;
                            stopRunning2= true;
                            break;
                        }else if (inputString.toLowerCase().contentEquals("back")){
                            stopRunning2= true;
                            break;
                        }

                        if (inputString.equals("1")){
                            // Single Linkage has been selected
                            System.out.println("Single Linkage has been selected");
                            System.out.println("Enter the Number of clusters");
                            inputString = scanner.nextLine();
                            numberOfClusters = new Integer(inputString).intValue();
                            System.out.println("Enter distance method: Enter 1 select Euclidean Distance or 2 for Manhattan Distance ");
                            inputString = scanner.nextLine();
                            distanceMehods = new Integer(inputString).intValue();
                            System.out.println("Do you want to show log: Enter 'true' for yes or 'false' for no");
                            inputString = scanner.nextLine();
                            showResultInConsole = new Boolean(inputString).booleanValue();
                            SingleLinkage singleLinkage= new SingleLinkage();
                            singleLinkage.computeCluster(numberOfClusters,listOfPoints,distanceMehods,showResultInConsole);

                        }else if (inputString.equals("2")){
                            // Simulated Annealing has been selected
                            System.out.println("Simulated Annealing SMC has been selected");
                            System.out.println("Enter the Number of clusters");
                            inputString = scanner.nextLine();
                            numberOfClusters = new Integer(inputString).intValue();
                            System.out.println("Enter the Number of iteration");
                            inputString = scanner.nextLine();
                            numberOfIteration = new Integer(inputString).intValue();
                            System.out.println("Enter Acceptance Temperature: e.g. 1");
                            inputString = scanner.nextLine();
                            acceptanceTemperature = new Double(inputString).doubleValue();
                            System.out.println("Enter mutation Factor: e.g. 0.05");
                            inputString = scanner.nextLine();
                            double mutationFactor =  new Double(inputString).doubleValue();
                            System.out.println("Do you want to show log: Enter 'true' for yes or 'false' for no");
                            inputString = scanner.nextLine();
                            showResultInConsole = new Boolean(inputString).booleanValue();
                            SimulatedAnnealingSMC simulatedAnnealingSMC = new SimulatedAnnealingSMC();
                            simulatedAnnealingSMC.computeCluster(numberOfClusters,listOfPoints,numberOfIteration, acceptanceTemperature,mutationFactor,showResultInConsole);
                        }else {

                            // Simulated Annealing has been selected
                            System.out.println("Simulated Annealing MMC has been selected");
                            System.out.println("Enter the Number of clusters");
                            inputString = scanner.nextLine();
                            numberOfClusters = new Integer(inputString).intValue();
                            System.out.println("Enter Initial Temperature");
                            inputString = scanner.nextLine();
                            initialTemperature = new Double(inputString).doubleValue();

                            System.out.println("Enter Final Temperature Temperature");
                            inputString = scanner.nextLine();
                            finalTemperature = new Double(inputString).doubleValue();

                            System.out.println("Enter AlphaValue");
                            inputString = scanner.nextLine();
                            alphaValue = new Double(inputString).doubleValue();

                            System.out.println("Enter the Number of Iteration per Given Temperature");
                            inputString = scanner.nextLine();
                            numberOfIterationPerGivenTemperature = new Integer(inputString).intValue();

                            System.out.println("Enter mutation Factor: e.g. 0.05");
                            inputString = scanner.nextLine();
                            double mutationFactor =  new Double(inputString).doubleValue();
                            System.out.println("Do you want to show log: Enter 'true' for yes or 'false' for no");
                            inputString = scanner.nextLine();
                            showResultInConsole = new Boolean(inputString).booleanValue();
                            SimulatedAnnealingMMC simulatedAnnealingMMC = new SimulatedAnnealingMMC();
                            simulatedAnnealingMMC.computeCluster(numberOfClusters,listOfPoints,initialTemperature,finalTemperature,alphaValue,mutationFactor,numberOfIterationPerGivenTemperature,showResultInConsole);
                        }

                    }
                }else{
                    System.out.println("There is no points in the selected file");
                }
            }else{
                System.out.println("Path is not correct, please try again");

            }
        }
    }


    public List<String[]> execute(String pathToDataFile)throws Exception {
        String SAMPLE_CSV_FILE_PATH = pathToDataFile;
        Reader reader1 = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
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
}
