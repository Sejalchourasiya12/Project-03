package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.LeaveDTO;
import in.co.rays.project_3.dto.StudentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.LeaveModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.StudentModelInt;
import in.co.rays.project_3.model.SubjectModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "LeaveCtl", urlPatterns = "/ctl/LeaveCtl")
public class LeaveCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String op = request.getParameter("operation");

		if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("leaveCode"))) {
			request.setAttribute("leaveCode", PropertyReader.getValue("error.require", "Leave Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("employeeName"))) {
			request.setAttribute("employeeName", PropertyReader.getValue("error.require", "Employee Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("leaveStartDate"))) {
			request.setAttribute("leaveStartDate", PropertyReader.getValue("error.require", "Leave Start Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("leaveStatus"))) {
			request.setAttribute("leaveStatus", PropertyReader.getValue("error.require", "Leave Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		LeaveDTO dto = new LeaveDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setLeaveCode(DataUtility.getString(request.getParameter("leaveCode")));
		dto.setEmployeeName(DataUtility.getString(request.getParameter("employeeName")));
		dto.setLeaveStatus(DataUtility.getString(request.getParameter("leaveStatus")));
		dto.setLeaveStartDate(DataUtility.getDate(request.getParameter("leaveStartDate")));
		dto.setLeaveEndDate(DataUtility.getDate(request.getParameter("leaveEndDate")));


		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		LeaveModelInt model = ModelFactory.getInstance().getLeaveModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				LeaveDTO dto = model.findByPK(id);
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

		LeaveModelInt model = ModelFactory.getInstance().getLeaveModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			LeaveDTO dto = (LeaveDTO) populateDTO(request);

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
				ServletUtility.setErrorMessage("Leave Email Id already exists", request);
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			LeaveDTO dto = (LeaveDTO) populateDTO(request);
			try {
				model.delete(dto);
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.LEAVE_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LEAVE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LEAVE_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		// log.debug("StudentCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.LEAVE_VIEW;
	}
}