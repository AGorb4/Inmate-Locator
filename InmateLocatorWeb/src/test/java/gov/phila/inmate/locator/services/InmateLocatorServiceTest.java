package gov.phila.inmate.locator.services;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.phila.inmate.locator.Inmate;
import gov.phila.inmate.locator.daos.InmateLocatorDAO;
import gov.phila.inmate.locator.exceptions.NoInmateFoundException;
import gov.phila.inmate.locator.web.InmateLocatorApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InmateLocatorApplication.class)
public class InmateLocatorServiceTest {

	@Autowired
	@InjectMocks
	private InmateLocatorServiceImpl inmateLocatorService;

	@Mock
	private InmateLocatorDAO mockInmateLocatorDao;

	private List<Inmate> inmateList;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		inmateList = new ArrayList<>();
		inmateList.add(new Inmate(123456, "Michael", "Scott", new Date(10452013000L), "City Hall"));
		inmateList.add(new Inmate(123458, "Dwight", "Shrute", new Date(10452013000L), "City Hall"));
		inmateList.add(new Inmate(123459, "Jim", "Halpert", new Date(10452013000L), "City Hall"));
		inmateList.add(new Inmate(123460, "Pam", "Beasley", new Date(10452013000L), "City Hall"));

		inmateList.add(new Inmate(123457, "Toby", "Flenderson", new Date(10452013000L), "Alcatraz"));
		inmateList.add(new Inmate(123461, "Golden", "Face", new Date(10452013000L), "Alcatraz"));
		Mockito.when(mockInmateLocatorDao.getAllInmates()).thenReturn(inmateList);
	}

	@Test
	public void testSuccessfulGetInmateByPID() throws NoInmateFoundException {
		Inmate inmate = inmateLocatorService.getInmateByPID(123456);
		assertEquals(123456, inmate.getpID());
	}

	@Test(expected = NoInmateFoundException.class)
	public void testGetInmateByPIDNoInmateFound() throws NoInmateFoundException {
		Inmate inmate = inmateLocatorService.getInmateByPID(1);
		assertEquals("should have thrown an exception", inmate);
	}

	@Test
	public void testSuccessfulGetInmateByNameAndDOB() throws NoInmateFoundException {
		Inmate inmate = inmateLocatorService.getInmateByNameAndDOB("Michael", "Scott", "05-01-1970");
		assertEquals(123456, inmate.getpID());
	}

	@Test(expected = NoInmateFoundException.class)
	public void testGetInmateByNameAndDOBNoInmateFound() throws NoInmateFoundException {
		Inmate inmate = inmateLocatorService.getInmateByNameAndDOB("Oscar", "Martinez", "05-01-1970");
		assertEquals("should have thrown an exception", inmate);
	}

	@Test
	public void testSuccessfulGetAllInmates() {
		List<Inmate> inmates = inmateLocatorService.getAllInmates();
		assertEquals(inmateList, inmates);
	}
	
	@Test
	public void testSuccessfulGetInmatesByLocation() {
		List<Inmate> inmates = inmateLocatorService.getInmatesByLocation("Alcatraz");
		assertEquals(2, inmates.size());
	}
}