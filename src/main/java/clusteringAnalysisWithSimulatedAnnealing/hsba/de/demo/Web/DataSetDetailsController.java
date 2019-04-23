package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web.errorHandlers.NotFoundException;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataPreperation.RawDataSet;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.data.dataPreperation.RawDataSetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DataSetDetailsController {
    private final RawDataSetService rawDataSetService;

    public DataSetDetailsController(RawDataSetService rawDataSetService) {
        this.rawDataSetService = rawDataSetService;
    }

    // here we must show a confirmation if the data were successfully added to the database
    @GetMapping("/dataSets/{id}")
    public String showDataSetInformation(Model model, @PathVariable("id") Long id){
        RawDataSet rawDataSet = rawDataSetService.findDataSetById(id);

        if (rawDataSet ==null){
            throw new NotFoundException();
        }
        model.addAttribute("dataSetDetails", rawDataSetService.findDataSetById(id));
        return "data/dataSetDetails";
    }

    @GetMapping("/dataSets/testWithMe/{id}")
    public String useDataSet(Model model, @PathVariable("id") Long id) throws Exception {
        List<String[]> dataSet =  rawDataSetService.dataSetSummay(id);
        model.addAttribute("totalcolums", rawDataSetService.getTotalNumberOfColumns(id));
        model.addAttribute("dataSetNumber", id);

        model.addAttribute("testWithMe", dataSet);
        model.addAttribute("newProcess");
        return "data/testWithMe";
    }

    @GetMapping("/dataSets/delete/{id}")
    public String deleteDataSet ( @PathVariable("id") Long id){
        rawDataSetService.deleteDataSet(id);
        return "redirect:/index";
    }

}
