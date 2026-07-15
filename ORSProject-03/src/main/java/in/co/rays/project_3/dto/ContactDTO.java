package in.co.rays.project_3.dto;

import java.util.Date;

public class ContactDTO extends BaseDTO {
    private Long id;
	private String name;
	private String city;
	private Date dob;
	private String mobile;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return mobile;
	}
	
	
	
}
