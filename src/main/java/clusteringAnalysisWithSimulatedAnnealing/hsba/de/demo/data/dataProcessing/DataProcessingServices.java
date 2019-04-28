package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataProcessing;


import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataPreperation.RawDataSetRepository;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataPreperation.RawDataSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.Collection;


@Service
@Transactional
public class DataProcessingServices {
    private final DataProcessingRepository processingRepository;
    private final RawDataSetService rawDataSetService;

    public DataProcessingServices(DataProcessingRepository processingRepository, RawDataSetService rawDataSetService) {
        this.processingRepository = processingRepository;
        this.rawDataSetService = rawDataSetService;
    }

    public void saveNewProcess(DataProcessing newProcess)throws Exception{
        newProcess.setListOfPoints(rawDataSetService.dataSetDetails(newProcess.getDataSetNumber()));
        processingRepository.save(newProcess);
    }

    public Collection<DataProcessing> findAllProcesses (){
        return processingRepository.findAll();
    }
    public void deleteDataProcessing (Long id){
        processingRepository.deleteById(id);
    }

    public DataProcessing findProcessById (Long id){
        return processingRepository.findById(id).orElse(null);
    }
}
