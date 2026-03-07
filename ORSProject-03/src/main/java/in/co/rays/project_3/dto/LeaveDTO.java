package in.co.rays.project_3.dto;


import java.util.Date;

/**
 * LeaveDTO JavaDto encapsulates leave attributes
 * 
 */

/**
 * @author Sejal Chourasiya
 *
 */
public class LeaveDTO extends BaseDTO {

    private String leaveCode;       // UNIQUE
    private String employeeName;
    private Date leaveStartDate;
    private Date leaveEndDate;
    private String leaveStatus;

    @Override
    public String toString() {
        return "LeaveDTO [leaveCode=" + leaveCode 
                + ", employeeName=" + employeeName 
                + ", leaveStartDate=" + leaveStartDate
                + ", leaveEndDate=" + leaveEndDate 
                + ", leaveStatus=" + leaveStatus + "]";
    }

    // Getter & Setter

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getLeaveStartDate() {
        return leaveStartDate;
    }

    public void setLeaveStartDate(Date leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }

    public Date getLeaveEndDate() {
        return leaveEndDate;
    }

    public void setLeaveEndDate(Date leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    @Override
    public String getKey() {
        return String.valueOf(getId());   // BaseDTO se aayega
    }

    @Override
    public String getValue() {
        return leaveCode;  // meaningful value
    }
}