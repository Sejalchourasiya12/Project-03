package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EnrollmentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EnrollmentModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "EnrollmentCtl", urlPatterns = { "/ctl/EnrollmentCtl" })
public class EnrollmentCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(EnrollmentCtl.class);

    private static final Pattern ENROLLMENT_CODE_PATTERN = 
    	    Pattern.compile("^[A-Z]{3}[0-9]{3}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("enrollmentCode"))) {

            request.setAttribute("enrollmentCode",
                    "Enrollment Code is required");

            pass = false;

        } else if (!ENROLLMENT_CODE_PATTERN.matcher(request.getParameter("enrollmentCode")).matches()) {

            request.setAttribute("enrollmentCode",
                    "Enrollment Code must be 3 Capital Letters followed by 3 Digits (e.g. RAY201)");

            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("studentName"))) {

            request.setAttribute("studentName",
                    PropertyReader.getValue("error.require", "Student Name"));

            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("enrollmentStatus"))) {

            request.setAttribute("enrollmentStatus",
                    PropertyReader.getValue("error.require", "Enrollment Status"));

            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("enrollmentDate"))) {

            request.setAttribute("enrollmentDate",
                    PropertyReader.getValue("error.require", "Enrollment Date"));

            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        EnrollmentDTO dto = new EnrollmentDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setEnrollmentCode(request.getParameter("enrollmentCode"));
        dto.setStudentName(request.getParameter("studentName"));
        dto.setEnrollmentStatus(request.getParameter("enrollmentStatus"));
        dto.setEnrollmentDate(DataUtility.getDate(request.getParameter("enrollmentDate")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String successMsg = (String) request.getSession().getAttribute("successMessage");

        if (successMsg != null) {

            ServletUtility.setSuccessMessage(successMsg, request);

            request.getSession().removeAttribute("successMessage");
        }

        long id = DataUtility.getLong(request.getParameter("id"));

        EnrollmentModelInt model = ModelFactory.getInstance().getEnrollmentModel();

        if (id > 0) {

            try {

                EnrollmentDTO dto = model.findByPK(id);

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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String op = DataUtility.getString(request.getParameter("operation"));

        EnrollmentModelInt model = ModelFactory.getInstance().getEnrollmentModel();

        EnrollmentDTO dto = (EnrollmentDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {

                    model.update(dto);

                    ServletUtility.setSuccessMessage("Enrollment Updated Successfully", request);

                    ServletUtility.setDto(dto, request);

                    ServletUtility.forward(getView(), request, response);

                    return;

                } else {

                    model.add(dto);

                    request.getSession().setAttribute("successMessage",
                            "Enrollment Added Successfully");

                    ServletUtility.redirect(ORSView.ENROLLMENT_CTL, request, response);

                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.ENROLLMENT_CTL, request, response);

                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.ENROLLMENT_LIST_CTL, request, response);

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

        return ORSView.ENROLLMENT_VIEW;
    }
}