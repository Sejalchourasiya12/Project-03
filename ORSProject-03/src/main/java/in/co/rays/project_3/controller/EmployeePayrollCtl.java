package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EmployeePayrollDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EmployeePayrollModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * EmployeePayroll Controller
 *
 * @author Sejal Chourasiya
 */
@WebServlet(name = "EmployeePayrollCtl", urlPatterns = { "/ctl/EmployeePayrollCtl" })
public class EmployeePayrollCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(EmployeePayrollCtl.class);
    
    private static final Pattern ALPHA_PATTERN =
            Pattern.compile("^[a-zA-Z ]+$");
    
    private static final Pattern PRICE_PATTERN =
            Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");


    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("employeeName"))) {
            request.setAttribute("employeeName",
                    PropertyReader.getValue("error.require", "Employee Name"));
            pass = false;
        }else if (!ALPHA_PATTERN.matcher(
                request.getParameter("employeeName").trim()).matches()) {
            request.setAttribute("employeeName",
                    "Employee Name must contain alphabets only");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("department"))) {
            request.setAttribute("department",
                    PropertyReader.getValue("error.require", "Department"));
            pass = false;
        }else if (!ALPHA_PATTERN.matcher(
                request.getParameter("department").trim()).matches()) {
            request.setAttribute("department",
                    "Department Name must contain alphabets only");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("salary"))) {
            request.setAttribute("salary",
                    PropertyReader.getValue("error.require", "Salary"));
            pass = false;
        }else if (!PRICE_PATTERN.matcher(
                request.getParameter("salary").trim()).matches()) {
            request.setAttribute("salary",
                    "Salary must be a valid number (e.g. 299 or 299.99)");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("bonus"))) {
            request.setAttribute("bonus",
                    PropertyReader.getValue("error.require", "Bonus"));
            pass = false;
        }else if (!PRICE_PATTERN.matcher(
                request.getParameter("bonus").trim()).matches()) {
            request.setAttribute("bonus",
                    "Bonus must be a valid number (e.g. 299 or 299.99)");
            pass = false;
        }
        

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        EmployeePayrollDTO dto = new EmployeePayrollDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setEmployeeName(DataUtility.getString(request.getParameter("employeeName")));
        dto.setDepartment(DataUtility.getString(request.getParameter("department")));
        dto.setSalary(DataUtility.getString(request.getParameter("salary")));
        dto.setBonus(DataUtility.getString(request.getParameter("bonus")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String successMsg = (String) request.getSession().getAttribute("successMessage");
        if (successMsg != null) {
            ServletUtility.setSuccessMessage(successMsg, request);
            request.getSession().removeAttribute("successMessage");
        }

        long id = DataUtility.getLong(request.getParameter("id"));
        EmployeePayrollModelInt model = ModelFactory.getInstance().getEmployeePayrollModel();

        if (id > 0) {
            try {
                EmployeePayrollDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, request);
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String op = DataUtility.getString(request.getParameter("operation"));

        EmployeePayrollModelInt model = ModelFactory.getInstance().getEmployeePayrollModel();
        EmployeePayrollDTO dto = (EmployeePayrollDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Employee Payroll Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Employee Payroll Added Successfully");
                    ServletUtility.redirect(ORSView.EMPLOYEE_PAYROLL_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.EMPLOYEE_PAYROLL_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.EMPLOYEE_PAYROLL_LIST_CTL, request, response);
                return;
            }

            ServletUtility.setDto(dto, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
        } catch (DuplicateRecordException e) {
            ServletUtility.setDto(dto, request);
            ServletUtility.setErrorMessage(e.getMessage(), request);
            ServletUtility.forward(getView(), request, response);
        }
    }

    @Override
    protected String getView() {
        return ORSView.EMPLOYEE_PAYROLL_VIEW;
    }
}