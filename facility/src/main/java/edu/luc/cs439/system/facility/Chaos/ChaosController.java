package edu.luc.cs439.system.facility.Chaos;

import edu.luc.cs439.system.facility.Chaos.ChaosSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jlroo on 3/24/17.
 */

@RestController
@RequestMapping(value="chaos")
public class ChaosController {

    @Autowired
    ChaosSource chaos = new ChaosSource();
    private String response;

    @RequestMapping(value="/rate/{rate}",produces = "application/json")
    public String setRate(@PathVariable("rate") int rate) {
        chaos.setProbabilityOfProblem(rate);
        response = String.valueOf("Problem rate set at: " + chaos.getProbabilityOfProblem());
        return response;
    }

}
