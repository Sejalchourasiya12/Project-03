
package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.StaffMemberDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.StaffMemberModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/StaffMemberCtl" })
public class StaffMemberCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(StaffMemberCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
	    StaffMemberDTO dto = new StaffMemberDTO();

	    dto.setId(DataUtility.getLong(request.getParameter("id")));
	    dto.setFullName(DataUtility.getString(request.getParameter("fullName")));
	    dto.setDivision(DataUtility.getString(request.getParameter("division")));
	    dto.setJoiningDate(DataUtility.getDate(request.getParameter("joiningDate")));
	    dto.setPreviousEmployeeId(DataUtility.getInt(request.getParameter("previousEmployeeId")));

	    populateBean(dto, request);
	    return dto;
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("StaffMemberCtl doGet Started");

		long id = DataUtility.getLong(request.getParameter("id"));
		StaffMemberModelInt model = ModelFactory.getInstance().getStaffMemberModel();

		if (id > 0) {
			try {
				StaffMemberDTO dto = model.findByPK(id);
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
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("Hello is coming ");

		StaffMemberModelInt model = ModelFactory.getInstance().getStaffMemberModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			StaffMemberDTO dto = (StaffMemberDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data Updated Successfully", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Data Saved Successfully", request);
				}
				ServletUtility.setDto(dto, request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Email already exists", request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			StaffMemberDTO dto = (StaffMemberDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.STAFF_MEMBER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STAFF_MEMBER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STAFF_MEMBER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.STAFF_MEMBER_VIEW;
	}
}
