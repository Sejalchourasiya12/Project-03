package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.AnnouncementDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AnnouncementModelInt;
import in.co.rays.project_3.model.ModelFactory;


import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "AnnouncementCtl", urlPatterns = "/ctl/AnnouncementCtl")
public class AnnouncementCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String op = request.getParameter("operation");

		if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
			return pass;
		}

		//log.debug("AnnouncementCtl validate start");

//        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("announcementCode"))) {
            request.setAttribute("announcementCode",
                    PropertyReader.getValue("error.require", "Announcement Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("title"))) {
            request.setAttribute("title",
                    PropertyReader.getValue("error.require", "Title"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description",
                    PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("publishDate"))) {
            request.setAttribute("publishDate",
                    PropertyReader.getValue("error.require", "Publish Date"));
            pass = false;
        }

       // log.debug("AnnouncementCtl validate end");
        return pass;
    }


		

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		

		 //log.debug("AnnouncementCtl populateDTO start");

	        AnnouncementDTO dto = new AnnouncementDTO();

	        dto.setId(DataUtility.getLong(request.getParameter("id")));
	        dto.setAnnouncementCode(DataUtility.getString(request.getParameter("announcementCode")));
	        dto.setTitle(DataUtility.getString(request.getParameter("title")));
	        dto.setDescription(DataUtility.getString(request.getParameter("description")));
	        dto.setPublishDate(DataUtility.getDate(request.getParameter("publishDate")));

	        populateBean(dto, request);

	        //log.debug("AnnouncementCtl populateDTO end");

	        return dto;
	    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		AnnouncementModelInt model = ModelFactory.getInstance().getAnnouncementModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				AnnouncementDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);

			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// log.debug("StudentCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		AnnouncementModelInt model = ModelFactory.getInstance().getAnnouncementModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			AnnouncementDTO dto = (AnnouncementDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
					ServletUtility.setDto(dto, request);

				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Leave already exists", request);
					}

				}

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Announcement code already exists", request);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			AnnouncementDTO dto = (AnnouncementDTO) populateDTO(request);
			try {
				model.delete(dto);
			
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.ANNOUNCEMENT_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ANNOUNCEMENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ANNOUNCEMENT_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		// log.debug("StudentCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.ANNOUNCEMENT_VIEW;
	}
}

