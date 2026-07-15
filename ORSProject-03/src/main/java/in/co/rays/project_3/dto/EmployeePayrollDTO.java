package in.co.rays.project_3.dto;

import javax.persistence.*;

/**
 * EmployeePayroll DTO
 *
 * @author Sejal Chourasiya
 */
public class EmployeePayrollDTO extends BaseDTO {

    private String employeeName;
    private String department;
    private String salary;
    private String bonus;

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getBonus() { return bonus; }
    public void setBonus(String bonus) { this.bonus = bonus; }

    @Override
    public String getKey() {
        return getId() + "";
    }

    @Override
    public String getValue() {
        return department;
    }
}