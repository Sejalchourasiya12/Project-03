package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BloodBankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BloodBankModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "BloodBankCtl", urlPatterns = { "/ctl/BloodBankCtl" })
public class BloodBankCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(BloodBankCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("donorName"))) {
            request.setAttribute("donorName",
                    PropertyReader.getValue("error.require", "Donor Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("bloodGroup"))) {
            request.setAttribute("bloodGroup",
                    PropertyReader.getValue("error.require", "Blood Group"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("available"))) {
            request.setAttribute("available",
                    PropertyReader.getValue("error.require", "Available Units"));
            pass = false;
        }

        String contact = request.getParameter("contactNumber");
        if (DataValidator.isNull(contact)) {
            request.setAttribute("contactNumber",
                    PropertyReader.getValue("error.require", "Contact Number"));
            pass = false;
        } else if (!contact.matches("^[0-9]{10}$")) {
            request.setAttribute("contactNumber", "Contact Number must be 10 digits");
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        BloodBankDTO dto = new BloodBankDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setDonorName(DataUtility.getString(request.getParameter("donorName")));
        dto.setBloodGroup(DataUtility.getString(request.getParameter("bloodGroup")));
        dto.setAvailable(DataUtility.getInt(request.getParameter("available")));
        dto.setContactNumber(DataUtility.getString(request.getParameter("contactNumber")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	System.out.println("TEST");

        String successMsg = (String) request.getSession().getAttribute("successMessage");
        if (successMsg != null) {
            ServletUtility.setSuccessMessage(successMsg, request);
            request.getSession().removeAttribute("successMessage");
        }

        long id = DataUtility.getLong(request.getParameter("id"));
        BloodBankModelInt model = ModelFactory.getInstance().getBloodBankModel();

        if (id > 0) {
            try {
                BloodBankDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, request);
            } catch (ApplicationException e) {
                log.error(e);
                System.out.println("TEST");
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        System.out.println(getView());
        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String op = DataUtility.getString(request.getParameter("operation"));

        BloodBankModelInt model = ModelFactory.getInstance().getBloodBankModel();
        BloodBankDTO dto = (BloodBankDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Blood Bank Record Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Blood Bank Record Added Successfully");
                    ServletUtility.redirect(ORSView.BLOOD_BANK_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.BLOOD_BANK_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.BLOOD_BANK_LIST_CTL, request, response);
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
        return ORSView.BLOOD_BANK_VIEW;
    }
}