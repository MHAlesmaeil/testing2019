package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web.errorHandlers.NotFoundException;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataPreperation.DataSet;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataPreperation.DataSetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DataSetDetailsController {
    private final DataSetService dataSetService;

    public DataSetDetailsController(DataSetService dataSetService) {
        this.dataSetService = dataSetService;
    }

    // here we must show a confirmation if the data were successfully added to the database
    @GetMapping("/dataSets/{id}")
    public String showDataSetInformation(Model model, @PathVariable("id") Long id){
        DataSet dataSet = dataSetService.findDataSetById(id);

        if (dataSet==null){
            throw new NotFoundException();
        }
        model.addAttribute("dataSetDetails", dataSetService.findDataSetById(id));
        return "data/dataSetDetails";
    }

    @GetMapping("/dataSets/testWithMe/{id}")
    public String useDataSet(Model model, @PathVariable("id") Long id) throws Exception {
        List<String[]> dataSet =  dataSetService.dataSetDetails(id);
        model.addAttribute("totalcolums",dataSetService.getTotalNumberOfColumns(id));
        model.addAttribute("testWithMe", dataSet);
        model.addAttribute("newProcess");
        return "data/testWithMe";
    }

    @GetMapping("/dataSets/delete/{id}")
    public String deleteDataSet (Model model, @PathVariable("id") Long id){
        dataSetService.deleteDataSet(id);
        return "redirect:/index";
    }

}
