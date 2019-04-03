package clusteringAnalysisWithSimulatedAnnealing.hsba.de.demo.Web.fehler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class Forbidden extends RuntimeException {
}
