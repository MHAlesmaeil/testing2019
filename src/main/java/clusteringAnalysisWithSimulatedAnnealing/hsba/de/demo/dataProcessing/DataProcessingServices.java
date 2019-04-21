package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataProcessing;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.Collection;


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

    public void processExecution(){

    }
}
