package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.StaffMemberDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.StaffMemberModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "StaffMemberListCtl", urlPatterns = { "/ctl/StaffMemberListCtl" })
public class StaffMemberListCtl extends BaseCtl {

    private static final long serialVersionUID = 1L;
    public static final String OP_SEARCH = "Search";
    public static final String OP_NEXT = "Next";
    public static final String OP_PREVIOUS = "Previous";
    public static final String OP_NEW = "New";
    public static final String OP_DELETE = "Delete";
    public static final String OP_RESET = "Reset";

    private static Logger log = Logger.getLogger(StaffMemberListCtl.class);

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        StaffMemberDTO dto = new StaffMemberDTO();

        dto.setFullName(DataUtility.getString(request.getParameter("fullName")));
        dto.setDivision(DataUtility.getString(request.getParameter("division")));
        dto.setPreviousEmployeeId(DataUtility.getInt(request.getParameter("previousEmployeeId")));
        dto.setJoiningDate(DataUtility.getDate(request.getParameter("joiningDate")));

        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void preload(HttpServletRequest request) {
        // Agar division list ya kuch dropdown preload karna hai toh yahan add karo
        // Filhal blank, agar need ho toh model se fetch karke request me set kar sakte ho
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("StaffMemberListCtl doGet Start");

        List list = null;
        List next = null;
        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        StaffMemberDTO dto = (StaffMemberDTO) populateDTO(request);
        StaffMemberModelInt model = ModelFactory.getInstance().getStaffMemberModel();

        try {
            list = model.search(dto, pageNo, pageSize);
            next = model.search(dto, pageNo + 1, pageSize);

            ServletUtility.setList(list, request);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", next.size());
            }

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
        } catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        log.debug("StaffMemberListCtl doGet End");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("StaffMemberListCtl doPost Start");

        List list = null;
        List next = null;
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        StaffMemberDTO dto = (StaffMemberDTO) populateDTO(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");
        StaffMemberModelInt model = ModelFactory.getInstance().getStaffMemberModel();

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.STAFF_MEMBER_CTL, request, response);
                return;

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.STAFF_MEMBER_LIST_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    StaffMemberDTO deletedto = new StaffMemberDTO();
                    for (String id : ids) {
                        deletedto.setId(DataUtility.getLong(id));
                        model.delete(deletedto);
                    }
                    ServletUtility.setSuccessMessage("Data Successfully Deleted!", request);
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }

            list = model.search(dto, pageNo, pageSize);
            next = model.search(dto, pageNo + 1, pageSize);

            ServletUtility.setDto(dto, request);
            ServletUtility.setList(list, request);

            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", next.size());
            }

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
        } catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        log.debug("StaffMemberListCtl doPost End");
    }

    @Override
    protected String getView() {
        return ORSView.STAFF_MEMBER_LIST_VIEW;
    }
}
