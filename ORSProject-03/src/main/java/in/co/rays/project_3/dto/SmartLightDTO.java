package in.co.rays.project_3.dto;

public class SmartLightDTO extends BaseDTO {

    private Long id;
    private String lightCode;
    private String roomName;
    private Integer brightnessLevel;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLightCode() {
        return lightCode;
    }

    public void setLightCode(String lightCode) {
        this.lightCode = lightCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(Integer brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
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