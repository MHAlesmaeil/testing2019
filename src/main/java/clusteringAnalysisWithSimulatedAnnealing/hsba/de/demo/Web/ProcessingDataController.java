package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web.errorHandlers.InternalServerError;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataPreperation.DataSet;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataProcessing.DataProcessing;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataProcessing.DataProcssingServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProcessingDataController {
    private final DataProcssingServices procssingServices;

    public ProcessingDataController(DataProcssingServices procssingServices) {
        this.procssingServices = procssingServices;
    }

    @GetMapping("/ProcssingData")
    public String showPage (Model model, @ModelAttribute("newProcess")DataProcessing newProcess){
        model.addAttribute("dataToBePeocessed", newProcess);
        return "data/dataIsInProcess";
    }
    @PostMapping("/ProcssingData")
    public String uploaddata (@ModelAttribute("newProcess") DataProcessing dataProcessing){
        // TODO: 03.04.2019 if data has been read, then accept it, otherweise throw an exception
        try{
            procssingServices.saveNewProcess(dataProcessing);
        }catch (Exception e){
            throw new InternalServerError();
        }

        return "redirect:/index";
    }
}
