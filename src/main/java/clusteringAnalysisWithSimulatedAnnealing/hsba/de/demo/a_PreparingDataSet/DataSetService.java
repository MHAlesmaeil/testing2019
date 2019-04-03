package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.a_PreparingDataSet;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web.fehler.InternalServerError;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

@Service
@Transactional
public class DataSetService {
    private final DataSetRepository dataSetRepository;

    public DataSetService(DataSetRepository dataSetRepository) {
        this.dataSetRepository = dataSetRepository;
    }

    // default path for the file
    private static String SAMPLE_CSV_FILE_PATH = "C:/Java/TestFolder/MallCustomers.csv";
    // Header of the data set
    private String[] headerOfDataSet;
    // total number of columns
    private int totalNumberOfColumns;
    // total number of rows without the header
    private int totalNumberOfRows;
    // relevant columns
    int [] relevantColumns;

    public void saveDataSet (DataSet dataset)throws Exception{
        if (verifyPathCorrectness(dataset)==true){
            dataSetRepository.save(dataset);
        }
    }
      public void deleteDataSet (Long id){
            dataSetRepository.deleteById(id);
    }
      public Collection<DataSet> findAllDataSet (){
           return dataSetRepository.findAll();
    }
    public DataSet findDataSetById(Long id) {
        return dataSetRepository.findById(id).orElse(null);
    }

    // constructor
    /*public DataSetService() throws Exception {
        // show the first notification
        new Notifications(1);
        Scanner scanner = new Scanner(System.in);
        String dataPath = scanner.nextLine();
        // if the enter value is 1, then use the hardcoded path
        if (!dataPath.equals("1")){
           SAMPLE_CSV_FILE_PATH=dataPath;
        }
        if (!readAll().isEmpty()){
            System.out.println("Data Set was loaded successfully! Congrats");
        }
        dataSetRepository= null;
    }*/

    // Method which is a list that contains all data as List<String[]>
    public List<String[]> readAll() throws Exception {
        Reader reader1 = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
        CSVReader csvReader = new CSVReader(reader1);
        List<String[]> list = new ArrayList<>();
        list = csvReader.readAll();
        reader1.close();
        csvReader.close();
        return list;
    }

    public boolean verifyPathCorrectness (DataSet dataSet) throws Exception{
        boolean isItValid;
        Reader reader = Files.newBufferedReader(Paths.get(dataSet.getDataSetPath()));
        CSVReader csvReader = new CSVReader(reader);
        isItValid = csvReader.verifyReader();
        reader.close();
        csvReader.close();
        return isItValid;

    }

    /**
     *Getter and Setter
    */

    public String[] getHeaderOfDataSet() throws Exception {
        setHeaderOfDataSet();
        return headerOfDataSet;
    }

    private void setHeaderOfDataSet() throws Exception{
        String[] header = readAll().get(0);
        this.headerOfDataSet = header;
    }
    public int getTotalNumberOfColumns() throws Exception {
        if (totalNumberOfColumns ==0){
            setTotalNumberOfColums();
        }
        return totalNumberOfColumns;
    }
    // this setter is related to the data set
    private void setTotalNumberOfColums() throws Exception   {
        int totalNumberOfColums;
        String[] totalNumberOfItemInFirstColum = this.getHeaderOfDataSet();
        totalNumberOfColums = totalNumberOfItemInFirstColum.length;
        this.totalNumberOfColumns = totalNumberOfColums;
    }

    public int getTotalNumberOfRows() throws Exception {
        if (totalNumberOfRows==0){
            setTotalNumberOfRows();
        }
        return totalNumberOfRows;
    }

    private void setTotalNumberOfRows() throws Exception {
        int totalNumberOfRows=0;
        for (String [] tempTotalNumberOfRows: readAll()){
            totalNumberOfRows+=1;
        }
        this.totalNumberOfRows = totalNumberOfRows-1;
    }
    // the columns which are not relevant in the next steps will be here eliminated 
    public int[] getRelevantColumns() throws Exception {
        int columnNumber = 1;
        // show the header row with numbers
        for (String columnName: getHeaderOfDataSet()){
            System.out.print(columnNumber +" "+ columnName +" | ");
            columnNumber++;
        }
        // Instructions of selecting the columns
        new Notifications(2);
        // TODO: 02.04.2019 you are here .--> next step to place scaner for 
        return relevantColumns;
    }

    public void setRelevantColumns(String relevantColumns) throws Exception {

    }


}
