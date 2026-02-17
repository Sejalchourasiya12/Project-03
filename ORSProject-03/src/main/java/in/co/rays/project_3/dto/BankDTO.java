package in.co.rays.project_3.dto;

import java.util.Date;

/**
 * Bank JavaDTO encapsulates bank account attributes
 * 
 * @author Sejal Chourasiya
 */
public class BankDTO extends BaseDTO {

	private String accountNo;
	private String accountHolder;
	private String accountType;
	private Date openingDate;
	private double balance;
	private double accountLimit;



	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getAccountLimit() {
		return accountLimit;
	}

	public void setAccountLimit(double accountLimit) {
		this.accountLimit = accountLimit;
	}

	

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return accountNo + " - " + accountHolder;
	}
}
