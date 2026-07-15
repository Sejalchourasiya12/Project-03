package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ContactDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ContactModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ContactCtl", urlPatterns = "/ctl/ContactCtl")
public class ContactCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_CANCEL.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name",
                    PropertyReader.getValue("error.require", "Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("city"))) {
            request.setAttribute("city",
                    PropertyReader.getValue("error.require", "City"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("mobile"))) {
            request.setAttribute("mobile",
                    PropertyReader.getValue("error.require", "Mobile No"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        ContactDTO dto = new ContactDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setName(DataUtility.getString(request.getParameter("name")));
        dto.setCity(DataUtility.getString(request.getParameter("city")));
        dto.setDob(DataUtility.getDate(request.getParameter("dob")));
        dto.setMobile(DataUtility.getString(request.getParameter("mobile")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        ContactModelInt model =
                ModelFactory.getInstance().getContactModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (id > 0) {
            try {
                ContactDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, request);
            } catch (Exception e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        ContactModelInt model =
                ModelFactory.getInstance().getContactModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            ContactDTO dto = (ContactDTO) populateDTO(request);

            try {
                if (id > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage(
                            "Contact Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                } else {
                    try {
                        model.add(dto);
                        ServletUtility.setSuccessMessage(
                                "Contact Saved Successfully", request);
                    } catch (DuplicateRecordException e) {
                        ServletUtility.setDto(dto, request);
                        ServletUtility.setErrorMessage(
                                "Contact already exists", request);
                    }
                }

            } catch (DuplicateRecordException e) {
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage(
                        "Contact already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            ContactDTO dto = (ContactDTO) populateDTO(request);
            try {
                model.delete(dto);
            } catch (ApplicationException e) {
                e.printStackTrace();
            }

            ServletUtility.redirect(
                    ORSView.CONTACT_LIST_CTL, request, response);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(
                    ORSView.CONTACT_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(
                    ORSView.CONTACT_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.CONTACT_VIEW;
    }
}