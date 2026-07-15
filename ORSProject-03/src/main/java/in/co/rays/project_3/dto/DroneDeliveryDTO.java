package in.co.rays.project_3.dto;

/**
 * DroneDeliveryDTO - Data Transfer Object for Drone Delivery
 *
 * @author Sejal Chourasiya
 */
public class DroneDeliveryDTO extends BaseDTO {

    private String droneCode;
    private String operatorName;
    private String deliveryZone;
    private String status;

    public String getDroneCode() {
        return droneCode;
    }
    public void setDroneCode(String droneCode) {
        this.droneCode = droneCode;
    }

    public String getOperatorName() {
        return operatorName;
    }
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getDeliveryZone() {
        return deliveryZone;
    }
    public void setDeliveryZone(String deliveryZone) {
        this.deliveryZone = deliveryZone;
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