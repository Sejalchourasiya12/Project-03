package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ConsumerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ConsumerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ConsumerCtl", urlPatterns = { "/ctl/ConsumerCtl" })
public class ConsumerCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(ConsumerCtl.class);

    // e.g. CON001
    private static final Pattern CONSUMER_CODE_PATTERN =
            Pattern.compile("^[A-Z]{3}[0-9]{3}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Consumer Code
        if (DataValidator.isNull(request.getParameter("consumerCode"))) {
            request.setAttribute("consumerCode",
                    PropertyReader.getValue("error.require", "Consumer Code"));
            pass = false;
        } else if (!CONSUMER_CODE_PATTERN.matcher(request.getParameter("consumerCode")).matches()) {
            request.setAttribute("consumerCode",
                    "Consumer Code must be 3 Capital Letters followed by 3 Digits (e.g. CON001)");
            pass = false;
        }

        // Consumer Group
        if (DataValidator.isNull(request.getParameter("consumerGroup"))) {
            request.setAttribute("consumerGroup",
                    PropertyReader.getValue("error.require", "Consumer Group"));
            pass = false;
        }

        // Topic Name
        if (DataValidator.isNull(request.getParameter("topicName"))) {
            request.setAttribute("topicName",
                    PropertyReader.getValue("error.require", "Topic Name"));
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

        ConsumerDTO dto = new ConsumerDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setConsumerCode(DataUtility.getString(request.getParameter("consumerCode")));
        dto.setConsumerGroup(DataUtility.getString(request.getParameter("consumerGroup")));
        dto.setTopicName(DataUtility.getString(request.getParameter("topicName")));
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

        ConsumerModelInt model = ModelFactory.getInstance().getConsumerModel();

        if (id > 0) {
            try {
                ConsumerDTO dto = model.findByPK(id);
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

        ConsumerModelInt model = ModelFactory.getInstance().getConsumerModel();

        ConsumerDTO dto = (ConsumerDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {

                    model.update(dto);
                    ServletUtility.setSuccessMessage("Consumer Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;

                } else {

                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Consumer Added Successfully");
                    ServletUtility.redirect(ORSView.CONSUMER_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.CONSUMER_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.CONSUMER_LIST_CTL, request, response);
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
        return ORSView.CONSUMER_VIEW;
    }
}