package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PodcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PodcastModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "PodcastCtl", urlPatterns = { "/ctl/PodcastCtl" })
public class PodcastCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(PodcastCtl.class);

    // e.g. POD001
    private static final Pattern PODCAST_CODE_PATTERN =
            Pattern.compile("^[A-Z]{3}[0-9]{3}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("podcastCode"))) {
            request.setAttribute("podcastCode",
                    PropertyReader.getValue("error.require", "Podcast Code"));
            pass = false;
        } else if (!PODCAST_CODE_PATTERN.matcher(
                request.getParameter("podcastCode")).matches()) {
            request.setAttribute("podcastCode",
                    "Podcast Code must be 3 Capital Letters followed by 3 Digits (e.g. POD001)");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("podcastTitle"))) {
            request.setAttribute("podcastTitle",
                    PropertyReader.getValue("error.require", "Podcast Title"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("hostName"))) {
            request.setAttribute("hostName",
                    PropertyReader.getValue("error.require", "Host Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        PodcastDTO dto = new PodcastDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setPodcastCode(DataUtility.getString(request.getParameter("podcastCode")));
        dto.setPodcastTitle(DataUtility.getString(request.getParameter("podcastTitle")));
        dto.setHostName(DataUtility.getString(request.getParameter("hostName")));
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
        PodcastModelInt model = ModelFactory.getInstance().getPodcastModel();

        if (id > 0) {
            try {
                PodcastDTO dto = model.findByPK(id);
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

        PodcastModelInt model = ModelFactory.getInstance().getPodcastModel();
        PodcastDTO dto = (PodcastDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Podcast Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Podcast Added Successfully");
                    ServletUtility.redirect(ORSView.PODCAST_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.PODCAST_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.PODCAST_LIST_CTL, request, response);
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
        return ORSView.PODCAST_VIEW;
    }
}