package in.co.rays.project_3.dto;

/**
 * VehicleInsuranceDTO - Data Transfer Object for Vehicle Insurance
 *
 * @author Sejal Chourasiya
 */
public class VehicleInsuranceDTO extends BaseDTO {

    private String ownerName;
    private String vehicleNumber;
    private String vehicleType;
    private String insuranceCompany;

    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }
    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return vehicleType;
    }
}