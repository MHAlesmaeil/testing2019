package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.a_PreparingDataSet;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class DataSetService {
    private final DataSetRepository dataSetRepository;
    private List<String []> dataSetDetails;



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


    // Method which is a list that contains all data as List<String[]>
    public List<String[]> dataSetDetails(Long id) throws Exception {
        DataSet dataSet = findDataSetById(id);
        Reader reader1 = Files.newBufferedReader(Paths.get(dataSet.getDataSetPath()));
        CSVReader csvReader = new CSVReader(reader1);
        List<String[]> list = new ArrayList<>();
        list = csvReader.readAll();
        reader1.close();
        csvReader.close();
        // TODO: 03.04.2019 checking the data before prcessing to the next step 
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

    public String[] getHeaderOfDataSet(Long id) throws Exception {
        setHeaderOfDataSet(id);
        for (int x=0; x<getHeaderOfDataSet(id).length;x++){
            System.out.println(getHeaderOfDataSet(id)[x]);
        }
        return headerOfDataSet;
    }

    public void setHeaderOfDataSet(Long id) throws Exception{
        Long aLong= new Long(0);
        List<String []> header = dataSetDetails(id);
        this.headerOfDataSet = header.get(0);
    }
    public int getTotalNumberOfColumns(Long dataSetid) throws Exception {
        if (totalNumberOfColumns ==0){
            setTotalNumberOfColums(dataSetid);
        }
        return totalNumberOfColumns;
    }
    // this setter is related to the data set
    private void setTotalNumberOfColums(Long dataSetid) throws Exception   {
        List<String []> header = dataSetDetails(dataSetid);
        this.totalNumberOfColumns =  header.get(0).length-1;
    }

    public int getTotalNumberOfRows(Long dataSetid) throws Exception {
        if (totalNumberOfRows==0){
            setTotalNumberOfRows(dataSetid);
        }
        return totalNumberOfRows;
    }

    private void setTotalNumberOfRows(Long id) throws Exception {
        int totalNumberOfRows=0;
        for (String [] tempTotalNumberOfRows: dataSetDetails(id)){
            totalNumberOfRows+=1;
        }
        this.totalNumberOfRows = totalNumberOfRows-1;
    }
    // the columns which are not relevant in the next steps will be here eliminated 
    public int[] getRelevantColumns(Long id) throws Exception {
        int columnNumber = 1;
        // show the header row with numbers
        for (String columnName: getHeaderOfDataSet(id)){
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
