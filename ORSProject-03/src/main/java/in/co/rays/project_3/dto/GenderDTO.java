package in.co.rays.project_3.dto;

public class GenderDTO extends BaseDTO {
	
	private String genderCode;
	private String genderType;
	private String description;
	private String status;
	
	
	public String getGenderCode() {
		return genderCode;
	}
	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
	public String getGenderType() {
		return genderType;
	}
	public void setGenderType(String genderType) {
		this.genderType = genderType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}
	@Override
	public String getValue(){
		// TODO Auto-generated method stub
		return genderType;
	}
	

}
