package in.co.rays.project_3.dto;

/**
 * SmartParkingDTO - Data Transfer Object for Smart Parking
 *
 * @author Sejal Chourasiya
 */
public class SmartParkingDTO extends BaseDTO {

    private String parkingCode;
    private String vechicleNumber;
    private String slotNumber;
    private String status;

    public String getParkingCode() {
        return parkingCode;
    }
    public void setParkingCode(String parkingCode) {
        this.parkingCode = parkingCode;
    }

    public String getVechicleNumber() {
        return vechicleNumber;
    }
    public void setVechicleNumber(String vechicleNumber) {
        this.vechicleNumber = vechicleNumber;
    }

    public String getSlotNumber() {
        return slotNumber;
    }
    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return status;
    }
}