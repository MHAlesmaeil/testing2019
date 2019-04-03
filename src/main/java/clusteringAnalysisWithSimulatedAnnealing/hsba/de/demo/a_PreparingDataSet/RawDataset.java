/*
package a_PreparingDataSet;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class RawDataset {
    private static final String SAMPLE_CSV_FILE_PATH = "C:/Go/Mall_Customers.csv";

    public static void main(String[] args) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader)

        ) {
            */
/*//*
/ Reading Records One by One in a String array
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                System.out.println("how many Elements? "+ nextRecord.length);
                System.out.println("Name : " + nextRecord[0]);
                System.out.println("Email : " + nextRecord[1]);
                System.out.println("Phone : " + nextRecord[2]);
                System.out.println("Country : " + nextRecord[3]);
                System.out.println("==========================");
            }*//*

            System.out.println(csvReader.verifyReader());
            List<String[]> records = csvReader.dataSetDetails();
            for (String[] record : records) {
                System.out.println("Name : " + record[0]);
                System.out.println("Email : " + record[1]);
                System.out.println("Phone : " + record[2]);
                System.out.println("Country : " + record[3]);
                System.out.println("Country : " + record[4]);
                System.out.println("---------------------------");
            }
        }
        // asking if the data does have header of not

        // if header exists

            // specifying an array to be be called one the header are exists

        // if header is not exists

        // which columns are relevant for the analysis

    }

}
*/
