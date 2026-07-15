package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.AIRecommendationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AIRecommendationModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "AIRecommendationCtl", urlPatterns = { "/ctl/AIRecommendationCtl" })
public class AIRecommendationCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(AIRecommendationCtl.class);

    // Recommendation Code: alphanumeric, hyphens, underscores (e.g. REC-001)
    private static final Pattern REC_CODE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_\\-]+$");

    // Username: alphanumeric, dots, underscores (e.g. rahul.sharma, rahul_01)
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._]+$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Recommendation Code — required + valid format
        if (DataValidator.isNull(request.getParameter("recommendationCode"))) {
            request.setAttribute("recommendationCode",
                    PropertyReader.getValue("error.require", "Recommendation Code"));
            pass = false;
        } else if (!REC_CODE_PATTERN.matcher(
                request.getParameter("recommendationCode").trim()).matches()) {
            request.setAttribute("recommendationCode",
                    "Recommendation Code must be alphanumeric (e.g. REC-001)");
            pass = false;
        }

        // Username — required + valid format
        if (DataValidator.isNull(request.getParameter("userName"))) {
            request.setAttribute("userName",
                    PropertyReader.getValue("error.require", "Username"));
            pass = false;
        } else if (!USERNAME_PATTERN.matcher(
                request.getParameter("userName").trim()).matches()) {
            request.setAttribute("userName",
                    "Username must contain alphabets, numbers, dots or underscores only");
            pass = false;
        }

        // Recommendation Type — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("recommendationType"))) {
            request.setAttribute("recommendationType",
                    PropertyReader.getValue("error.require", "Recommendation Type"));
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

        AIRecommendationDTO dto = new AIRecommendationDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setRecommendationCode(DataUtility.getString(request.getParameter("recommendationCode")));
        dto.setUserName(DataUtility.getString(request.getParameter("userName")));
        dto.setRecommendationType(DataUtility.getString(request.getParameter("recommendationType")));
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
        AIRecommendationModelInt model = ModelFactory.getInstance().getAIRecommendationModel();

        if (id > 0) {
            try {
                AIRecommendationDTO dto = model.findByPK(id);
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

        AIRecommendationModelInt model = ModelFactory.getInstance().getAIRecommendationModel();
        AIRecommendationDTO dto = (AIRecommendationDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("AI Recommendation Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "AI Recommendation Added Successfully");
                    ServletUtility.redirect(ORSView.AI_RECOMMENDATION_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.AI_RECOMMENDATION_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.AI_RECOMMENDATION_LIST_CTL, request, response);
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
        return ORSView.AI_RECOMMENDATION_VIEW;
    }
}