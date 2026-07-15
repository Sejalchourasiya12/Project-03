package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.DroneDeliveryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.DroneDeliveryModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "DroneDeliveryCtl", urlPatterns = { "/ctl/DroneDeliveryCtl" })
public class DroneDeliveryCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(DroneDeliveryCtl.class);

    // Drone Code: alphanumeric, hyphens, underscores (e.g. DRN-001, DR_2024)
    private static final Pattern DRONE_CODE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_\\-]+$");

    // Operator Name: alphabets and spaces only
    private static final Pattern OPERATOR_NAME_PATTERN =
            Pattern.compile("^[a-zA-Z ]+$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Drone Code — required + valid format
        if (DataValidator.isNull(request.getParameter("droneCode"))) {
            request.setAttribute("droneCode",
                    PropertyReader.getValue("error.require", "Drone Code"));
            pass = false;
        } else if (!DRONE_CODE_PATTERN.matcher(
                request.getParameter("droneCode").trim()).matches()) {
            request.setAttribute("droneCode",
                    "Drone Code must be alphanumeric (e.g. DRN-001)");
            pass = false;
        }

        // Operator Name — required + alphabets only
        if (DataValidator.isNull(request.getParameter("operatorName"))) {
            request.setAttribute("operatorName",
                    PropertyReader.getValue("error.require", "Operator Name"));
            pass = false;
        } else if (!OPERATOR_NAME_PATTERN.matcher(
                request.getParameter("operatorName").trim()).matches()) {
            request.setAttribute("operatorName",
                    "Operator Name must contain alphabets only");
            pass = false;
        }

        // Delivery Zone — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("deliveryZone"))) {
            request.setAttribute("deliveryZone",
                    PropertyReader.getValue("error.require", "Delivery Zone"));
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

        DroneDeliveryDTO dto = new DroneDeliveryDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setDroneCode(DataUtility.getString(request.getParameter("droneCode")));
        dto.setOperatorName(DataUtility.getString(request.getParameter("operatorName")));
        dto.setDeliveryZone(DataUtility.getString(request.getParameter("deliveryZone")));
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
        DroneDeliveryModelInt model = ModelFactory.getInstance().getDroneDeliveryModel();

        if (id > 0) {
            try {
                DroneDeliveryDTO dto = model.findByPK(id);
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

        DroneDeliveryModelInt model = ModelFactory.getInstance().getDroneDeliveryModel();
        DroneDeliveryDTO dto = (DroneDeliveryDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Drone Delivery Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Drone Delivery Added Successfully");
                    ServletUtility.redirect(ORSView.DRONE_DELIVERY_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.DRONE_DELIVERY_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.DRONE_DELIVERY_LIST_CTL, request, response);
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
        return ORSView.DRONE_DELIVERY_VIEW;
    }
}