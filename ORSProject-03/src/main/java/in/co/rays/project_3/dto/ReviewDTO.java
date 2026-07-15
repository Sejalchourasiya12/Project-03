package in.co.rays.project_3.dto;

public class ReviewDTO extends BaseDTO {

	
	private String reviewCode ;
	private Long userId;
	private Integer rating;
	private String comment;
	
	
	public String getReviewCode() {
		return reviewCode;
	}
	public void setReviewCode(String reviewCode) {
		this.reviewCode = reviewCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id +"";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return comment;
	}
}
