package gov.phila.inmate.locator.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import gov.phila.inmate.locator.Inmate;

@Repository
public class InmateLocatorDAOImpl implements InmateLocatorDAO {

	List<Inmate> allInmatesList = new ArrayList<>();

	@Override
	public List<Inmate> getAllInmates() {
		return allInmatesList;
	}
	
	@PostConstruct
	private void createInmates() {
		allInmatesList.add(new Inmate(123456, "Michael", "Scott", new Date(10452013000L), "City Hall"));
		allInmatesList.add(new Inmate(123458, "Dwight", "Shrute", new Date(10452013000L), "City Hall"));
		allInmatesList.add(new Inmate(123459, "Jim", "Halpert", new Date(10452013000L), "City Hall"));
		allInmatesList.add(new Inmate(123460, "Pam", "Beasley", new Date(10452013000L), "City Hall"));
		
		allInmatesList.add(new Inmate(123457, "Toby", "Flenderson", new Date(10452013000L), "Alcatraz"));
		allInmatesList.add(new Inmate(123461, "Golden", "Face", new Date(10452013000L), "Alcatraz"));
	}
}
