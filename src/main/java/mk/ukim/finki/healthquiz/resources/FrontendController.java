package mk.ukim.finki.healthquiz.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by KM on 07-Feb-17.
 */
@Controller
public class FrontendController {

    @RequestMapping(value="/",method = RequestMethod.GET)
    public String home() {
        return "index";
    }
}
