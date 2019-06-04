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

/**
 * This class aggregate the coded methods to be called then from the main method in the class run
*/

public class ParametersReader {

    /**
     * This is the main method in this class and the one which needed to optimize the clusters
    */
    public void run() throws Exception{
        // create an empty list
        List<String[]> listOfPoints = null;
        /*System.out.println("Welcome to Cluster Analysis");
        System.out.println("You can enter '-1' or type 'Stop' to stop the processes");
        System.out.println("Please enter the path to the data-file (e.g. C:/Java/xxx.csv");*/
        boolean stopRunning = false;
        // keep loop until it is true
        while (stopRunning==false){
            System.out.println("############################################################################################");
            System.out.println("# WELCOME TO SIMULATED ANNEALING TOOL BASED ON CENTER PERTURBATION USING GAUSSIAN MUTATION #");
            System.out.println("############################################################################################");
            System.out.println("You can enter: \n 1 to read the files from the default folder,\n 0 to enter the path manually ,or\n type 'help' to get information about the required files");

            System.out.println("Note: All inputs are not case sensitive");
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine();
            if (inputString.toLowerCase().equals("help")){
               System.out.println("#################################");
               System.out.println("########      HELP        #######");
               System.out.println("#################################");
               System.out.println("To use this tool you need to have two files:");
               System.out.println("1. File which contains the points of dataset which needs to be in '.csv' format");
               System.out.println("2. File which contains the parameter which also needs to be in '.csv' format");
               System.out.println("#################################");
               System.out.println("## Assumptions and Expectation ##");
               System.out.println("#################################");
               System.out.println("The assumption for the first file is: The file is in '.csv' format, it " +
                       "does have a header, the values of the points are numerical and each point does have" +
                       " a unique key which is the first value in the point array");
               System.out.println("The assumption for the second file is:  Same as the first file as well" +
                       " as it has the following pieces of information:{Please Note: Recommendations in this" +
                       " section are based on the developer personal experiences, you are however encouraged " +
                       "to choose you own values of the parameters}");
               System.out.println("1- clusteringMethod,'2' {1: Single Linkage, 2: Simulated Annealing SMC," +
                       " 3: Simulated Annealing MMC, 4: All the previous methods}\n" +
                       "2- numberOfClusters,'5'{It must be an Integer. No specific recommendation}\n" +
                       "3- distanceMethods,'1' {1: Euclidean Distance, 2: Manhattan Distance }\n" +
                       "4- numberOfIterationSMC,'10' {This parameter will be multiplied with the number of " +
                       "points in the dataset}\n" +
                       "5- acceptanceTemperature,'0.5' {For a small dataset '0.5' is recommended}\n" +
                       "6- initialTemperature,'2'{For a small dataset, '2.0' is recommended}\n" +
                       "7- finalTemperature,'0.5' {For a small dataset, '0.5' is recommended}\n" +
                       "8- coolingRate,'0.05' {No Specific recommendation}\n" +
                       "9- mutationFactor,'0.01' {Between '0.05' and '0.01' is recommended}\n" +
                       "10- showResultInConsole,'false' {For a large dataset, 'false' is recommended}\n" +
                       "11- numberOfIterationPerGivenTemperature,'4' {It must be an Integer and this parameter will be multiplied with the number of points in the dataset. No specific recommendation}");
               System.out.println("The order in which those parameters are placed is critical to let the tool " +
                       "work as expected");
               System.out.println("Last but not least, you can rename the dataset file as 'points.csv'" +
                       " and parameters file as 'parameters.csv.' and place them in a folder as to the next " +
                       "path 'C:/java/' so you can call everything automatically with selecting '1' in the first " +
                       "menu. However if you don't want to follow the last tip, you can give the path of each " +
                       "file manually by selecting '0'");
               System.out.println("#################################");
               System.out.println("######### Help Ends Here #######");
               System.out.println("#################################");
            }
            // if 1 is selected then read automatically from the default folder with the default name
            if (inputString.equals("1")){
                // read the file from the default paths C:/Java/points.csv and C:/Java/parameters.csv
                String filePath = "c:/java/points.csv";
                String parameterPath = "C:/Java/parameters.csv";
                // Check if the path goes to a csv file
                if (checkPath(filePath)){
                    // load the list of points
                    listOfPoints= readListOfPoints(filePath);
                    System.out.println("File 'Points' has been found and accepted. Total number of points is: "+ (listOfPoints.size()-1));
                    if (checkPath(parameterPath)){
                        // load the parameters
                       List<String[]> listOfParameters = readListOfPoints(parameterPath);
                        System.out.println("File 'Parameters' has been found and total number of read parameters is "+ (listOfParameters.size()-1));
                        executeOrdersInParametersFile(listOfParameters,listOfPoints);
                    }else{
                        // the path of the parameters is not correct
                        System.out.println("File 'Parameters' was not found or not in the required format. Please check again in 'C:/Java/Parameters'");
                    }
                }else{
                    System.out.println("File 'Points' was not found or not in the required format. Please check again in 'C:/Java/Points.csv'");
                }

            }else if (inputString.equals("0")){
                System.out.println("Please enter the path to the dataset. The file extension must '.csv'. For Example: 'C:/XXX/points.csv'");

                inputString = scanner.nextLine();
                if (inputString.equals("-1")||inputString.toLowerCase().contentEquals("stop")){
                    stopRunning = true;
                    break;
                }
                if (checkPath(inputString)){
                    System.out.println("Path is correct and has been accepted");
                    listOfPoints= readListOfPoints(inputString);
                    if (listOfPoints.size()!=0){
                        System.out.println("File has been read and number of points is: "+ (listOfPoints.size()-1));
                        System.out.println("Please enter the path to the parameters file. The file extension must '.csv'. For Example: 'C:/XXX/parameters.csv'");
                        inputString = scanner.nextLine();
                        if (checkPath(inputString)){
                            List<String[]> listOfParameters = readListOfPoints(inputString);
                            System.out.println("Parameters file has been read");
                            executeOrdersInParametersFile(listOfParameters,listOfPoints);

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

    /**
     * Reading the list of the points from the file
    */
    public List<String[]> readListOfPoints(String pathToDataFile)throws Exception {
        Reader reader1 = Files.newBufferedReader(Paths.get(pathToDataFile));
        CSVReader csvReader = new CSVReader(reader1);
        List<String[]> listOfStrings = new ArrayList<>();
        listOfStrings = csvReader.readAll();
        reader1.close();
        csvReader.close();
        return listOfStrings;

    }

    /**
     * Check if the given path is correct
    */
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

    /**
     * Print out the given parameters
    */
    public void printOutParatmers(List<String []> listOfParametersToBePrintout){
        System.out.println("The Next Parameter were given and will be applied");
        for (int x= 1; x<listOfParametersToBePrintout.size();x++){
            System.out.println(x+"- "+listOfParametersToBePrintout.get(x)[0] +" is set to "+listOfParametersToBePrintout.get(x)[1] );
        }
    }

    /**
     * Execute the orders in the parameters
    */
    public void executeOrdersInParametersFile(List<String[]> listOfParameters, List<String[]> listOfPoints)throws Exception{
        printOutParatmers(listOfParameters);
        SimulatedAnnealingMMC simulatedAnnealingMMC = new SimulatedAnnealingMMC();
        SimulatedAnnealingSMC simulatedAnnealingSMC = new SimulatedAnnealingSMC();
        SingleLinkage singleLinkage= new SingleLinkage();
        int clusteringMethod = new Integer(listOfParameters.get(1)[1]);
        int distanceMethods = new Integer(listOfParameters.get(3)[1]);
        int numberOfClusters = new Integer(listOfParameters.get(2)[1]);
        boolean showResultInConsole = new Boolean(listOfParameters.get(10)[1]);
        int numberOfIteration = new Integer(listOfParameters.get(4)[1])*listOfPoints.size();
        double acceptanceTemperature = new Double(listOfParameters.get(5)[1]);
        double initialTemperature = new Double(listOfParameters.get(6)[1]);
        double mutationFactor = new Double(listOfParameters.get(9)[1]);
        double finalTemperature = new Double(listOfParameters.get(7)[1]);
        double coolingRate = new Double(listOfParameters.get(8)[1]);
        int numberOfIterationPerGivenTemperature = new Integer(listOfParameters.get(11)[1]);
        if (clusteringMethod==1){
            // readListOfPoints single linkage
            System.out.println("Single Linkage is executing");

            singleLinkage.computeCluster(numberOfClusters,listOfPoints,distanceMethods,showResultInConsole);
        }else if (clusteringMethod ==2){
            // readListOfPoints simulated annealing SMC
            System.out.println("Simulated Annealing SMC is executing");

            simulatedAnnealingSMC.computeCluster(numberOfClusters,listOfPoints,numberOfIteration, acceptanceTemperature,mutationFactor,showResultInConsole);
        }else if (clusteringMethod ==3 ){
            // readListOfPoints simulated annealing MMC
            System.out.println("Simulated Annealing MMC is executing");

            simulatedAnnealingMMC.computeCluster(numberOfClusters,listOfPoints,initialTemperature,finalTemperature,coolingRate,mutationFactor,numberOfIterationPerGivenTemperature,showResultInConsole);
        }else{
            System.out.println("All methods will be executed");
            System.out.println("Single Linkage is executing");
            singleLinkage.computeCluster(numberOfClusters,listOfPoints,distanceMethods,showResultInConsole);
            System.out.println("Simulated Annealing SMC is executing");
            simulatedAnnealingSMC.computeCluster(numberOfClusters,listOfPoints,numberOfIteration, acceptanceTemperature,mutationFactor,showResultInConsole);
            System.out.println("Simulated Annealing MMC is executing");
            simulatedAnnealingMMC.computeCluster(numberOfClusters,listOfPoints,initialTemperature,finalTemperature,coolingRate,mutationFactor,numberOfIterationPerGivenTemperature,showResultInConsole);
        }
    }

























}
