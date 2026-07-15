package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CloudStorageDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CloudStorageModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "CloudStorageCtl", urlPatterns = { "/ctl/CloudStorageCtl" })
public class CloudStorageCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(CloudStorageCtl.class);

    // File name: alphanumeric, dots, underscores, hyphens
    private static final Pattern FILE_NAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._\\- ]+$");

    // File size: numeric with optional unit (e.g. 10MB, 512KB, 1.5GB)
    private static final Pattern FILE_SIZE_PATTERN =
            Pattern.compile("^[0-9]+(\\.[0-9]+)?\\s?(KB|MB|GB|TB|kb|mb|gb|tb)?$");

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // File Name — required + valid characters
        if (DataValidator.isNull(request.getParameter("fileName"))) {
            request.setAttribute("fileName",
                    PropertyReader.getValue("error.require", "File Name"));
            pass = false;
        } else if (!FILE_NAME_PATTERN.matcher(
                request.getParameter("fileName").trim()).matches()) {
            request.setAttribute("fileName",
                    "File Name must contain alphabets, numbers, dots, hyphens or underscores only");
            pass = false;
        }

        // File Size — required + valid format
        if (DataValidator.isNull(request.getParameter("fileSize"))) {
            request.setAttribute("fileSize",
                    PropertyReader.getValue("error.require", "File Size"));
            pass = false;
        } else if (!FILE_SIZE_PATTERN.matcher(
                request.getParameter("fileSize").trim()).matches()) {
            request.setAttribute("fileSize",
                    "File Size must be a valid value (e.g. 10MB, 512KB, 1.5GB)");
            pass = false;
        }

        // Upload Date — required
        if (DataValidator.isNull(request.getParameter("uploadDate"))) {
            request.setAttribute("uploadDate",
                    PropertyReader.getValue("error.require", "Upload Date"));
            pass = false;
        }

        // Storage Type — required (preloaded dropdown)
        if (DataValidator.isNull(request.getParameter("storageType"))) {
            request.setAttribute("storageType",
                    PropertyReader.getValue("error.require", "Storage Type"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        CloudStorageDTO dto = new CloudStorageDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setFileName(DataUtility.getString(request.getParameter("fileName")));
        dto.setFileSize(DataUtility.getString(request.getParameter("fileSize")));
        dto.setUploadDate(DataUtility.getDate(request.getParameter("uploadDate")));
        dto.setStorageType(DataUtility.getString(request.getParameter("storageType")));

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
        CloudStorageModelInt model = ModelFactory.getInstance().getCloudStorageModel();

        if (id > 0) {
            try {
                CloudStorageDTO dto = model.findByPK(id);
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

        CloudStorageModelInt model = ModelFactory.getInstance().getCloudStorageModel();
        CloudStorageDTO dto = (CloudStorageDTO) populateDTO(request);

        try {

            if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

                if (dto.getId() > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Cloud Storage Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.forward(getView(), request, response);
                    return;
                } else {
                    model.add(dto);
                    request.getSession().setAttribute("successMessage",
                            "Cloud Storage Added Successfully");
                    ServletUtility.redirect(ORSView.CLOUD_STORAGE_CTL, request, response);
                    return;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.CLOUD_STORAGE_CTL, request, response);
                return;

            } else if (OP_CANCEL.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.CLOUD_STORAGE_LIST_CTL, request, response);
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
        return ORSView.CLOUD_STORAGE_VIEW;
    }
}