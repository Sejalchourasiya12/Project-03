package in.co.rays.project_3.dto;

public class TrackingDTO extends BaseDTO{
	
	private String trackingNumber;
	private String currentLocation;
	private String status;
	
	
	
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getKey() {
		
		return id +"";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return status;
	}

}
