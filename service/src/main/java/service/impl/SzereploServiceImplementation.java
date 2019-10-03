package service.impl;

import filmespelda.exceptions.DateIsTooLate;
import filmespelda.exceptions.NoMatchingId;
import filmespelda.model.Szereplo;
import org.omg.CORBA.DynAnyPackage.InvalidValue;
import service.SzereploService;

import java.util.Collection;
import java.util.UUID;

public class SzereploServiceImplementation implements SzereploService {

    public Collection<Szereplo> listAllSzereplo() {
        return null;
    }

    public Szereplo getSzereplo(UUID id) throws NoMatchingId {
        return null;
    }

    public void addSzereplo(Szereplo szereplo) throws DateIsTooLate, InvalidValue {

    }

    public void updateSzereplo(Szereplo szereplo) throws DateIsTooLate, InvalidValue {

    }

    public void deleteSzereplo(Szereplo szereplo) throws NoMatchingId {

    }

    public void deleteSzereplo(UUID id) throws NoMatchingId {

    }
}
