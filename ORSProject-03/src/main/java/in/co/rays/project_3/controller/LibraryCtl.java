package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.LibraryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.LibraryModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "LibraryCtl", urlPatterns = { "/ctl/LibraryCtl" })
public class LibraryCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(LibraryCtl.class);

    // Only alphabets and spaces
    private static final Pattern ALPHA_PATTERN =
            Pattern.compile("^[a-zA-Z ]+$");

    // Only digits and decimal point
    private static final Pattern PRICE_PATTERN =
            Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Book Name — required + only alphabets
        if (DataValidator.isNull(request.getParameter("bookName"))) {
            request.setAttribute("bookName",
                    PropertyReader.getValue("error.require", "Book Name"));
            pass = false;
        } else if (!ALPHA_PATTERN.matcher(
                request.getParameter("bookName").trim()).matches()) {
            request.setAttribute("bookName",
                    "Book Name must contain alphabets only");
            pass = false;
        }

        // Author Name — required + only alphabets
        if (DataValidator.isNull(request.getParameter("authorName"))) {
            request.setAttribute("authorName",
                    PropertyReader.getValue("error.require", "Author Name"));
            pass = false;
        } else if (!ALPHA_PATTERN.matcher(
                request.getParameter("authorName").trim()).matches()) {
            request.setAttribute("authorName",
                    "Author Name must contain alphabets only");
            pass = false;
        }

        // Issue Date — required
        if (DataValidator.isNull(request.getParameter("issueDate"))) {
            request.setAttribute("issueDate",
                    PropertyReader.getValue("error.require", "Issue Date"));
            pass = false;
        }

        // Price — required + only numeric
        if (DataValidator.isNull(request.getParameter("price"))) {
            request.setAttribute("price",
                    PropertyReader.getValue("error.require", "Price"));
            pass = false;
        } else if (!PRICE_PATTERN.matcher(
                request.getParameter("price").trim()).matches()) {
            request.setAttribute("price",
                    "Price must be a valid number (e.g. 299 or 299.99)");
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        LibraryDTO dto = new LibraryDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setBookName(DataUtility.getString(request.getParameter("bookName")));
        dto.setAuthorName(DataUtility.getString(request.getParameter("authorName")));
        dto.setIssueDate(DataUtility.getString(request.getParameter("issueDate")));
        dto.setPrice(DataUtility.getString(request.getParameter("price")));

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
        LibraryModelInt model = ModelFactory.getInstance().getLibraryModel();

        if (id > 0) {
            try {
                LibraryDTO dto = model.findByPK(id);
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

        LibraryModelInt model = ModelFactory.getInstance().getLibraryModel();
        LibraryDTO dto = (LibraryDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Library Record Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Library Record Added Successfully");
                    ServletUtility.redirect(ORSView.LIBRARY_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.LIBRARY_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.LIBRARY_LIST_CTL, request, response);
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
        return ORSView.LIBRARY_VIEW;
    }
}