package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.VehicleInsuranceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.VehicleInsuranceModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "VehicleInsuranceCtl", urlPatterns = { "/ctl/VehicleInsuranceCtl" })
public class VehicleInsuranceCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(VehicleInsuranceCtl.class);

    // Owner Name: alphabets and spaces only
    private static final Pattern OWNER_NAME_PATTERN =
            Pattern.compile("^[a-zA-Z ]+$");

    // Vehicle Number: e.g. MP09AB1234
    private static final Pattern VEHICLE_NUMBER_PATTERN =
            Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Owner Name — required + only alphabets
        if (DataValidator.isNull(request.getParameter("ownerName"))) {
            request.setAttribute("ownerName",
                    PropertyReader.getValue("error.require", "Owner Name"));
            pass = false;
        } else if (!OWNER_NAME_PATTERN.matcher(
                request.getParameter("ownerName").trim()).matches()) {
            request.setAttribute("ownerName",
                    "Owner Name must contain alphabets only");
            pass = false;
        }

        // Vehicle Number — required + valid format
        if (DataValidator.isNull(request.getParameter("vehicleNumber"))) {
            request.setAttribute("vehicleNumber",
                    PropertyReader.getValue("error.require", "Vehicle Number"));
            pass = false;
        } else if (!VEHICLE_NUMBER_PATTERN.matcher(
                request.getParameter("vehicleNumber").trim().toUpperCase()).matches()) {
            request.setAttribute("vehicleNumber",
                    "Vehicle Number must be valid (e.g. MP09AB1234)");
            pass = false;
        }

        // Vehicle Type — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("vehicleType"))) {
            request.setAttribute("vehicleType",
                    PropertyReader.getValue("error.require", "Vehicle Type"));
            pass = false;
        }

        // Insurance Company — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("insuranceCompany"))) {
            request.setAttribute("insuranceCompany",
                    PropertyReader.getValue("error.require", "Insurance Company"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        VehicleInsuranceDTO dto = new VehicleInsuranceDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setOwnerName(DataUtility.getString(request.getParameter("ownerName")));
        dto.setVehicleNumber(DataUtility.getString(
                request.getParameter("vehicleNumber")).toUpperCase());
        dto.setVehicleType(DataUtility.getString(request.getParameter("vehicleType")));
        dto.setInsuranceCompany(DataUtility.getString(request.getParameter("insuranceCompany")));

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
        VehicleInsuranceModelInt model = ModelFactory.getInstance().getVehicleInsuranceModel();

        if (id > 0) {
            try {
                VehicleInsuranceDTO dto = model.findByPK(id);
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

        VehicleInsuranceModelInt model = ModelFactory.getInstance().getVehicleInsuranceModel();
        VehicleInsuranceDTO dto = (VehicleInsuranceDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Vehicle Insurance Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Vehicle Insurance Added Successfully");
                    ServletUtility.redirect(ORSView.VEHICLE_INSURANCE_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.VEHICLE_INSURANCE_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.VEHICLE_INSURANCE_LIST_CTL, request, response);
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
        return ORSView.VEHICLE_INSURANCE_VIEW;
    }
}