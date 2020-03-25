package gov.phila.inmate.locator.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.phila.inmate.locator.Inmate;
import gov.phila.inmate.locator.exceptions.NoInmateFoundException;
import gov.phila.inmate.locator.services.InmateLocatorService;
import gov.phila.inmate.locator.web.InmateLocatorApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InmateLocatorApplication.class)
public class PublicInmateLocatorControllerTest {

	@Autowired
	@InjectMocks
	private PublicInmateLocatorController publicInmateLocatorController;

	@Mock
	private InmateLocatorService mockInmateLocatorService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetByPIDSuccessful() throws NoInmateFoundException {
		Mockito.when(mockInmateLocatorService.getInmateByPID(1))
				.thenReturn(new Inmate(1, "alex", "gorbachenko", new Date(), "home"));
		ResponseEntity<Inmate> response = publicInmateLocatorController.getInmateByPID(1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getpID());
	}

	@Test(expected = NoInmateFoundException.class)
	public void testGetByPIDNoInmateFound() throws NoInmateFoundException {
		Mockito.when(mockInmateLocatorService.getInmateByPID(1)).thenThrow(NoInmateFoundException.class);
		ResponseEntity<Inmate> response = publicInmateLocatorController.getInmateByPID(1);
		assertEquals("should have thrown an exception", response);
	}

	@Test
	public void testGetByPIDBadRequest() throws NoInmateFoundException {
		ResponseEntity<Inmate> response = publicInmateLocatorController.getInmateByPID(0);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testGetByNameAndDOBSuccessful() throws NoInmateFoundException {
		Mockito.when(mockInmateLocatorService.getInmateByNameAndDOB("alex", "gorbachenko", "08-04-1994"))
				.thenReturn(new Inmate(1, "alex", "gorbachenko", new Date(), "home"));
		ResponseEntity<Inmate> response = publicInmateLocatorController.getInmateByNameAndDOB("alex", "gorbachenko",
				"08-04-1994");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getpID());
	}

	@Test(expected = NoInmateFoundException.class)
	public void testGetByNameAndDOBNoInmateFound() throws NoInmateFoundException {
		Mockito.when(mockInmateLocatorService.getInmateByNameAndDOB("alex", "gorbachenko", "08-04-1994"))
				.thenThrow(NoInmateFoundException.class);
		ResponseEntity<Inmate> response = publicInmateLocatorController.getInmateByNameAndDOB("alex", "gorbachenko",
				"08-04-1994");
		assertEquals("should have thrown an exception", response);
	}
}
