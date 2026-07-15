package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.VoiceCommandDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.VoiceCommandModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "VoiceCommandCtl", urlPatterns = { "/ctl/VoiceCommandCtl" })
public class VoiceCommandCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(VoiceCommandCtl.class);

    // Command Code: alphanumeric, hyphens, underscores (e.g. CMD-001)
    private static final Pattern COMMAND_CODE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_\\-]+$");

    // Username: alphanumeric, dots, underscores (e.g. rahul.sharma, rahul_01)
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._]+$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Command Code — required + valid format
        if (DataValidator.isNull(request.getParameter("commandCode"))) {
            request.setAttribute("commandCode",
                    PropertyReader.getValue("error.require", "Command Code"));
            pass = false;
        } else if (!COMMAND_CODE_PATTERN.matcher(
                request.getParameter("commandCode").trim()).matches()) {
            request.setAttribute("commandCode",
                    "Command Code must be alphanumeric (e.g. CMD-001)");
            pass = false;
        }

        // Username — required + valid format
        if (DataValidator.isNull(request.getParameter("username"))) {
            request.setAttribute("username",
                    PropertyReader.getValue("error.require", "Username"));
            pass = false;
        } else if (!USERNAME_PATTERN.matcher(
                request.getParameter("username").trim()).matches()) {
            request.setAttribute("username",
                    "Username must contain alphabets, numbers, dots or underscores only");
            pass = false;
        }

        // Command Text — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("commandText"))) {
            request.setAttribute("commandText",
                    PropertyReader.getValue("error.require", "Command Text"));
            pass = false;
        }

        // Status — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        VoiceCommandDTO dto = new VoiceCommandDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setCommandCode(DataUtility.getString(request.getParameter("commandCode")));
        dto.setUsername(DataUtility.getString(request.getParameter("username")));
        dto.setCommandText(DataUtility.getString(request.getParameter("commandText")));
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
        VoiceCommandModelInt model = ModelFactory.getInstance().getVoiceCommandModel();

        if (id > 0) {
            try {
                VoiceCommandDTO dto = model.findByPK(id);
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

        VoiceCommandModelInt model = ModelFactory.getInstance().getVoiceCommandModel();
        VoiceCommandDTO dto = (VoiceCommandDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Voice Command Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Voice Command Added Successfully");
                    ServletUtility.redirect(ORSView.VOICE_COMMAND_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.VOICE_COMMAND_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.VOICE_COMMAND_LIST_CTL, request, response);
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
        return ORSView.VOICE_COMMAND_VIEW;
    }
}