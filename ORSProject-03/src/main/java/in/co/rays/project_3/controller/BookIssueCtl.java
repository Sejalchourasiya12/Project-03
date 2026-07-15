package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BookIssueDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BookIssueModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "BookIssueCtl", urlPatterns = { "/ctl/BookIssueCtl" })
public class BookIssueCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(BookIssueCtl.class);

    // e.g. ISS001
    private static final Pattern ISSUE_CODE_PATTERN =
            Pattern.compile("^[A-Z]{3}[0-9]{3}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Issue Code
        if (DataValidator.isNull(request.getParameter("issueCode"))) {
            request.setAttribute("issueCode",
                    PropertyReader.getValue("error.require", "Issue Code"));
            pass = false;
        } else if (!ISSUE_CODE_PATTERN.matcher(request.getParameter("issueCode")).matches()) {
            request.setAttribute("issueCode",
                    "Issue Code must be 3 Capital Letters followed by 3 Digits (e.g. ISS001)");
            pass = false;
        }

        // Book Name
        if (DataValidator.isNull(request.getParameter("bookName"))) {
            request.setAttribute("bookName",
                    PropertyReader.getValue("error.require", "Book Name"));
            pass = false;
        }

        // Issue Date
        if (DataValidator.isNull(request.getParameter("issueDate"))) {
            request.setAttribute("issueDate",
                    PropertyReader.getValue("error.require", "Issue Date"));
            pass = false;
        }

        // Return Date
        if (DataValidator.isNull(request.getParameter("returnDate"))) {
            request.setAttribute("returnDate",
                    PropertyReader.getValue("error.require", "Return Date"));
            pass = false;
        }

        // Issue Status
        if (DataValidator.isNull(request.getParameter("issueStatus"))) {
            request.setAttribute("issueStatus",
                    PropertyReader.getValue("error.require", "Issue Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        BookIssueDTO dto = new BookIssueDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setIssueCode(DataUtility.getString(request.getParameter("issueCode")));
        dto.setBookName(DataUtility.getString(request.getParameter("bookName")));
        dto.setIssueDate(DataUtility.getDate(request.getParameter("issueDate")));
        dto.setReturnDate(DataUtility.getDate(request.getParameter("returnDate")));
        dto.setIssueStatus(DataUtility.getString(request.getParameter("issueStatus")));

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

        BookIssueModelInt model = ModelFactory.getInstance().getBookIssueModel();

        if (id > 0) {
            try {
                BookIssueDTO dto = model.findByPK(id);
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

        BookIssueModelInt model = ModelFactory.getInstance().getBookIssueModel();

        BookIssueDTO dto = (BookIssueDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {

                    model.update(dto);
                    ServletUtility.setSuccessMessage("Book Issue Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;

                } else {

                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Book Issue Added Successfully");
                    ServletUtility.redirect(ORSView.BOOK_ISSUE_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.BOOK_ISSUE_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.BOOK_ISSUE_LIST_CTL, request, response);
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
        return ORSView.BOOK_ISSUE_VIEW;
    }
}