package in.co.rays.project_3.dto;

import java.util.Date;

public class DeliveryTrackingDTO extends BaseDTO{
	
	private String orderNumber;
	private String customerName;
	private String deliveryStatus;
	private Date deliveryDate;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id +"";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return deliveryStatus;
	}
	

}
