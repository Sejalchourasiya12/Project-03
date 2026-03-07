package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.QueueDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.QueueModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Queue List functionality controller to perform Search and List operation
 * 
 * @author Sejal
 */
@WebServlet(name = "QueueListCtl", urlPatterns = {"/ctl/QueueListCtl"})
public class QueueListCtl extends BaseCtl {

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(QueueListCtl.class);

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        QueueDTO dto = new QueueDTO();
        dto.setQueueCode(DataUtility.getString(request.getParameter("queueCode")));
        dto.setQueueName(DataUtility.getString(request.getParameter("queueName")));
        dto.setQueueStatus(DataUtility.getString(request.getParameter("queueStatus")));
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        QueueDTO dto = (QueueDTO) populateDTO(request);
        QueueModelInt model = ModelFactory.getInstance().getQueueModel();

        try {
            List list = model.search(dto, pageNo, pageSize);
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);
        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        pageNo = (pageNo == 0) ? 1 : pageNo;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        QueueDTO dto = (QueueDTO) populateDTO(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");
        QueueModelInt model = ModelFactory.getInstance().getQueueModel();

        try {
            if ("Delete".equalsIgnoreCase(op)) {
                if (ids != null && ids.length > 0) {
                    for (String id : ids) {
                        QueueDTO deletedto = new QueueDTO();
                        deletedto.setId(DataUtility.getLong(id));
                        model.delete(deletedto);
                    }
                    ServletUtility.setSuccessMessage("Queue(s) deleted successfully", request);
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }

            List list = model.search(dto, pageNo, pageSize);
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);
        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
        }
    }

    @Override
    protected String getView() {
        return ORSView.QUEUE_LIST_VIEW;
    }
}
