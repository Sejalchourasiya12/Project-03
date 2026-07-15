package in.co.rays.project_3.dto;

/**
 * VoiceCommandDTO - Data Transfer Object for Voice Command
 *
 * @author Sejal Chourasiya
 */
public class VoiceCommandDTO extends BaseDTO {

    private String commandCode;
    private String username;
    private String commandText;
    private String status;

    public String getCommandCode() {
        return commandCode;
    }
    public void setCommandCode(String commandCode) {
        this.commandCode = commandCode;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommandText() {
        return commandText;
    }
    public void setCommandText(String commandText) {
        this.commandText = commandText;
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