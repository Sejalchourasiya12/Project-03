package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.AnnouncementDTO;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.AnnouncementModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "AnnouncementListCtl", urlPatterns = { "/ctl/AnnouncementListCtl" })
public class AnnouncementListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(AnnouncementListCtl.class);

    @Override
    protected void preload(HttpServletRequest request) {
        AnnouncementModelInt model = ModelFactory.getInstance().getAnnouncementModel();
        try {
            List list = model.list();
            request.setAttribute("announcementList", list);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        log.debug("AnnouncementListCtl populateDTO start");

        AnnouncementDTO dto = new AnnouncementDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setTitle(DataUtility.getString(request.getParameter("title")));
        dto.setDescription(DataUtility.getString(request.getParameter("description")));

        populateBean(dto, request);

        log.debug("AnnouncementListCtl populateDTO end");

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        log.debug("AnnouncementListCtl doGet start");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        AnnouncementDTO dto = (AnnouncementDTO) populateDTO(request);
        AnnouncementModelInt model = ModelFactory.getInstance().getAnnouncementModel();

        try {
            List list = model.search(dto, pageNo, pageSize);
            List next = model.search(dto, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", "0");
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
        }

        log.debug("AnnouncementListCtl doGet end");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        log.debug("AnnouncementListCtl doPost start");

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ?
                DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        String op = DataUtility.getString(request.getParameter("operation"));

        AnnouncementModelInt model = ModelFactory.getInstance().getAnnouncementModel();
        AnnouncementDTO dto = (AnnouncementDTO) populateDTO(request);

        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) ||
                    OP_NEXT.equalsIgnoreCase(op) ||
                    OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.ANNOUNCEMENT_CTL, request, response);
                return;

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.ANNOUNCEMENT_LIST_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    AnnouncementDTO deleteDTO = new AnnouncementDTO();

                    for (String id : ids) {
                        deleteDTO.setId(DataUtility.getLong(id));
                        model.delete(deleteDTO);
                    }

                    ServletUtility.setSuccessMessage("Data Deleted Successfully", request);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }

            List list = model.search(dto, pageNo, pageSize);
            List next = model.search(dto, pageNo + 1, pageSize);

            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", "0");
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
        }

        log.debug("AnnouncementListCtl doPost end");
    }

    @Override
    protected String getView() {
        return ORSView.ANNOUNCEMENT_LIST_VIEW;
    }
}