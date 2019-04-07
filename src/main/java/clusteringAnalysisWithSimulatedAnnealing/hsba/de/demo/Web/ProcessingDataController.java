package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ProcessingDataController {
    @GetMapping("/ProcssingData")
    public String showPage (){
        return "data/dataIsInProcess";
    }
}
