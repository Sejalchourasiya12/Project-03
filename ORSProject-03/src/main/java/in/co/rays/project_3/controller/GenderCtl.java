package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.GenderDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.GenderModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "GenderCtl", urlPatterns = { "/ctl/GenderCtl" })
public class GenderCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(GenderCtl.class);

    // e.g. GEN001
    private static final Pattern GENDER_CODE_PATTERN =
            Pattern.compile("^[A-Z]{3}[0-9]{3}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("genderCode"))) {
            request.setAttribute("genderCode",
                    PropertyReader.getValue("error.require", "Gender Code"));
            pass = false;
        } else if (!GENDER_CODE_PATTERN.matcher(
                request.getParameter("genderCode")).matches()) {
            request.setAttribute("genderCode",
                    "Gender Code must be 3 Capital Letters followed by 3 Digits (e.g. GEN001)");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("genderType"))) {
            request.setAttribute("genderType",
                    PropertyReader.getValue("error.require", "Gender Type"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description",
                    PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        GenderDTO dto = new GenderDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setGenderCode(DataUtility.getString(request.getParameter("genderCode")));
        dto.setGenderType(DataUtility.getString(request.getParameter("genderType")));
        dto.setDescription(DataUtility.getString(request.getParameter("description")));
        dto.setStatus(DataUtility.getString(request.getParameter("status")));

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
        GenderModelInt model = ModelFactory.getInstance().getGenderModel();

        if (id > 0) {
            try {
                GenderDTO dto = model.findByPK(id);
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

        GenderModelInt model = ModelFactory.getInstance().getGenderModel();
        GenderDTO dto = (GenderDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Gender Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Gender Added Successfully");
                    ServletUtility.redirect(ORSView.GENDER_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.GENDER_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.GENDER_LIST_CTL, request, response);
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
        return ORSView.GENDER_VIEW;
    }
}