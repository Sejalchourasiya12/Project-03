package in.co.rays.project_3.dto;

import java.util.Date;

public class PromotionDTO extends BaseDTO{
	
	private String promotionCode;

	private String promotionTitle;

	private Date startDate;

	private String promotionStatus;
	
	

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public String getPromotionTitle() {
		return promotionTitle;
	}

	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getPromotionStatus() {
		return promotionStatus;
	}

	public void setPromotionStatus(String promotionStatus) {
		this.promotionStatus = promotionStatus;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id +"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return promotionStatus;
	}

}
