package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.a_PreparingDataSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DataSetRepository extends JpaRepository<DataSet, Long> {
}
