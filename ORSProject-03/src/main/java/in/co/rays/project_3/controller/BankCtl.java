package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BankModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Bank functionality Controller
 * 
 * @author Sejal Chourasiya
 */
@WebServlet(urlPatterns = { "/ctl/BankCtl" })
public class BankCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(BankCtl.class);

	/* ================= VALIDATION ================= */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("accountNo"))) {
			request.setAttribute("accountNo", PropertyReader.getValue("error.require", "Account No"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("accountHolder"))) {
			request.setAttribute("accountHolder", PropertyReader.getValue("error.require", "Account Holder"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("accountType"))) {
			request.setAttribute("accountType", PropertyReader.getValue("error.require", "Account Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("openingDate"))) {
			request.setAttribute("openingDate", PropertyReader.getValue("error.require", "Opening Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("openingDate"))) {
			request.setAttribute("openingDate", PropertyReader.getValue("error.date", "Opening Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("balance"))) {
			request.setAttribute("balance", PropertyReader.getValue("error.require", "Balance"));
			pass = false;
		}

		return pass;
	}

	/* ================= POPULATE DTO ================= */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		BankDTO dto = new BankDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setAccountNo(DataUtility.getString(request.getParameter("accountNo")));
		dto.setAccountHolder(DataUtility.getString(request.getParameter("accountHolder")));
		dto.setAccountType(DataUtility.getString(request.getParameter("accountType")));
		dto.setOpeningDate(DataUtility.getDate(request.getParameter("openingDate")));
		dto.setBalance(DataUtility.getDouble(request.getParameter("balance")));
		dto.setAccountLimit(DataUtility.getDouble(request.getParameter("accountLimit")));

		populateBean(dto, request);

		return dto;
	}

	/* ================= DO GET ================= */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("BankCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		BankModelInt model = ModelFactory.getInstance().getBankModel();

		if (id > 0 || op != null) {
			BankDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	/* ================= DO POST ================= */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		BankModelInt model = ModelFactory.getInstance().getBankModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			BankDTO dto = (BankDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data updated successfully", request);
					ServletUtility.setDto(dto, request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Data saved successfully", request);
				}
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Account No already exists", request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			BankDTO dto = (BankDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BANK_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.BANK_VIEW;
	}
}
