package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.TrackingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.TrackingModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "TrackingCtl", urlPatterns = { "/ctl/TrackingCtl" })
public class TrackingCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(TrackingCtl.class);

    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("trackingNumber"))) {
            request.setAttribute("trackingNumber",
                    PropertyReader.getValue("error.require", "Tracking Number"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("currentLocation"))) {
            request.setAttribute("currentLocation",
                    PropertyReader.getValue("error.require", "Current Location"));
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

        TrackingDTO dto = new TrackingDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setTrackingNumber(request.getParameter("trackingNumber"));
        dto.setCurrentLocation(request.getParameter("currentLocation"));
        dto.setStatus(request.getParameter("status"));

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

        TrackingModelInt model = ModelFactory.getInstance().getTrackingModel();

        if (id > 0) {

            try {

                TrackingDTO dto = model.findByPK(id);

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

        TrackingModelInt model = ModelFactory.getInstance().getTrackingModel();

        TrackingDTO dto = (TrackingDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {

                    model.update(dto);

                    ServletUtility.setSuccessMessage("Tracking Updated Successfully", request);

                    ServletUtility.setDto(dto, request);

                    ServletUtility.forward(getView(), request, response);

                    return;

                } else {

                    model.add(dto);

                    request.getSession().setAttribute("successMessage", "Tracking Added Successfully");

                    ServletUtility.redirect(ORSView.TRACKING_CTL, request, response);

                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.TRACKING_CTL, request, response);

                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.TRACKING_LIST_CTL, request, response);

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

        return ORSView.TRACKING_VIEW;
    }
}