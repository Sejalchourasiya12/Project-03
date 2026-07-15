package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PromotionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PromotionModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "PromotionCtl", urlPatterns = { "/ctl/PromotionCtl" })
public class PromotionCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(PromotionCtl.class);

	private static final Pattern PROMOTION_CODE_PATTERN = Pattern.compile("^\\d{4}$");

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("promotionCode"))) {

			request.setAttribute("promotionCode",
					PropertyReader.getValue("error.require", "Promotion Code"));

			pass = false;

		} else if (!PROMOTION_CODE_PATTERN.matcher(request.getParameter("promotionCode")).matches()) {

			request.setAttribute("promotionCode",
					"Promotion Code must contain 4 digits only");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("promotionTitle"))) {

			request.setAttribute("promotionTitle",
					PropertyReader.getValue("error.require", "Promotion Title"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("promotionStatus"))) {

			request.setAttribute("promotionStatus",
					PropertyReader.getValue("error.require", "Promotion Status"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("startDate"))) {

			request.setAttribute("startDate",
					PropertyReader.getValue("error.require", "Start Date"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		PromotionDTO dto = new PromotionDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setPromotionCode(request.getParameter("promotionCode"));
		dto.setPromotionTitle(request.getParameter("promotionTitle"));
		dto.setPromotionStatus(request.getParameter("promotionStatus"));
		dto.setStartDate(DataUtility.getDate(request.getParameter("startDate")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {

		String successMsg = (String) request.getSession().getAttribute("successMessage");

		if (successMsg != null) {

			ServletUtility.setSuccessMessage(successMsg, request);

			request.getSession().removeAttribute("successMessage");
		}

		long id = DataUtility.getLong(request.getParameter("id"));

		PromotionModelInt model = ModelFactory.getInstance().getPromotionModel();

		if (id > 0) {

			try {

				PromotionDTO dto = model.findByPK(id);

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException {

	    String op = DataUtility.getString(request.getParameter("operation"));
	    PromotionModelInt model = ModelFactory.getInstance().getPromotionModel();
	    PromotionDTO dto = (PromotionDTO) populateDTO(request);

	    try {
	        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

	            // ✅ Bug 2 Fix - validate karo pehle
	            if (!validate(request)) {
	                ServletUtility.setDto(dto, request);
	                ServletUtility.forward(getView(), request, response);
	                return;
	            }

	            // ✅ Bug 1 Fix - null check add kiya
	            if (dto.getId() != null && dto.getId() > 0) {

	                model.update(dto);
	                ServletUtility.setSuccessMessage("Promotion Updated Successfully", request);
	                ServletUtility.setDto(dto, request);
	                ServletUtility.forward(getView(), request, response);
	                return;

	            } else {

	                model.add(dto);
	                request.getSession().setAttribute("successMessage", "Promotion Added Successfully");
	                ServletUtility.redirect(ORSView.PROMOTION_CTL, request, response);
	                return;
	            }

	        } else if (OP_RESET.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.PROMOTION_CTL, request, response);
	            return;

	        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.PROMOTION_LIST_CTL, request, response);
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

		return ORSView.PROMOTION_VIEW;
	}
}