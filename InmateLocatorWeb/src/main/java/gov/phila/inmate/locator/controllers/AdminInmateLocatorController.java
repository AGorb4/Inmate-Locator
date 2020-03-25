package gov.phila.inmate.locator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.phila.inmate.locator.Inmate;
import gov.phila.inmate.locator.services.InmateLocatorService;

@RestController
@RequestMapping("/api/admin")
public class AdminInmateLocatorController {

	private InmateLocatorService inmateLocatorService;

	@Autowired
	public void setInmateLocatorService(InmateLocatorService inmateLocatorService) {
		this.inmateLocatorService = inmateLocatorService;
	}

	@GetMapping(value = "/inmates", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('Administrator')")
	public ResponseEntity<List<Inmate>> getAllInmates() {
		return new ResponseEntity<>(inmateLocatorService.getAllInmates(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/inmates/{location}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('Administrator')")
	public ResponseEntity<List<Inmate>> getInmatesByLocation(@PathVariable("location") String location) {
		if(location.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(inmateLocatorService.getInmatesByLocation(location), HttpStatus.OK);
	}
}
