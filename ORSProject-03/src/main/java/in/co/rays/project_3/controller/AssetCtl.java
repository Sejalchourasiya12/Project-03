package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.AssetDTO;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AssetModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "AssetCtl", urlPatterns = { "/ctl/AssetCtl" })
public class AssetCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(AssetCtl.class);

    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("assetCode"))) {
            request.setAttribute("assetCode", PropertyReader.getValue("error.require", "Asset Code"));
            pass = false;
        } else if (!DataValidator.isAssetCode(request.getParameter("assetCode"))) {
            request.setAttribute("assetCode", "Please enter valid Asset Code (e.g. AS-101)");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("assetName"))) {
            request.setAttribute("assetName", PropertyReader.getValue("error.require", "Asset Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("assetName"))) {
            request.setAttribute("assetName", "Please enter correct Asset Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("assignedTo"))) {
            request.setAttribute("assignedTo", PropertyReader.getValue("error.require", "Assigned To"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("assignedTo"))) {
            request.setAttribute("assignedTo", "Please enter correct Assigned To name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("issueDate"))) {
            request.setAttribute("issueDate", PropertyReader.getValue("error.require", "Issue Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("issueDate"))) {
            request.setAttribute("issueDate", PropertyReader.getValue("error.date", "Issue Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("assetStatus"))) {
            request.setAttribute("assetStatus", PropertyReader.getValue("error.require", "Asset Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        AssetDTO dto = new AssetDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setAssetCode(request.getParameter("assetCode"));
        dto.setAssetName(request.getParameter("assetName"));
        dto.setAssignedTo(request.getParameter("assignedTo"));
        dto.setIssueDate(DataUtility.getDate(request.getParameter("issueDate")));
        dto.setAssetStatus(request.getParameter("assetStatus"));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        // Session se success message uthao
        String successMsg = (String) request.getSession().getAttribute("successMessage");
        if (successMsg != null) {
            ServletUtility.setSuccessMessage(successMsg, request);
            request.getSession().removeAttribute("successMessage");
        }

        long id = DataUtility.getLong(request.getParameter("id"));
        AssetModelInt model = ModelFactory.getInstance().getAssetModel();

        if (id > 0) {
            try {
                AssetDTO dto = model.findByPK(id);
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
        AssetModelInt model = ModelFactory.getInstance().getAssetModel();
        AssetDTO dto = (AssetDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    // UPDATE
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Asset Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    // ADD
                    model.add(dto);
                    request.getSession().setAttribute("successMessage", "Asset Added Successfully");
                    ServletUtility.redirect(ORSView.ASSET_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ASSET_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ASSET_LIST_CTL, request, response);
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
        return ORSView.ASSET_VIEW;
    }
}