package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EnergyConsumptionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.EnergyConsumptionModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "EnergyConsumptionListCtl", urlPatterns = { "/ctl/EnergyConsumptionListCtl" })
public class EnergyConsumptionListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(EnergyConsumptionListCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {
        return true;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        EnergyConsumptionDTO dto = new EnergyConsumptionDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setEnergyCode(DataUtility.getString(request.getParameter("energyCode")));
        dto.setUnitsConsumed(DataUtility.getString(request.getParameter("unitsConsumed")));

        String deviceName = request.getParameter("deviceName");
        if (deviceName == null || deviceName.trim().startsWith("--") || deviceName.trim().isEmpty()) {
            dto.setDeviceName("");
        } else {
            dto.setDeviceName(deviceName.trim());
        }

        String status = request.getParameter("status");
        if (status == null || status.trim().startsWith("--") || status.trim().isEmpty()) {
            dto.setStatus("");
        } else {
            dto.setStatus(status.trim());
        }

        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        EnergyConsumptionDTO dto = (EnergyConsumptionDTO) populateDTO(request);
        EnergyConsumptionModelInt model = ModelFactory.getInstance().getEnergyConsumptionModel();

        try {
            List list = model.search(dto, pageNo, pageSize);
            List next = model.search(dto, pageNo + 1, pageSize);

            ServletUtility.setList(list, request);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            request.setAttribute("nextListSize",
                    (next == null || next.size() == 0) ? "0" : String.valueOf(next.size()));

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        List list;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo   = (pageNo   == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        String op = DataUtility.getString(request.getParameter("operation"));

        EnergyConsumptionModelInt model = ModelFactory.getInstance().getEnergyConsumptionModel();
        EnergyConsumptionDTO dto = (EnergyConsumptionDTO) populateDTO(request);
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op)) {
                pageNo = 1;
            } else if (OP_NEXT.equalsIgnoreCase(op)) {
                pageNo++;
            } else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
                pageNo--;
            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ENERGY_CONSUMPTION_CTL, request, response);
                return;
            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ENERGY_CONSUMPTION_LIST_CTL, request, response);
                return;
            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {
                    EnergyConsumptionDTO deleteBean = new EnergyConsumptionDTO();
                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }
                    ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }

            list = model.search(dto, pageNo, pageSize);
            List next = model.search(dto, pageNo + 1, pageSize);

            ServletUtility.setDto(dto, request);
            ServletUtility.setList(list, request);
            if ((list == null || list.size() == 0) && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            request.setAttribute("nextListSize",
                    (next == null || next.size() == 0) ? "0" : String.valueOf(next.size()));

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
        return ORSView.ENERGY_CONSUMPTION_LIST_VIEW;
    }
}