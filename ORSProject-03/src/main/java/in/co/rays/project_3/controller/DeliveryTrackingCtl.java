package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.DeliveryTrackingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.DeliveryTrackingModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "DeliveryTrackingCtl", urlPatterns = { "/ctl/DeliveryTrackingCtl" })
public class DeliveryTrackingCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DeliveryTrackingCtl.class);

	private static final Pattern ORDER_NUMBER_PATTERN = Pattern.compile("^\\d{5}$");

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("orderNumber"))) {

			request.setAttribute("orderNumber",
					PropertyReader.getValue("error.require", "Order Number"));

			pass = false;

		} else if (!ORDER_NUMBER_PATTERN.matcher(request.getParameter("orderNumber")).matches()) {

			request.setAttribute("orderNumber",
					"Order Number must contain 5 digits only");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("customerName"))) {

			request.setAttribute("customerName",
					PropertyReader.getValue("error.require", "Customer Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("deliveryStatus"))) {

			request.setAttribute("deliveryStatus",
					PropertyReader.getValue("error.require", "Delivery Status"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("deliveryDate"))) {

			request.setAttribute("deliveryDate",
					PropertyReader.getValue("error.require", "Delivery Date"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		DeliveryTrackingDTO dto = new DeliveryTrackingDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setOrderNumber(request.getParameter("orderNumber"));
		dto.setCustomerName(request.getParameter("customerName"));
		dto.setDeliveryStatus(request.getParameter("deliveryStatus"));
		dto.setDeliveryDate(DataUtility.getDate(request.getParameter("deliveryDate")));

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

		DeliveryTrackingModelInt model = ModelFactory.getInstance().getDeliveryTrackingModel();

		if (id > 0) {

			try {

				DeliveryTrackingDTO dto = model.findByPK(id);

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

		DeliveryTrackingModelInt model = ModelFactory.getInstance().getDeliveryTrackingModel();

		DeliveryTrackingDTO dto = (DeliveryTrackingDTO) populateDTO(request);

		try {

			if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

				if (dto.getId() > 0) {

					model.update(dto);

					ServletUtility.setSuccessMessage("Delivery Tracking Updated Successfully", request);

					ServletUtility.setDto(dto, request);

					ServletUtility.forward(getView(), request, response);

					return;

				} else {

					model.add(dto);

					request.getSession().setAttribute("successMessage",
							"Delivery Tracking Added Successfully");

					ServletUtility.redirect(ORSView.DELIVERY_TRACKING_CTL, request, response);

					return;
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.DELIVERY_TRACKING_CTL, request, response);

				return;

			} else if (OP_CANCEL.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.DELIVERY_TRACKING_LIST_CTL, request, response);

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

		return ORSView.DELIVERY_TRACKING_VIEW;
	}
}