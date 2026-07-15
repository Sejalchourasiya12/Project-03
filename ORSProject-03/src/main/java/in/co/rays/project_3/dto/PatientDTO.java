package in.co.rays.project_3.dto;

import java.util.Date;

public class PatientDTO extends BaseDTO {

	
	private String name;
	private Date dob;
	private String mobileNo;
	private String decease;

	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDecease() {
		return decease;
	}

	public void setDecease(String decease) {
		this.decease = decease;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return decease;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return decease;
	}

}
