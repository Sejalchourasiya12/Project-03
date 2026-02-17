package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PatientDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PatientModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "PatientListCtl", urlPatterns = { "/ctl/PatientListCtl" })
public class PatientListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(PatientListCtl.class);

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        PatientDTO dto = new PatientDTO();

        dto.setName(DataUtility.getString(request.getParameter("name")));
        dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        dto.setDecease(DataUtility.getString(request.getParameter("decease")));

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        log.debug("PatientListCtl doGet start");

        int pageNo = 1;
        int pageSize =
                DataUtility.getInt(PropertyReader.getValue("page.size"));

        PatientDTO dto = (PatientDTO) populateDTO(request);
        PatientModelInt model =
                ModelFactory.getInstance().getPatientModel();

        try {
            List list = model.search(dto, pageNo, pageSize);
            List next = model.search(dto, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", next.size());
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        log.debug("PatientListCtl doPost start");

        int pageNo =
                DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize =
                DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        String op =
                DataUtility.getString(request.getParameter("operation"));

        PatientDTO dto = (PatientDTO) populateDTO(request);
        PatientModelInt model =
                ModelFactory.getInstance().getPatientModel();

        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op)) {
                pageNo = 1;

            } else if ("Next".equalsIgnoreCase(op)) {
                pageNo++;

            } else if ("Previous".equalsIgnoreCase(op) && pageNo > 1) {
                pageNo--;

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(
                        ORSView.PATIENT_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                if (ids != null && ids.length > 0) {
                    for (String id : ids) {
                        PatientDTO deleteDto = new PatientDTO();
                        deleteDto.setId(DataUtility.getLong(id));
                        model.delete(deleteDto);
                    }
                    ServletUtility.setSuccessMessage(
                            "Record deleted successfully", request);
                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }
            }

            List list = model.search(dto, pageNo, pageSize);
            List next = model.search(dto, pageNo + 1, pageSize);

            if (list == null || list.size() == 0
                    && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage(
                        "No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", next.size());
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    @Override
    protected String getView() {
        return ORSView.PATIENT_LIST_VIEW;
    }
}
