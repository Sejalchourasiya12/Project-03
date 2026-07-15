package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.FoodDeliveryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.FoodDeliveryModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "FoodDeliveryListCtl", urlPatterns = { "/ctl/FoodDeliveryListCtl" })
public class FoodDeliveryListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(FoodDeliveryListCtl.class);

    @Override
    protected void preload(HttpServletRequest request) {
        FoodDeliveryModelInt model =
                ModelFactory.getInstance().getFoodDeliveryModel();
        try {
            List list = model.list();
            request.setAttribute("foodDeliveryList", list);
        } catch (Exception e) {
        }
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        FoodDeliveryDTO dto = new FoodDeliveryDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setOrderNumber(request.getParameter("orderNumber"));
        dto.setRestaurantName(request.getParameter("restaurantName"));
        dto.setDeliveryPartner(request.getParameter("deliveryPartner"));
        dto.setDeliveryStatus(request.getParameter("deliveryStatus"));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(
                PropertyReader.getValue("page.size"));

        FoodDeliveryDTO dto =
                (FoodDeliveryDTO) populateDTO(request);

        FoodDeliveryModelInt model =
                ModelFactory.getInstance().getFoodDeliveryModel();

        try {

            List list = model.search(dto, pageNo, pageSize);
            List next = model.search(dto, pageNo + 1, pageSize);

            ServletUtility.setList(list, request);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", "0");
            } else {
                request.setAttribute("nextListSize", next.size());
            }

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        List list;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        String op = DataUtility.getString(request.getParameter("operation"));

        FoodDeliveryModelInt model =
                ModelFactory.getInstance().getFoodDeliveryModel();

        FoodDeliveryDTO dto =
                (FoodDeliveryDTO) populateDTO(request);

        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op)
                    || OP_NEXT.equalsIgnoreCase(op)
                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.FOOD_DELIVERY_CTL,
                        request, response);
                return;

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.FOOD_DELIVERY_LIST_CTL,
                        request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    FoodDeliveryDTO deleteBean =
                            new FoodDeliveryDTO();

                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage(
                            "Data Deleted Successfully", request);

                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }
            }

            list = model.search(dto, pageNo, pageSize);
            List next = model.search(dto, pageNo + 1, pageSize);

            ServletUtility.setDto(dto, request);
            ServletUtility.setList(list, request);

            if (list == null || list.size() == 0
                    && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage(
                        "No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", "0");
            } else {
                request.setAttribute("nextListSize", next.size());
            }

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
        }
    }

    @Override
    protected String getView() {
        return ORSView.FOOD_DELIVERY_LIST_VIEW;
    }
}