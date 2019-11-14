package filmespelda.controller;

import filmespelda.exceptions.DateIsTooLate;
import filmespelda.exceptions.NoMatchingId;
import filmespelda.model.Szereplo;
import org.omg.CORBA.DynAnyPackage.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.SzereploService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Controller
public class SzereploController {
    SzereploService service;

    public SzereploController(@Autowired SzereploService service) {
        this.service = service;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ResponseBody
    public String showSzereplokCount(){
        return String.valueOf(service.listAllSzereplo().size());
    }

    @RequestMapping(value = "/szereplok", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Szereplo> showAllSzereplo(){
        return service.listAllSzereplo();
    }

    @RequestMapping(value = "/addSzereplo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addSzereplo(@RequestBody Szereplo szereplo) throws DateIsTooLate, InvalidValue {
        service.addSzereplo(szereplo);
        return szereplo.getId().toString();
    }

    @RequestMapping(value = "/removeSzereplo", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String removeSzereplo(@RequestBody Szereplo szereplo) throws DateIsTooLate, InvalidValue, NoMatchingId {
        service.deleteSzereplo(szereplo);
        return  "Szereplo torolve: " + szereplo.getId().toString();
    }

    @RequestMapping(value = "/fiatalkoruszerpelok", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Szereplo> showFiatalkoruak(){
        Collection<Szereplo> szereplok = service.listAllSzereplo();
        Collection<Szereplo> fiatalok = new ArrayList<>();
        for(Szereplo sz: szereplok){
            if(sz.getSzuletesi_datum().isAfter(LocalDate.now().minusYears(18))){
                fiatalok.add(sz);
            }
        }
        return fiatalok;
    }

    @ExceptionHandler(NoMatchingId.class)
    @ResponseBody
    public String handleNoMatchingId(Exception e){
        return "UUID not found in the database: " + e.getMessage();
    }

    @RequestMapping(value = "/szereplo/{id:[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}}", method = RequestMethod.GET)
    @ResponseBody
    public Szereplo getSzereploById(@PathVariable UUID id) throws NoMatchingId {
        return service.getSzereplo(id);
    }

    /*@ExceptionHandler(NoMatchingId.class)
    @ResponseBody
    public String handleNoMatchingId(Exception e){
        return "UUID not found in the database: " + e.getMessage();
    }*/
}
