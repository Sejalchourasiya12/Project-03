package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.EmployeePayrollDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * EmployeePayroll Model Interface
 *
 * @author Sejal Chourasiya
 */
public interface EmployeePayrollModelInt {

    public long add(EmployeePayrollDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(EmployeePayrollDTO dto) throws ApplicationException;

    public void update(EmployeePayrollDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(EmployeePayrollDTO dto) throws ApplicationException;

    public List search(EmployeePayrollDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public EmployeePayrollDTO findByPK(long pk) throws ApplicationException;

    public EmployeePayrollDTO findByEmployeeName(String employeeName) throws ApplicationException;
}