package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PolicyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PolicyModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "PolicyCtl", urlPatterns = { "/ctl/PolicyCtl" })
public class PolicyCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(PolicyCtl.class);

    // Policy Code Validation
    private static final Pattern POLICY_CODE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9]+$");

    // Policy Name Validation
    private static final Pattern POLICY_NAME_PATTERN =
            Pattern.compile("^[a-zA-Z ]+$");

    // Premium Amount Validation
    private static final Pattern PREMIUM_PATTERN =
            Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Policy Code
        if (DataValidator.isNull(request.getParameter("policyCode"))) {
            request.setAttribute("policyCode",
                    PropertyReader.getValue("error.require", "Policy Code"));
            pass = false;

        } else if (!POLICY_CODE_PATTERN.matcher(
                request.getParameter("policyCode").trim()).matches()) {

            request.setAttribute("policyCode",
                    "Policy Code must contain only alphabets and numbers");
            pass = false;
        }

        // Policy Name
        if (DataValidator.isNull(request.getParameter("policyName"))) {
            request.setAttribute("policyName",
                    PropertyReader.getValue("error.require", "Policy Name"));
            pass = false;

        } else if (!POLICY_NAME_PATTERN.matcher(
                request.getParameter("policyName").trim()).matches()) {

            request.setAttribute("policyName",
                    "Policy Name must contain alphabets only");
            pass = false;
        }

        // Premium Amount
        if (DataValidator.isNull(request.getParameter("premiumAmount"))) {
            request.setAttribute("premiumAmount",
                    PropertyReader.getValue("error.require", "Premium Amount"));
            pass = false;

        } else if (!PREMIUM_PATTERN.matcher(
                request.getParameter("premiumAmount").trim()).matches()) {

            request.setAttribute("premiumAmount",
                    "Premium Amount must be numeric");
            pass = false;
        }

        // Policy Status
        if (DataValidator.isNull(request.getParameter("policyStatus"))) {
            request.setAttribute("policyStatus",
                    PropertyReader.getValue("error.require", "Policy Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        PolicyDTO dto = new PolicyDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setPolicyCode(DataUtility.getString(request.getParameter("policyCode")));
        dto.setPolicyName(DataUtility.getString(request.getParameter("policyName")));
        dto.setPremiumAmount(DataUtility.getString(request.getParameter("premiumAmount")));
        dto.setPolicyStatus(DataUtility.getString(request.getParameter("policyStatus")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String successMsg =
                (String) request.getSession().getAttribute("successMessage");

        if (successMsg != null) {
            ServletUtility.setSuccessMessage(successMsg, request);
            request.getSession().removeAttribute("successMessage");
        }

        long id = DataUtility.getLong(request.getParameter("id"));

        PolicyModelInt model =
                ModelFactory.getInstance().getPolicyModel();

        if (id > 0) {
            try {

                PolicyDTO dto = model.findByPK(id);
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

        String op =
                DataUtility.getString(request.getParameter("operation"));

        PolicyModelInt model =
                ModelFactory.getInstance().getPolicyModel();

        PolicyDTO dto =
                (PolicyDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op)
                    || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {

                    model.update(dto);

                    ServletUtility.setSuccessMessage(
                            "Policy Updated Successfully", request);

                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(),
                            request, response);
                    return;

                } else {

                    model.add(dto);

                    request.getSession().setAttribute(
                            "successMessage",
                            "Policy Added Successfully");

                    ServletUtility.redirect(
                            ORSView.POLICY_CTL,
                            request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.POLICY_CTL,
                        request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.POLICY_LIST_CTL,
                        request, response);
                return;
            }

            ServletUtility.setDto(dto, request);
            ServletUtility.forward(getView(),
                    request, response);

        } catch (ApplicationException e) {

            log.error(e);
            ServletUtility.handleException(e,
                    request, response);

        } catch (DuplicateRecordException e) {

            ServletUtility.setDto(dto, request);
            ServletUtility.setErrorMessage(
                    e.getMessage(), request);

            ServletUtility.forward(getView(),
                    request, response);
        }
    }

    @Override
    protected String getView() {
        return ORSView.POLICY_VIEW;
    }
}