package gov.phila.inmate.locator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;

import gov.phila.inmate.locator.Inmate;
import gov.phila.inmate.locator.configs.JwtUtils;
import gov.phila.inmate.locator.exceptions.NoInmateFoundException;
import gov.phila.inmate.locator.services.InmateLocatorService;

@RestController
@RequestMapping("/api/warden")
public class WardenInmateLocatorController {

	private InmateLocatorService inmateLocatorService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	public void setInmateLocatorService(InmateLocatorService inmateLocatorService) {
		this.inmateLocatorService = inmateLocatorService;
	}

	@GetMapping(value = "/inmate/{pID}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('warden')")
	public ResponseEntity<Inmate> getInmateByPID(@RequestHeader("Authorization") String authHeader,
			@PathVariable("pID") int pID) throws NoInmateFoundException {
		if (pID == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		DecodedJWT jwt = jwtUtils.getDecodedJwt(authHeader.substring(7));
		String location = jwtUtils.getLocation(jwt);
		return new ResponseEntity<>(inmateLocatorService.getInmateByPIDWarden(pID,location), HttpStatus.OK);
	}

	@GetMapping(value = "/inmates", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('warden')")
	public ResponseEntity<List<Inmate>> getInmates(@RequestHeader("Authorization") String authHeader) {
		DecodedJWT jwt = jwtUtils.getDecodedJwt(authHeader.substring(7));
		String location = jwtUtils.getLocation(jwt);
		return new ResponseEntity<>(inmateLocatorService.getInmatesByLocation(location), HttpStatus.OK);
	}
}