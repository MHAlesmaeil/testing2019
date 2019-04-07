package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataProcessing;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
