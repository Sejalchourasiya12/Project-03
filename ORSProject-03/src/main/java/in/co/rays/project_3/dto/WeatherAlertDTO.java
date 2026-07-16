package in.co.rays.project_3.dto;

/**
 * WeatherAlertDTO - Data Transfer Object for Weather Alert
 *
 * @author Sejal Chourasiya
 */
public class WeatherAlertDTO extends BaseDTO {

    private String alertCode;
    private String cityName;
    private String temperature;
    private String status;

    public String getAlertCode() {
        return alertCode;
    }
    public void setAlertCode(String alertCode) {
        this.alertCode = alertCode;
    }

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
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