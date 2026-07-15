package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.ATMDTO;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ATMModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ATMCtl", urlPatterns = { "/ctl/ATMCtl" })
public class ATMCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(ATMCtl.class);

    // e.g. ATM001
    private static final Pattern SECURITY_CODE_PATTERN =
            Pattern.compile("^[A-Z]{3}[0-9]{3}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("securityCode"))) {
            request.setAttribute("securityCode",
                    PropertyReader.getValue("error.require", "Security Code"));
            pass = false;
        } else if (!SECURITY_CODE_PATTERN.matcher(
                request.getParameter("securityCode")).matches()) {
            request.setAttribute("securityCode",
                    "Security Code must be 3 Capital Letters followed by 3 Digits (e.g. ATM001)");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("bankName"))) {
            request.setAttribute("bankName",
                    PropertyReader.getValue("error.require", "Bank Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("location"))) {
            request.setAttribute("location",
                    PropertyReader.getValue("error.require", "Location"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("cashAvailable"))) {
            request.setAttribute("cashAvailable",
                    PropertyReader.getValue("error.require", "Cash Available"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        ATMDTO dto = new ATMDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setSecurityCode(DataUtility.getString(request.getParameter("securityCode")));
        dto.setBankName(DataUtility.getString(request.getParameter("bankName")));
        dto.setLocation(DataUtility.getString(request.getParameter("location")));
        dto.setCashAvailable(DataUtility.getString(request.getParameter("cashAvailable")));

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
        ATMModelInt model = ModelFactory.getInstance().getATMModel();

        if (id > 0) {
            try {
                ATMDTO dto = model.findByPK(id);
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

        ATMModelInt model = ModelFactory.getInstance().getATMModel();
        ATMDTO dto = (ATMDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("ATM Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "ATM Added Successfully");
                    ServletUtility.redirect(ORSView.ATM_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ATM_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ATM_LIST_CTL, request, response);
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
        return ORSView.ATM_VIEW;
    }
}