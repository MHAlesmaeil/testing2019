package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataPreperation;

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
public class RawDataSetService {
    private final RawDataSetRepository rawDataSetRepository;
    private List<String []> dataSetDetails;



    public RawDataSetService(RawDataSetRepository rawDataSetRepository) {
        this.rawDataSetRepository = rawDataSetRepository;
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

    public void saveDataSet (RawDataSet dataset)throws Exception{
        if (verifyPathCorrectness(dataset)==true){
            rawDataSetRepository.save(dataset);
        }
    }
    public void deleteDataSet (Long id){
            rawDataSetRepository.deleteById(id);
    }
    public Collection<RawDataSet> findAllDataSet (){
           return rawDataSetRepository.findAll();
    }
    public RawDataSet findDataSetById(Long id) {
        return rawDataSetRepository.findById(id).orElse(null);
    }


    // Method which is a list that contains all data as List<String[]>
    public List<String[]> dataSetDetails(Long id) throws Exception {
        RawDataSet rawDataSet = findDataSetById(id);
        Reader reader1 = Files.newBufferedReader(Paths.get(rawDataSet.getDataSetPath()));
        CSVReader csvReader = new CSVReader(reader1);
        List<String[]> list = new ArrayList<>();
        list = csvReader.readAll();
        reader1.close();
        csvReader.close();
        return list;
    }
    // Method which is a list that contains all data as List<String[]>
    public List<String[]> dataSetSummay(Long id) throws Exception {
        List<String[]> list = new ArrayList<>();
        list = dataSetDetails(id);
        List<String []> summary = new ArrayList<>();
        // add the first 3 rows to the summary

        // if the points/elements of a dataset is greater than 10, then add an empty line and the last two elements
        if (list.size()>10){
            for (int x = 0; x<3; x++){
                summary.add(list.get(x));
            }
            for (int x=0; x<1; x++){
                String [] emptyLine = new String[summary.get(0).length];
                for (int y= 0; y<summary.get(0).length; y++){
                    emptyLine[y]=" ... ";
                }
                summary.add(emptyLine);
            }

            for (int x=list.size()-2; x<list.size(); x++){
                summary.add(list.get(x));
            }
        }else {
            for (int x = 0; x<5; x++){
                summary.add(list.get(x));
            }
        }
        return summary;
    }

    // to compute the values
    public List<double[]> dataSetDoubleWithoutHeader(List<String[]> dataSetToBeConverted ) throws Exception{
        List<double[]> temp = new ArrayList<>();
        List<String []> tempString = dataSetToBeConverted;
        // for loop to call each row in the string dataset
        // first row will be escaped as the header might not be a double
        for (int dataSetRowCounter=1; dataSetRowCounter<tempString.size(); dataSetRowCounter++){
            String [] singleRowString = tempString.get(dataSetRowCounter);
            double [] singleRowDouble = new double[singleRowString.length];
            // for loop to call every single attribute in the row and save it in the return List<double []>
            for (int singleRowStringCounter=0; singleRowStringCounter<singleRowString.length; singleRowStringCounter++){
                singleRowDouble[singleRowStringCounter]=Double.valueOf(singleRowString[singleRowStringCounter]);
            }
            temp.add(singleRowDouble);
        }
        return temp;
    }

    public boolean verifyPathCorrectness (RawDataSet rawDataSet) throws Exception{
        boolean isItValid;
        Reader reader = Files.newBufferedReader(Paths.get(rawDataSet.getDataSetPath()));
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
}
