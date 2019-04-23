package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataPreperation;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "dataSet")
@Component
public class RawDataSet {
    @Basic(optional = false)
    private String dataSetName;
    private String dataSetDescription;
    @Basic(optional = false)
    private String dataSetPath;
    @Id @GeneratedValue
    private Long id;


    /**
     * Getters and setters
    */
    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getDataSetDescription() {
        return dataSetDescription;
    }

    public void setDataSetDescription(String dataSetDescription) {
        this.dataSetDescription = dataSetDescription;
    }

    public String getDataSetPath() {
        return dataSetPath;
    }

    public void setDataSetPath(String dataSetPath) {
        this.dataSetPath = dataSetPath;
    }

    public Long getId() {
        return id;
    }
}
