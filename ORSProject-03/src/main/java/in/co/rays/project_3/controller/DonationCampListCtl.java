package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.DonationCampDTO;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.DonationCampModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "DonationCampListCtl", urlPatterns = { "/ctl/DonationCampListCtl" })
public class DonationCampListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(DonationCampListCtl.class);

    @Override
    protected void preload(HttpServletRequest request) {
        DonationCampModelInt model = ModelFactory.getInstance().getDonationCampModel();
        try {
            List list = model.list();
            request.setAttribute("donationCampList", list);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        log.debug("DonationCampListCtl populateDTO start");

        DonationCampDTO dto = new DonationCampDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setCampName(DataUtility.getString(request.getParameter("campName")));
        dto.setOrganizer(DataUtility.getString(request.getParameter("organizer")));
        dto.setCampDate(DataUtility.getDate(request.getParameter("campDate")));

        populateBean(dto, request);

        log.debug("DonationCampListCtl populateDTO end");

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        log.debug("DonationCampListCtl doGet start");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        DonationCampDTO dto = (DonationCampDTO) populateDTO(request);
        DonationCampModelInt model = ModelFactory.getInstance().getDonationCampModel();

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

        log.debug("DonationCampListCtl doGet end");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        log.debug("DonationCampListCtl doPost start");

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ?
                DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        String op = DataUtility.getString(request.getParameter("operation"));

        DonationCampModelInt model = ModelFactory.getInstance().getDonationCampModel();
        DonationCampDTO dto = (DonationCampDTO) populateDTO(request);

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

                ServletUtility.redirect(ORSView.DONATION_CAMP_CTL, request, response);
                return;

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.DONATION_CAMP_LIST_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    DonationCampDTO deleteDTO = new DonationCampDTO();

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

        log.debug("DonationCampListCtl doPost end");
    }

    @Override
    protected String getView() {
        return ORSView.DONATION_CAMP_LIST_VIEW;
    }
}