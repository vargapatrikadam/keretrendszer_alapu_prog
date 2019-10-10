package filmespelda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DummyController {

    @RequestMapping(value = "/cica")
    public void dummy(){
        System.out.println("CICA");
    }
}
