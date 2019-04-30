package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FactoryClass {

    public List<String[]> execute(String pathToFileOfData)throws Exception {
        String SAMPLE_CSV_FILE_PATH = pathToFileOfData;

        Reader reader1 = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
        CSVReader csvReader = new CSVReader(reader1);
        List<String[]> listOfStrings = new ArrayList<>();
        listOfStrings = csvReader.readAll();
        reader1.close();
        csvReader.close();
        return listOfStrings;

    }
}
