package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web.fehler.NotFoundException;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.a_PreparingDataSet.DataSet;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.a_PreparingDataSet.DataSetService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DataSetDetails {
    private final DataSetService dataSetService;

    public DataSetDetails(DataSetService dataSetService) {
        this.dataSetService = dataSetService;
    }

    // here we must show a confirmation if the data were successfully added to the database
    @GetMapping("/dataSets/{id}")
    public String showDataSet (Model model, @PathVariable("id") Long id){
        DataSet dataSet = dataSetService.findDataSetById(id);

        if (dataSet==null){
            throw new NotFoundException();
        }
        model.addAttribute("dataSetDetails", dataSetService.findDataSetById(id));
        return "data/dataSetDetails";
    }
    @GetMapping("/dataSets/delete/{id}")
    public String deleteDataSet (Model model, @PathVariable("id") Long id){
        dataSetService.deleteDataSet(id);
        return "redirect:/index";
    }
}
