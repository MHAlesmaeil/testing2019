package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web;


import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web.errorHandlers.InternalServerError;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataProcessing.DataProcessing;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.dataProcessing.DataProcessingServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProcessingDataController {
    private final DataProcessingServices dataProcessingServices;

    public ProcessingDataController(DataProcessingServices dataProcessingServices) {
        this.dataProcessingServices = dataProcessingServices;
    }

    @GetMapping("/ProcssingData")
    public String showPage (Model model){
        model.addAttribute("something", "here is the value");
        return "data/dataIsInProcess";
    }

    @PostMapping("/ProcssingData")
    public String uploaddata (@ModelAttribute("newProcess") DataProcessing newProcess){
        try{
            this.dataProcessingServices.saveNewProcess(newProcess);
        }catch (Exception e){
            throw new InternalServerError();
        }

        return "redirect:/ProcssingData";
    }
}
