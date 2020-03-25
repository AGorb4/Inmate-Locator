package gov.phila.inmate.locator.services;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.phila.inmate.locator.Inmate;
import gov.phila.inmate.locator.daos.InmateLocatorDAO;
import gov.phila.inmate.locator.exceptions.NoInmateFoundException;

@Service
public class InmateLocatorServiceImpl implements InmateLocatorService {

	private InmateLocatorDAO inmateLocatorDao;

	@Autowired
	public void setInmateLocatorDao(InmateLocatorDAO inmateLocatorDao) {
		this.inmateLocatorDao = inmateLocatorDao;
	}

	@Override
	public Inmate getInmateByPID(int pID) throws NoInmateFoundException {
		Inmate requestedInmate = null;
		for (Inmate inmate : inmateLocatorDao.getAllInmates()) {
			if (inmate.getpID() == pID) {
				requestedInmate = inmate;
				break;
			}
		}

		if (requestedInmate == null) {
			throw new NoInmateFoundException("No inmate found with PID : " + pID);
		}

		return requestedInmate;
	}
	
	@Override
	public Inmate getInmateByPIDWarden(int pID, String location) throws NoInmateFoundException {
		Inmate requestedInmate = null;
		for (Inmate inmate : getInmatesByLocation(location)) {
			if (inmate.getpID() == pID) {
				requestedInmate = inmate;
				break;
			}
		}

		if (requestedInmate == null) {
			throw new NoInmateFoundException("No inmate found with PID : " + pID);
		}

		return requestedInmate;
	}

	@Override
	public Inmate getInmateByNameAndDOB(String firstName, String lastName, String dob) throws NoInmateFoundException {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Inmate requestedInmate = null;
		for (Inmate inmate : inmateLocatorDao.getAllInmates()) {
			if (inmate.getFirstName().equalsIgnoreCase(firstName) && inmate.getLastName().equalsIgnoreCase(lastName)
					&& df.format(inmate.getDob()).equals(dob)) {
				requestedInmate = inmate;
				break;
			}
		}

		if (requestedInmate == null) {
			throw new NoInmateFoundException(
					"No inmate found named : " + firstName + " " + lastName + " born on " + dob);
		}

		return requestedInmate;
	}

	@Override
	public List<Inmate> getAllInmates() {
		return inmateLocatorDao.getAllInmates();
	}

	@Override
	public List<Inmate> getInmatesByLocation(String location) {
		return inmateLocatorDao.getAllInmates().stream()
				.filter(inmate -> inmate.getLocation().equalsIgnoreCase(location)).collect(Collectors.toList());
	}
}