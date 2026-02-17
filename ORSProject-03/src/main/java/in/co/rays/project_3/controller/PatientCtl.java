package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PatientDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PatientModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "PatientCtl", urlPatterns = { "/ctl/PatientCtl" })
public class PatientCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(PatientCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name",
                    PropertyReader.getValue("error.require", "Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.require", "Date of Birth"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo",
                    PropertyReader.getValue("error.require", "Mobile No"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("decease"))) {
            request.setAttribute("decease",
                    PropertyReader.getValue("error.require", "Decease"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        PatientDTO dto = new PatientDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setName(DataUtility.getString(request.getParameter("name")));
        dto.setDob(DataUtility.getDate(request.getParameter("dob")));
        dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        dto.setDecease(DataUtility.getString(request.getParameter("decease")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        long id = DataUtility.getLong(request.getParameter("id"));

        PatientModelInt model = ModelFactory.getInstance().getPatientModel();

        if (id > 0) {
            try {
                PatientDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, request);
            } catch (ApplicationException e) {
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

        PatientModelInt model = ModelFactory.getInstance().getPatientModel();
        PatientDTO dto = (PatientDTO) populateDTO(request);

        if (OP_SAVE.equalsIgnoreCase(op)
                || OP_UPDATE.equalsIgnoreCase(op)) {

            try {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage(
                            "Patient updated successfully", request);
                } else {
                    model.add(dto);
                    ServletUtility.setSuccessMessage(
                            "Patient added successfully", request);
                }

            } catch (DuplicateRecordException e) {
                ServletUtility.setErrorMessage(
                        "Patient already exists", request);
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            try {
                model.delete(dto);
                ServletUtility.redirect(
                        ORSView.PATIENT_LIST_CTL, request, response);
                return;
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(
                    ORSView.PATIENT_LIST_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.PATIENT_VIEW;
    }
}
