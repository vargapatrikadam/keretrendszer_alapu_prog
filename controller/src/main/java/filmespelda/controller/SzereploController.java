package filmespelda.controller;

import filmespelda.exceptions.DateIsTooLate;
import filmespelda.exceptions.NoMatchingId;
import filmespelda.model.Szereplo;
import org.omg.CORBA.DynAnyPackage.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

    @RequestMapping(value = "/szereplok/", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Szereplo> getSzereploByDate(@RequestParam(required = false) Integer ev,
                                    @RequestParam(required = false) Integer honap,
                                    @RequestParam(required = false) Integer nap) throws DateIsMissingSense {

        if(ev == null && honap == null && nap == null){
            throw new DateIsMissingSense();
        }

        Collection<Szereplo> szereplok =  service.listAllSzereplo();
        Collection<Szereplo> szerpeloResult = new ArrayList<>();

        for (Szereplo sz: szereplok) {
            if(ev != null && honap != null && nap != null){
                LocalDate date = LocalDate.of(ev, honap, nap);
                if(sz.getSzuletesi_datum().equals(date)) {
                    szerpeloResult.add(sz);
                    continue;
                }
                continue;
            }

            if(ev != null && honap != null) {
                if(sz.getSzuletesi_datum().getMonth().getValue() == honap &&
                    sz.getSzuletesi_datum().getYear() == ev){
                    szerpeloResult.add(sz);
                    continue;
                }
                continue;
            }
            if(honap != null && nap != null) {
                if(sz.getSzuletesi_datum().getMonth().getValue() == honap &&
                        sz.getSzuletesi_datum().getDayOfMonth() == nap){
                    szerpeloResult.add(sz);
                    continue;
                }
                continue;
            }
            if(ev != null && nap != null) {
                if(sz.getSzuletesi_datum().getYear() == ev &&
                        sz.getSzuletesi_datum().getDayOfMonth() == nap){
                    szerpeloResult.add(sz);
                    continue;
                }
                continue;
            }

            if(ev != null){
                if(sz.getSzuletesi_datum().getYear() != ev){
                    continue;
                }
                szerpeloResult.add(sz);
                continue;
            }
            if(honap != null){
                if(sz.getSzuletesi_datum().getMonth().getValue() != honap){
                    continue;
                }
                szerpeloResult.add(sz);
                continue;
            }
            if(nap != null){
                if(sz.getSzuletesi_datum().getDayOfMonth() != nap){
                    continue;
                }
                szerpeloResult.add(sz);
                continue;
            }
        }

        return szerpeloResult;
    }
}
