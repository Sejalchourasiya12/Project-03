package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ReviewDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ReviewModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ReviewCtl", urlPatterns = { "/ctl/ReviewCtl" })
public class ReviewCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(ReviewCtl.class);

    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("reviewCode"))) {
            request.setAttribute("reviewCode",
                    PropertyReader.getValue("error.require", "Review Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("userId"))) {
            request.setAttribute("userId",
                    PropertyReader.getValue("error.require", "User Id"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("rating"))) {
            request.setAttribute("rating",
                    PropertyReader.getValue("error.require", "Rating"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("userId"))) {
            request.setAttribute("userId",
                    PropertyReader.getValue("error.require", "User Id"));
            pass = false;

        } else if (DataUtility.getLong(request.getParameter("userId")) <= 0) {
            request.setAttribute("userId",
                    "User Id must be greater than 0");
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("comment"))) {
            request.setAttribute("comment",
                    PropertyReader.getValue("error.require", "Comment"));
            pass = false;
        }

        

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        ReviewDTO dto = new ReviewDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setReviewCode(request.getParameter("reviewCode"));
        dto.setUserId(DataUtility.getLong(request.getParameter("userId")));
        dto.setRating(DataUtility.getInt(request.getParameter("rating")));
        dto.setComment(request.getParameter("comment"));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String successMsg = (String) request.getSession().getAttribute("successMessage");

        if (successMsg != null) {
            ServletUtility.setSuccessMessage(successMsg, request);
            request.getSession().removeAttribute("successMessage");
        }

        long id = DataUtility.getLong(request.getParameter("id"));

        ReviewModelInt model = ModelFactory.getInstance().getReviewModel();

        if (id > 0) {

            try {

                ReviewDTO dto = model.findByPK(id);

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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String op = DataUtility.getString(request.getParameter("operation"));

        ReviewModelInt model = ModelFactory.getInstance().getReviewModel();

        ReviewDTO dto = (ReviewDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {

                    model.update(dto);

                    ServletUtility.setSuccessMessage("Review Updated Successfully", request);

                    ServletUtility.setDto(dto, request);

                    ServletUtility.forward(getView(), request, response);

                    return;

                } else {

                    model.add(dto);

                    request.getSession().setAttribute("successMessage", "Review Added Successfully");

                    ServletUtility.redirect(ORSView.REVIEW_CTL, request, response);

                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.REVIEW_CTL, request, response);

                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.REVIEW_LIST_CTL, request, response);

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

        return ORSView.REVIEW_VIEW;
    }
}