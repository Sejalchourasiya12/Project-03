package in.co.rays.project_3.dto;

/**
 * EnergyConsumptionDTO - Data Transfer Object for Energy Consumption
 *
 * @author Sejal Chourasiya
 */
public class EnergyConsumptionDTO extends BaseDTO {

    private String energyCode;
    private String deviceName;
    private String unitsConsumed;
    private String status;

    public String getEnergyCode() {
        return energyCode;
    }
    public void setEnergyCode(String energyCode) {
        this.energyCode = energyCode;
    }

    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getUnitsConsumed() {
        return unitsConsumed;
    }
    public void setUnitsConsumed(String unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
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