package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.AchievementDTO;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AchievementModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "AchievementCtl", urlPatterns = { "/ctl/AchievementCtl" })
public class AchievementCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(AchievementCtl.class);

    // e.g. ACH001
    private static final Pattern ACHIEVEMENT_CODE_PATTERN =
            Pattern.compile("^[A-Z]{3}[0-9]{3}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("achievementCode"))) {
            request.setAttribute("achievementCode",
                    PropertyReader.getValue("error.require", "Achievement Code"));
            pass = false;
        } else if (!ACHIEVEMENT_CODE_PATTERN.matcher(
                request.getParameter("achievementCode")).matches()) {
            request.setAttribute("achievementCode",
                    "Achievement Code must be 3 Capital Letters followed by 3 Digits (e.g. ACH001)");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("achievementName"))) {
            request.setAttribute("achievementName",
                    PropertyReader.getValue("error.require", "Achievement Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("earnedBy"))) {
            request.setAttribute("earnedBy",
                    PropertyReader.getValue("error.require", "Earned By"));
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

        AchievementDTO dto = new AchievementDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setAchievementCode(DataUtility.getString(request.getParameter("achievementCode")));
        dto.setAchievementName(DataUtility.getString(request.getParameter("achievementName")));
        dto.setEarnedBy(DataUtility.getString(request.getParameter("earnedBy")));
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
        AchievementModelInt model = ModelFactory.getInstance().getAchievementModel();

        if (id > 0) {
            try {
                AchievementDTO dto = model.findByPK(id);
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

        AchievementModelInt model = ModelFactory.getInstance().getAchievementModel();
        AchievementDTO dto = (AchievementDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Achievement Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Achievement Added Successfully");
                    ServletUtility.redirect(ORSView.ACHIEVEMENT_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ACHIEVEMENT_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ACHIEVEMENT_LIST_CTL, request, response);
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
        return ORSView.ACHIEVEMENT_VIEW;
    }
}