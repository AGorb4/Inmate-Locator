package gov.phila.inmate.locator.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.phila.inmate.locator.Inmate;
import gov.phila.inmate.locator.configs.JwtUtils;
import gov.phila.inmate.locator.exceptions.NoInmateFoundException;
import gov.phila.inmate.locator.services.InmateLocatorService;
import gov.phila.inmate.locator.web.InmateLocatorApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InmateLocatorApplication.class)
public class WardenInmateLocatorControllerTest {

	@Autowired
	@InjectMocks
	private WardenInmateLocatorController wardenInmateLocatorController;

	@Mock
	private InmateLocatorService mockInmateLocatorService;

	@Mock
	private JwtUtils jwtUtils;

	private List<Inmate> inmateList;

	private static final String authHeader = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIx"
			+ "MjM0NTY3ODkxIiwibmFtZSI6IldhcmRlbiIsInJvbGUiOiJ3YXJkZW4iLCJsb2NhdGlvbiI6IkFsY2F0cmF6Iiwi"
			+ "aWF0IjoxNTE2MjM5MDIyfQ.dVIceJwZutqWZlnwcwsSujOLI35TPxKoA9jPpD1TAZ4";

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		inmateList = new ArrayList<>();
		inmateList.add(new Inmate(1, "alex", "gorbachenko", new Date(), "Alcatraz"));
	}

	@WithMockUser(authorities = { "warden" })
	@Test
	public void testGetInmateByPIDSuccessful() throws NoInmateFoundException {
		Mockito.when(mockInmateLocatorService.getInmateByPIDWarden(1, "Alcatraz")).thenReturn(inmateList.get(0));
		ResponseEntity<Inmate> response = wardenInmateLocatorController.getInmateByPID(authHeader, 1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getpID());
	}

	@WithMockUser(authorities = { "warden" })
	@Test
	public void testGetInmateByPIDBadRequest() throws NoInmateFoundException {
		ResponseEntity<Inmate> response = wardenInmateLocatorController.getInmateByPID(authHeader, 0);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@WithMockUser(authorities = { "warden" })
	@Test(expected = NoInmateFoundException.class)
	public void testGetInmateByPIDNoInmateFound() throws NoInmateFoundException {
		Mockito.when(mockInmateLocatorService.getInmateByPIDWarden(1, "Alcatraz"))
				.thenThrow(NoInmateFoundException.class);
		ResponseEntity<Inmate> response = wardenInmateLocatorController.getInmateByPID(authHeader, 1);
		assertEquals("should have thrown an exception", response);
	}

	@WithMockUser(authorities = { "warden" })
	@Test
	public void testGetInmatesSuccessful() {
		Mockito.when(mockInmateLocatorService.getInmatesByLocation("Alcatraz")).thenReturn(inmateList);
		ResponseEntity<List<Inmate>> response = wardenInmateLocatorController.getInmates(authHeader);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(inmateList, response.getBody());
	}

	@WithMockUser(authorities = { "Administrator" })
	@Test(expected = AccessDeniedException.class)
	public void testGetInmatesAccessDenied() {
		ResponseEntity<List<Inmate>> response = wardenInmateLocatorController.getInmates(authHeader);
		assertEquals("should have thrown an exception", response);
	}
}
