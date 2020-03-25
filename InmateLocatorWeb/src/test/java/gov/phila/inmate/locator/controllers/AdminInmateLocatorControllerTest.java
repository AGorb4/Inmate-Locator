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
import gov.phila.inmate.locator.services.InmateLocatorService;
import gov.phila.inmate.locator.web.InmateLocatorApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InmateLocatorApplication.class)
public class AdminInmateLocatorControllerTest {

	@Autowired
	@InjectMocks
	private AdminInmateLocatorController adminInmateLocatorController;

	@Mock
	private InmateLocatorService mockInmateLocatorService;

	private List<Inmate> inmateList;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		inmateList = new ArrayList<>();
		inmateList.add(new Inmate(1, "alex", "gorbachenko", new Date(), "home"));
	}

	@WithMockUser(authorities = { "Administrator" })
	@Test
	public void testGetAllInmatesSuccessful() {
		Mockito.when(mockInmateLocatorService.getAllInmates()).thenReturn(inmateList);
		ResponseEntity<List<Inmate>> response = adminInmateLocatorController.getAllInmates();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().get(0).getpID());
	}

	@WithMockUser(authorities = { "warden" })
	@Test(expected = AccessDeniedException.class)
	public void testGetAllInmatesAccessDenied() {
		ResponseEntity<List<Inmate>> response = adminInmateLocatorController.getAllInmates();
		assertEquals("should have thrown an exception", response);
	}

	@WithMockUser(authorities = { "Administrator" })
	@Test
	public void testGetInmatesByLocationSuccessful() {
		Mockito.when(mockInmateLocatorService.getInmatesByLocation("Home")).thenReturn(inmateList);
		ResponseEntity<List<Inmate>> response = adminInmateLocatorController.getInmatesByLocation("Home");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().get(0).getpID());
	}

	@WithMockUser(authorities = { "warden" })
	@Test(expected = AccessDeniedException.class)
	public void testGetAllInmatesByLocationAccessDenied() {
		ResponseEntity<List<Inmate>> response = adminInmateLocatorController.getInmatesByLocation("Home");
		assertEquals("should have thrown an exception", response);
	}
}
