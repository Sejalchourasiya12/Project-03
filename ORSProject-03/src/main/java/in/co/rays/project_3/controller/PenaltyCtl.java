package in.co.rays.project_3.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PenaltyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PenaltyModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "PenaltyCtl", urlPatterns = { "/ctl/PenaltyCtl" })
public class PenaltyCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(PenaltyCtl.class);

    // e.g. PEN001
    private static final Pattern PENALTY_CODE_PATTERN =
            Pattern.compile("^[A-Z]{3}[0-9]{3}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Penalty Code
        if (DataValidator.isNull(request.getParameter("penaltyCode"))) {
            request.setAttribute("penaltyCode",
                    PropertyReader.getValue("error.require", "Penalty Code"));
            pass = false;
        } else if (!PENALTY_CODE_PATTERN.matcher(request.getParameter("penaltyCode")).matches()) {
            request.setAttribute("penaltyCode",
                    "Penalty Code must be 3 Capital Letters followed by 3 Digits (e.g. PEN001)");
            pass = false;
        }

        // Penalty Reason
        if (DataValidator.isNull(request.getParameter("penaltyReason"))) {
            request.setAttribute("penaltyReason",
                    PropertyReader.getValue("error.require", "Penalty Reason"));
            pass = false;
        }

        // Penalty Amount
        if (DataValidator.isNull(request.getParameter("penaltyAmount"))) {
            request.setAttribute("penaltyAmount",
                    PropertyReader.getValue("error.require", "Penalty Amount"));
            pass = false;
        } else {
            try {
                BigDecimal amount = new BigDecimal(request.getParameter("penaltyAmount"));
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    request.setAttribute("penaltyAmount",
                            "Penalty Amount must be greater than 0");
                    pass = false;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("penaltyAmount",
                        "Penalty Amount must be a valid number");
                pass = false;
            }
        }

        // Penalty Status
        if (DataValidator.isNull(request.getParameter("penaltyStatus"))) {
            request.setAttribute("penaltyStatus",
                    PropertyReader.getValue("error.require", "Penalty Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        PenaltyDTO dto = new PenaltyDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setPenaltyCode(DataUtility.getString(request.getParameter("penaltyCode")));
        dto.setPenaltyReason(DataUtility.getString(request.getParameter("penaltyReason")));
        dto.setPenaltyStatus(DataUtility.getString(request.getParameter("penaltyStatus")));

        String amountStr = request.getParameter("penaltyAmount");
        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                dto.setPenaltyAmount(new BigDecimal(amountStr));
            } catch (NumberFormatException e) {
                dto.setPenaltyAmount(null);
            }
        }

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

        PenaltyModelInt model = ModelFactory.getInstance().getPenaltyModel();

        if (id > 0) {
            try {
                PenaltyDTO dto = model.findByPK(id);
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

        PenaltyModelInt model = ModelFactory.getInstance().getPenaltyModel();

        PenaltyDTO dto = (PenaltyDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {

                    model.update(dto);
                    ServletUtility.setSuccessMessage("Penalty Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;

                } else {

                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Penalty Added Successfully");
                    ServletUtility.redirect(ORSView.PENALTY_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.PENALTY_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.PENALTY_LIST_CTL, request, response);
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
        return ORSView.PENALTY_VIEW;
    }
}