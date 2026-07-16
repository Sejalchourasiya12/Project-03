package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.WeatherAlertDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.WeatherAlertModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "WeatherAlertCtl", urlPatterns = { "/ctl/WeatherAlertCtl" })
public class WeatherAlertCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(WeatherAlertCtl.class);

    // Alert Code: alphanumeric, hyphens, underscores (e.g. WA-001)
    private static final Pattern ALERT_CODE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_\\-]+$");

    // Temperature: numeric with optional decimal and optional minus (e.g. 36.5, -5, 100)
    private static final Pattern TEMPERATURE_PATTERN =
            Pattern.compile("^-?[0-9]+(\\.[0-9]{1,2})?$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Alert Code — required + valid format
        if (DataValidator.isNull(request.getParameter("alertCode"))) {
            request.setAttribute("alertCode",
                    PropertyReader.getValue("error.require", "Alert Code"));
            pass = false;
        } else if (!ALERT_CODE_PATTERN.matcher(
                request.getParameter("alertCode").trim()).matches()) {
            request.setAttribute("alertCode",
                    "Alert Code must be alphanumeric (e.g. WA-001)");
            pass = false;
        }

        // City Name — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("cityName"))) {
            request.setAttribute("cityName",
                    PropertyReader.getValue("error.require", "City Name"));
            pass = false;
        }

        // Temperature — required + valid numeric format
        if (DataValidator.isNull(request.getParameter("temperature"))) {
            request.setAttribute("temperature",
                    PropertyReader.getValue("error.require", "Temperature"));
            pass = false;
        } else if (!TEMPERATURE_PATTERN.matcher(
                request.getParameter("temperature").trim()).matches()) {
            request.setAttribute("temperature",
                    "Temperature must be a valid number (e.g. 36.5 or -5)");
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

        WeatherAlertDTO dto = new WeatherAlertDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setAlertCode(DataUtility.getString(request.getParameter("alertCode")));
        dto.setCityName(DataUtility.getString(request.getParameter("cityName")));
        dto.setTemperature(DataUtility.getString(request.getParameter("temperature")));
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
        WeatherAlertModelInt model = ModelFactory.getInstance().getWeatherAlertModel();

        if (id > 0) {
            try {
                WeatherAlertDTO dto = model.findByPK(id);
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

        WeatherAlertModelInt model = ModelFactory.getInstance().getWeatherAlertModel();
        WeatherAlertDTO dto = (WeatherAlertDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Weather Alert Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Weather Alert Added Successfully");
                    ServletUtility.redirect(ORSView.WEATHER_ALERT_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.WEATHER_ALERT_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.WEATHER_ALERT_LIST_CTL, request, response);
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
        return ORSView.WEATHER_ALERT_VIEW;
    }
}