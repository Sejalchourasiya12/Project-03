package in.co.rays.project_3.dto;

public class ATMDTO extends BaseDTO {

    private Long id;
    private String securityCode;
    private String bankName;
    private String location;
    private String cashAvailable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCashAvailable() {
        return cashAvailable;
    }

    public void setCashAvailable(String cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return location;
    }
}