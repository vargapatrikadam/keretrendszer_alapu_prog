package filmespelda.controller;

import filmespelda.exceptions.DateIsTooLate;
import filmespelda.model.Szereplo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class DummyController {

    @RequestMapping(value = "/cica")
    public void dummy(){
        System.out.println("CICA");
    }

    @RequestMapping(value = "/dummy")
    @ResponseBody
    public Szereplo returnWithSzereplo() throws DateIsTooLate {
        Szereplo szereplo = new Szereplo();
        szereplo.setSzuletesi_datum(LocalDate.of(1998,8,12));
        szereplo.setKarakter_neve("Don Juan");
        szereplo.setId(UUID.randomUUID());
        szereplo.setKeresztnev("Erika");
        szereplo.setVezeteknev("Zoltán");
        return szereplo;
    }
    @RequestMapping(value = "/dummyWrong")
    @ResponseBody
    public Szereplo returnWithException() throws DateIsTooLate {
        Szereplo szereplo = new Szereplo();
        szereplo.setSzuletesi_datum(LocalDate.of(2088,8,12));
        szereplo.setKarakter_neve("Don Juan");
        szereplo.setId(UUID.randomUUID());
        szereplo.setKeresztnev("Erika");
        szereplo.setVezeteknev("Zoltán");
        return szereplo;
    }


    @ExceptionHandler(DateIsTooLate.class)
    @ResponseBody
    public String handleException(){
        return "HEheeee";

    }
}
