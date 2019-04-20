package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataProcessing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
@Transactional
public interface DataProcessingRepository extends JpaRepository< DataProcessing, Long> {
    // new Processes appear first

}
