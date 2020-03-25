package gov.phila.inmate.locator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.phila.inmate.locator.Inmate;
import gov.phila.inmate.locator.exceptions.NoInmateFoundException;
import gov.phila.inmate.locator.services.InmateLocatorService;

@RestController
@RequestMapping("/api/public")
public class PublicInmateLocatorController {

	@Autowired
	private InmateLocatorService inmateLocatorService;

	@GetMapping(value = "/inmate/{pID}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Inmate> getInmateByPID(@PathVariable("pID") int pID) throws NoInmateFoundException {
		if (pID == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(inmateLocatorService.getInmateByPID(pID), HttpStatus.OK);
	}

	@GetMapping(value = "/inmate", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Inmate> getInmateByNameAndDOB(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("dob") String dob) throws NoInmateFoundException {
		return new ResponseEntity<>(inmateLocatorService.getInmateByNameAndDOB(firstName, lastName, dob),
				HttpStatus.OK);

	}
}