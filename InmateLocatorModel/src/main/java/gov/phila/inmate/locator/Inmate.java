package gov.phila.inmate.locator;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Inmate {

	private int pID;
	private String firstName;
	private String lastName;
	@JsonFormat(pattern="MM-dd-yyyy")
	private Date dob;
	private String location;

	public Inmate(int pID, String firstName, String lastName, Date dob, String location) {
		super();
		this.pID = pID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.location = location;
	}

	public int getpID() {
		return pID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getDob() {
		return dob;
	}

	public String getLocation() {
		return location;
	}
}