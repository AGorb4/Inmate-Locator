package gov.phila.inmate.locator.daos;

import java.util.List;

import gov.phila.inmate.locator.Inmate;

public interface InmateLocatorDAO {

	List<Inmate> getAllInmates();

}