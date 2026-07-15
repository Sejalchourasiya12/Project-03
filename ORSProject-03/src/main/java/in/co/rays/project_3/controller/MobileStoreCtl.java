package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.MobileStoreDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.MobileStoreModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "MobileStoreCtl", urlPatterns = { "/ctl/MobileStoreCtl" })
public class MobileStoreCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(MobileStoreCtl.class);

    // Price: numeric only (e.g. 15000, 49999)
    private static final Pattern PRICE_PATTERN =
            Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Brand Name — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("brandName"))) {
            request.setAttribute("brandName",
                    PropertyReader.getValue("error.require", "Brand Name"));
            pass = false;
        }

        // Model Name — required
        if (DataValidator.isNull(request.getParameter("modelName"))) {
            request.setAttribute("modelName",
                    PropertyReader.getValue("error.require", "Model Name"));
            pass = false;
        }

        // RAM Size — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("ramSize"))) {
            request.setAttribute("ramSize",
                    PropertyReader.getValue("error.require", "RAM Size"));
            pass = false;
        }

        // Price — required + numeric
        if (DataValidator.isNull(request.getParameter("price"))) {
            request.setAttribute("price",
                    PropertyReader.getValue("error.require", "Price"));
            pass = false;
        } else if (!PRICE_PATTERN.matcher(
                request.getParameter("price").trim()).matches()) {
            request.setAttribute("price",
                    "Price must be a valid number (e.g. 15000)");
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        MobileStoreDTO dto = new MobileStoreDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setBrandName(DataUtility.getString(request.getParameter("brandName")));
        dto.setModelName(DataUtility.getString(request.getParameter("modelName")));
        dto.setRamSize(DataUtility.getString(request.getParameter("ramSize")));
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
        MobileStoreModelInt model = ModelFactory.getInstance().getMobileStoreModel();

        if (id > 0) {
            try {
                MobileStoreDTO dto = model.findByPK(id);
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

        MobileStoreModelInt model = ModelFactory.getInstance().getMobileStoreModel();
        MobileStoreDTO dto = (MobileStoreDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Mobile Store Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Mobile Store Added Successfully");
                    ServletUtility.redirect(ORSView.MOBILE_STORE_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.MOBILE_STORE_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.MOBILE_STORE_LIST_CTL, request, response);
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
        return ORSView.MOBILE_STORE_VIEW;
    }
}