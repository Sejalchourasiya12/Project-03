package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SubscriptionPlanDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SubscriptionPlanModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "SubscriptionPlanCtl", urlPatterns = { "/ctl/SubscriptionPlanCtl" })
public class SubscriptionPlanCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(SubscriptionPlanCtl.class);

    private static final Pattern PLAN_NAME_PATTERN = Pattern.compile("^[a-zA-Z ]+$");

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("planName"))) {
            request.setAttribute("planName",
                    PropertyReader.getValue("error.require", "Plan Name"));
            pass = false;
        } else if (!PLAN_NAME_PATTERN.matcher(request.getParameter("planName")).matches()) {  // ✅ sirf ek }
            request.setAttribute("planName", "Plan Name must contain alphabets only");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("price"))) {
            request.setAttribute("price",
                    PropertyReader.getValue("error.require", "Price"));
            pass = false;
        } else if (DataUtility.getDouble(request.getParameter("price")) <= 0) {
            request.setAttribute("price",
                    "Price must be greater than 0");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("validityDays"))) {
            request.setAttribute("validityDays",
                    PropertyReader.getValue("error.require", "Validity Days"));
            pass = false;
        }else if (DataUtility.getDouble(request.getParameter("validityDays")) <= 0) {
            request.setAttribute("validityDays",
                    "validityDays not contain minus value");
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        SubscriptionPlanDTO dto = new SubscriptionPlanDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setPlanName(request.getParameter("planName"));
        dto.setPrice(DataUtility.getDouble(request.getParameter("price")));
        dto.setValidityDays(DataUtility.getInt(request.getParameter("validityDays")));

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

        SubscriptionPlanModelInt model = ModelFactory.getInstance().getSubscriptionPlanModel();

        if (id > 0) {
            try {
                SubscriptionPlanDTO dto = model.findByPK(id);
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

        SubscriptionPlanModelInt model = ModelFactory.getInstance().getSubscriptionPlanModel();

        SubscriptionPlanDTO dto = (SubscriptionPlanDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {

                    model.update(dto);
                    ServletUtility.setSuccessMessage("Subscription Plan Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;

                } else {

                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Subscription Plan Added Successfully");
                    ServletUtility.redirect(ORSView.SUBSCRIPTION_PLAN_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.SUBSCRIPTION_PLAN_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.SUBSCRIPTION_PLAN_LIST_CTL, request, response);
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
        return ORSView.SUBSCRIPTION_PLAN_VIEW;
    }
}