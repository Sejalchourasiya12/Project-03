package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EnergyConsumptionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EnergyConsumptionModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "EnergyConsumptionCtl", urlPatterns = { "/ctl/EnergyConsumptionCtl" })
public class EnergyConsumptionCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(EnergyConsumptionCtl.class);

    // Energy Code: alphanumeric, hyphens, underscores (e.g. EC-001, EC_2024)
    private static final Pattern ENERGY_CODE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_\\-]+$");

    // Units Consumed: numeric with optional decimal (e.g. 150, 23.5)
    private static final Pattern UNITS_PATTERN =
            Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Energy Code — required + valid format
        if (DataValidator.isNull(request.getParameter("energyCode"))) {
            request.setAttribute("energyCode",
                    PropertyReader.getValue("error.require", "Energy Code"));
            pass = false;
        } else if (!ENERGY_CODE_PATTERN.matcher(
                request.getParameter("energyCode").trim()).matches()) {
            request.setAttribute("energyCode",
                    "Energy Code must be alphanumeric (e.g. EC-001)");
            pass = false;
        }

        // Device Name — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("deviceName"))) {
            request.setAttribute("deviceName",
                    PropertyReader.getValue("error.require", "Device Name"));
            pass = false;
        }

        // Units Consumed — required + numeric
        if (DataValidator.isNull(request.getParameter("unitsConsumed"))) {
            request.setAttribute("unitsConsumed",
                    PropertyReader.getValue("error.require", "Units Consumed"));
            pass = false;
        } else if (!UNITS_PATTERN.matcher(
                request.getParameter("unitsConsumed").trim()).matches()) {
            request.setAttribute("unitsConsumed",
                    "Units Consumed must be a valid number (e.g. 150 or 23.5)");
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

        EnergyConsumptionDTO dto = new EnergyConsumptionDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setEnergyCode(DataUtility.getString(request.getParameter("energyCode")));
        dto.setDeviceName(DataUtility.getString(request.getParameter("deviceName")));
        dto.setUnitsConsumed(DataUtility.getString(request.getParameter("unitsConsumed")));
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
        EnergyConsumptionModelInt model = ModelFactory.getInstance().getEnergyConsumptionModel();

        if (id > 0) {
            try {
                EnergyConsumptionDTO dto = model.findByPK(id);
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

        EnergyConsumptionModelInt model = ModelFactory.getInstance().getEnergyConsumptionModel();
        EnergyConsumptionDTO dto = (EnergyConsumptionDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Energy Consumption Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Energy Consumption Added Successfully");
                    ServletUtility.redirect(ORSView.ENERGY_CONSUMPTION_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ENERGY_CONSUMPTION_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ENERGY_CONSUMPTION_LIST_CTL, request, response);
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
        return ORSView.ENERGY_CONSUMPTION_VIEW;
    }
}