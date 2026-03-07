package in.co.rays.project_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.QueueDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.QueueModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Queue functionality controller to perform add/update/delete operation
 * 
 * @author Sejal Chourasiya
 */
@WebServlet(urlPatterns = {"/ctl/QueueCtl"})
public class QueueCtl extends BaseCtl {

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(QueueCtl.class);

    public static final String OP_SAVE = "Save";
    public static final String OP_RESET = "Reset";

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("queueCode"))) {
            request.setAttribute("queueCode", "Queue Code is required");
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("queueName"))) {
            request.setAttribute("queueName", "Queue Name is required");
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("queueSize"))) {
            request.setAttribute("queueSize", "Queue Size is required");
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("queueStatus"))) {
            request.setAttribute("queueStatus", "Queue Status is required");
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        QueueDTO dto = new QueueDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setQueueCode(DataUtility.getString(request.getParameter("queueCode")));
        dto.setQueueName(DataUtility.getString(request.getParameter("queueName")));
        dto.setQueueSize(DataUtility.getInt(request.getParameter("queueSize")));
        dto.setQueueStatus(DataUtility.getString(request.getParameter("queueStatus")));
        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id = DataUtility.getLong(request.getParameter("id"));
        QueueModelInt model = ModelFactory.getInstance().getQueueModel();

        if (id > 0) {
            try {
                QueueDTO dto = model.findByPK(id);
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
            throws ServletException, IOException {
        String op = request.getParameter("operation");
        long id = DataUtility.getLong(request.getParameter("id"));
        QueueModelInt model = ModelFactory.getInstance().getQueueModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            QueueDTO dto = (QueueDTO) populateDTO(request);
            try {
                if (id > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Queue updated successfully", request);
                } else {
                    model.add(dto);
                    ServletUtility.setSuccessMessage("Queue added successfully", request);
                }
                ServletUtility.setDto(dto, request);
            } catch (ApplicationException | DuplicateRecordException e) {
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage(e.getMessage(), request);
            }
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.QUEUE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.QUEUE_VIEW;
    }
}
