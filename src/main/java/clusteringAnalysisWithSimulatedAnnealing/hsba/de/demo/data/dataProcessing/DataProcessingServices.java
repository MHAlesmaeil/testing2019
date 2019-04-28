package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing;


import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.Cluster;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.cluster.ClusterReposity;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataPreperation.RawDataSetRepository;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataPreperation.RawDataSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@Transactional
public class DataProcessingServices {

    List<String[]> listOfStrings = new ArrayList<>();

    private final DataProcessingRepository processingRepository;
    private final ClusterReposity clusterReposity;
    private final RawDataSetService rawDataSetService;

    public DataProcessingServices(DataProcessingRepository processingRepository, ClusterReposity clusterReposity, RawDataSetService rawDataSetService) {
        this.processingRepository = processingRepository;
        this.clusterReposity = clusterReposity;
        this.rawDataSetService = rawDataSetService;
    }

    public void saveNewProcess(DataProcessing newProcess)throws Exception{
        processingRepository.save(newProcess);
    }

    public Collection<DataProcessing> findAllProcesses (){
        return processingRepository.findAll();
    }
    public DataProcessing findDataProcessingById(Long id){
        return processingRepository.findById(id).orElse(null);
    }
    public void deleteDataProcessing (Long id){
        processingRepository.deleteById(id);
    }

    public void addNewCluster() throws Exception{
        Cluster cluster = new Cluster();
        cluster.setClusterName("cluster one");
        clusterReposity.save(new Cluster());
    }
}
