package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.FoodDeliveryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.FoodDeliveryModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "FoodDeliveryCtl", urlPatterns = "/ctl/FoodDeliveryCtl")
public class FoodDeliveryCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_CANCEL.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("orderNumber"))) {
            request.setAttribute("orderNumber",
                    PropertyReader.getValue("error.require", "Order Number"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("restaurantName"))) {
            request.setAttribute("restaurantName",
                    PropertyReader.getValue("error.require", "Restaurant Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("deliveryAddress"))) {
            request.setAttribute("deliveryAddress",
                    PropertyReader.getValue("error.require", "Delivery Address"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("orderAmount"))) {
            request.setAttribute("orderAmount",
                    PropertyReader.getValue("error.require", "Order Amount"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("deliveryPartner"))) {
            request.setAttribute("deliveryPartner",
                    PropertyReader.getValue("error.require", "Delivery Partner"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("deliveryStatus"))) {
            request.setAttribute("deliveryStatus",
                    PropertyReader.getValue("error.require", "Delivery Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        FoodDeliveryDTO dto = new FoodDeliveryDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setOrderNumber(DataUtility.getString(request.getParameter("orderNumber")));
        dto.setRestaurantName(DataUtility.getString(request.getParameter("restaurantName")));
        dto.setDeliveryAddress(DataUtility.getString(request.getParameter("deliveryAddress")));
        dto.setOrderAmount(DataUtility.getDouble(request.getParameter("orderAmount")));
        dto.setDeliveryPartner(DataUtility.getString(request.getParameter("deliveryPartner")));
        dto.setDeliveryStatus(DataUtility.getString(request.getParameter("deliveryStatus")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        FoodDeliveryModelInt model =
                ModelFactory.getInstance().getFoodDeliveryModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (id > 0) {
            try {
                FoodDeliveryDTO dto = model.findByPK(id);
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

        FoodDeliveryModelInt model =
                ModelFactory.getInstance().getFoodDeliveryModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            FoodDeliveryDTO dto = (FoodDeliveryDTO) populateDTO(request);

            try {
                if (id > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage(
                            "Food Order Updated Successfully", request);
                    ServletUtility.setDto(dto, request);
                } else {
                    try {
                        model.add(dto);
                        ServletUtility.setSuccessMessage(
                                "Food Order Saved Successfully", request);
                    } catch (DuplicateRecordException e) {
                        ServletUtility.setDto(dto, request);
                        ServletUtility.setErrorMessage(
                                "Order Number already exists", request);
                    }
                }

            } catch (ApplicationException e) {
                e.printStackTrace();
            } catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            FoodDeliveryDTO dto = (FoodDeliveryDTO) populateDTO(request);
            try {
                model.delete(dto);
            } catch (ApplicationException e) {
                e.printStackTrace();
            }

            ServletUtility.redirect(
                    ORSView.FOOD_DELIVERY_LIST_CTL, request, response);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(
                    ORSView.FOOD_DELIVERY_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(
                    ORSView.FOOD_DELIVERY_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.FOOD_DELIVERY_VIEW;
    }
}
