package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataProcessing;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataPreperation.RawDataSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class DataProcessingServices {
    private final DataProcessingRepository processingRepository;

    public DataProcessingServices(DataProcessingRepository processingRepository) {
        this.processingRepository = processingRepository;
    }

    public void saveNewProcess(DataProcessing newProcess){
        processingRepository.save(newProcess);
    }

    public Collection<DataProcessing> findAllProcesses (){
        return processingRepository.findAll();
    }
    public void deleteDataProcessing (Long id){
        processingRepository.deleteById(id);
    }
}
