package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web;

import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web.fehler.InternalServerError;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.a_PreparingDataSet.DataSet;
import clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.a_PreparingDataSet.DataSetService;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
    private final DataSetService dataSetService;

    public IndexController(DataSetService dataSetService) {
        this.dataSetService = dataSetService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("newDataSet");
        return "index";
    }
    @ModelAttribute
    public void showAvaibaleDataSet(Model model){
        model.addAttribute("avaibleDataSet", dataSetService.findAllDataSet());
    }
    @PostMapping("/index")
    public String uploaddata (@ModelAttribute("newDataSet") DataSet dataSet){
        // TODO: 03.04.2019 if data has been read, then accept it, otherweise throw an exception
        try{
            this.dataSetService.saveDataSet(dataSet);
        }catch (Exception e){
            throw new InternalServerError();
        }

        return "redirect:/index";
    }


    /*// jede kann diesen URL aufrufen
    @GetMapping("/login")
    public String login() {
        //Bevor die Login Seite gezeigt wird, wird geprueft ob der Benutzer anonym oder bereits angemeldet ist,
        //falls der Benutzer anonym ist, wird die Login Seite angezeigt, sonst wird die Start Seite gezeigt
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof AnonymousAuthenticationToken ? "login" : "redirect:/index";
    }*/
}
