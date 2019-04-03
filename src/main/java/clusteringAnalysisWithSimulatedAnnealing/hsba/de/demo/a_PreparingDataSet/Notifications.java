package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.a_PreparingDataSet;

public class Notifications {
    public  Notifications(int x) {
        // 1 is the start processing message
        if (x==1){
            System.out.println("##########################################################");
            System.out.println("The Process has been started \n"+"It is recommended to locate your file in a folder which is direct on the c drive \n"+"Enter 1 to use the hard coded path or a fall path e.g.: C:/xxx/xxx.csv ");
            System.out.println("##########################################################");
        }
        // 2 : Selecting the relavent colums and the format of it
        else if (x==2){
            System.out.println("\n##########################################################");
            System.out.println("The Columns with their name and number are Listed above");
            System.out.println("Please select the start column and the end one");
            System.out.println("For Example: '1:3' means the column is 1 and it ends with 3 inclusively");
            System.out.println("##########################################################");

        }



    }
}
