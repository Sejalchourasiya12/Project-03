package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.UserProfileDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.UserProfileModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "UserProfileCtl", urlPatterns = { "/ctl/UserProfileCtl" })
public class UserProfileCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(UserProfileCtl.class);

    // e.g. PRF001
    private static final Pattern PROFILE_CODE_PATTERN =
            Pattern.compile("^[A-Z]{3}[0-9]{3}$");

    // Only alphabets and spaces for userName
    private static final Pattern USER_NAME_PATTERN =
            Pattern.compile("^[a-zA-Z ]+$");

    // 10 digit mobile number
    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^[0-9]{10}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Profile Code
        if (DataValidator.isNull(request.getParameter("profileCode"))) {
            request.setAttribute("profileCode",
                    PropertyReader.getValue("error.require", "Profile Code"));
            pass = false;
        } else if (!PROFILE_CODE_PATTERN.matcher(
                request.getParameter("profileCode")).matches()) {
            request.setAttribute("profileCode",
                    "Profile Code must be 3 Capital Letters followed by 3 Digits (e.g. PRF001)");
            pass = false;
        }

        // User Name
        if (DataValidator.isNull(request.getParameter("userName"))) {
            request.setAttribute("userName",
                    PropertyReader.getValue("error.require", "User Name"));
            pass = false;
        } else if (!USER_NAME_PATTERN.matcher(
                request.getParameter("userName")).matches()) {
            request.setAttribute("userName",
                    "User Name must contain alphabets only");
            pass = false;
        }

        // Mobile Number
        if (DataValidator.isNull(request.getParameter("mobileNumber"))) {
            request.setAttribute("mobileNumber",
                    PropertyReader.getValue("error.require", "Mobile Number"));
            pass = false;
        } else if (!MOBILE_PATTERN.matcher(
                request.getParameter("mobileNumber")).matches()) {
            request.setAttribute("mobileNumber",
                    "Mobile Number must be 10 digits");
            pass = false;
        }

        // Status
        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        UserProfileDTO dto = new UserProfileDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setProfileCode(DataUtility.getString(request.getParameter("profileCode")));
        dto.setUserName(DataUtility.getString(request.getParameter("userName")));
        dto.setMobileNumber(DataUtility.getString(request.getParameter("mobileNumber")));
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

        UserProfileModelInt model = ModelFactory.getInstance().getUserProfileModel();

        if (id > 0) {
            try {
                UserProfileDTO dto = model.findByPK(id);
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

        UserProfileModelInt model = ModelFactory.getInstance().getUserProfileModel();

        UserProfileDTO dto = (UserProfileDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {

                    model.update(dto);
                    ServletUtility.setSuccessMessage("User Profile Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;

                } else {

                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "User Profile Added Successfully");
                    ServletUtility.redirect(ORSView.USER_PROFILE_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.USER_PROFILE_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.USER_PROFILE_LIST_CTL, request, response);
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
        return ORSView.USER_PROFILE_VIEW;
    }
}