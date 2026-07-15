package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SmartParkingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.SmartParkingModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "SmartParkingCtl", urlPatterns = { "/ctl/SmartParkingCtl" })
public class SmartParkingCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(SmartParkingCtl.class);

    // Parking Code: alphanumeric, hyphens, underscores (e.g. PRK-001)
    private static final Pattern PARKING_CODE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_\\-]+$");

    // Vehicle Number: Indian format e.g. MP09AB1234
    private static final Pattern VEHICLE_NUMBER_PATTERN =
            Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Parking Code — required + valid format
        if (DataValidator.isNull(request.getParameter("parkingCode"))) {
            request.setAttribute("parkingCode",
                    PropertyReader.getValue("error.require", "Parking Code"));
            pass = false;
        } else if (!PARKING_CODE_PATTERN.matcher(
                request.getParameter("parkingCode").trim()).matches()) {
            request.setAttribute("parkingCode",
                    "Parking Code must be alphanumeric (e.g. PRK-001)");
            pass = false;
        }

        // Vehicle Number — required + valid Indian format
        if (DataValidator.isNull(request.getParameter("vechicleNumber"))) {
            request.setAttribute("vechicleNumber",
                    PropertyReader.getValue("error.require", "Vehicle Number"));
            pass = false;
        } else if (!VEHICLE_NUMBER_PATTERN.matcher(
                request.getParameter("vechicleNumber").trim().toUpperCase()).matches()) {
            request.setAttribute("vechicleNumber",
                    "Vehicle Number must be valid (e.g. MP09AB1234)");
            pass = false;
        }

        // Slot Number — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("slotNumber"))) {
            request.setAttribute("slotNumber",
                    PropertyReader.getValue("error.require", "Slot Number"));
            pass = false;
        }

        // Status — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        SmartParkingDTO dto = new SmartParkingDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setParkingCode(DataUtility.getString(request.getParameter("parkingCode")));
        dto.setVechicleNumber(DataUtility.getString(
                request.getParameter("vechicleNumber")).toUpperCase());
        dto.setSlotNumber(DataUtility.getString(request.getParameter("slotNumber")));
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
        SmartParkingModelInt model = ModelFactory.getInstance().getSmartParkingModel();

        if (id > 0) {
            try {
                SmartParkingDTO dto = model.findByPK(id);
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

        SmartParkingModelInt model = ModelFactory.getInstance().getSmartParkingModel();
        SmartParkingDTO dto = (SmartParkingDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Smart Parking Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Smart Parking Added Successfully");
                    ServletUtility.redirect(ORSView.SMART_PARKING_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SMART_PARKING_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SMART_PARKING_LIST_CTL, request, response);
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
        return ORSView.SMART_PARKING_VIEW;
    }
}