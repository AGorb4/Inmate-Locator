package gov.phila.inmate.locator.services;

import java.util.List;

import gov.phila.inmate.locator.Inmate;
import gov.phila.inmate.locator.exceptions.NoInmateFoundException;

public interface InmateLocatorService {

	Inmate getInmateByPID(int pID) throws NoInmateFoundException;
	
	Inmate getInmateByPIDWarden(int pID, String location) throws NoInmateFoundException;

	Inmate getInmateByNameAndDOB(String firstName, String lastName, String dob) throws NoInmateFoundException;

	List<Inmate> getAllInmates();

	List<Inmate> getInmatesByLocation(String location);
}
