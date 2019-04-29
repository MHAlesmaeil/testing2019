package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo;

import java.io.IOException;
import java.util.Scanner;

public class FactoryClass {

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int choiceNumber = 0;

        while(choiceNumber!=-1){
            System.out.println("Welcome to Cluster Analysis With Simulated Annealing");
            System.out.println("To Stop running you can enter '-1'");

            choiceNumber = scanner.nextInt();
            System.out.println("Number you have entered is: "+ choiceNumber);
        }

    }
}
