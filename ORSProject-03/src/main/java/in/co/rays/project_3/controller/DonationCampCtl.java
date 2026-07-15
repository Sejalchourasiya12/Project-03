package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.DonationCampDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.DonationCampModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "DonationCampCtl", urlPatterns = { "/ctl/DonationCampCtl" })
public class DonationCampCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(DonationCampCtl.class);

    // ✅ preload — static organizer list
    @Override
    protected void preload(HttpServletRequest request) {
        LinkedHashMap<String, String> organizerMap = new LinkedHashMap<>();
        organizerMap.put("Dr. Rajesh Sharma",  "Dr. Rajesh Sharma");
        organizerMap.put("Dr. Priya Singh",    "Dr. Priya Singh");
        organizerMap.put("Dr. Amit Verma",     "Dr. Amit Verma");
        organizerMap.put("Dr. Sunita Patel",   "Dr. Sunita Patel");
        organizerMap.put("Dr. Rahul Gupta",    "Dr. Rahul Gupta");
        request.setAttribute("organizerMap", organizerMap);
    }

    // ✅ validate — AnnouncementCtl jaisa pattern
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = request.getParameter("operation");
        String campName = request.getParameter("campName");

        if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        
        if (DataValidator.isNull(campName)) {
            request.setAttribute("campName",
                    PropertyReader.getValue("error.require", "Camp Name"));
            pass = false;
        } else if (!campName.matches("[a-zA-Z\\s]+")) {
            request.setAttribute("campName",
                    "Camp Name must contain alphabets only");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("campDate"))) {
            request.setAttribute("campDate",
                    PropertyReader.getValue("error.require", "Camp Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("organizer"))) {
            request.setAttribute("organizer",
                    PropertyReader.getValue("error.require", "Organizer"));
            pass = false;
        }

        return pass;
    }

    // ✅ populateDTO — AnnouncementCtl jaisa pattern
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        log.debug("DonationCampCtl populateDTO start");

        DonationCampDTO dto = new DonationCampDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setCampName(DataUtility.getString(request.getParameter("campName")));
        dto.setCampDate(DataUtility.getDate(request.getParameter("campDate")));
        dto.setOrganizer(DataUtility.getString(request.getParameter("organizer")));

        populateBean(dto, request);

        log.debug("DonationCampCtl populateDTO end");

        return dto;
    }

    // ✅ doGet — AnnouncementCtl jaisa pattern
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        DonationCampModelInt model = ModelFactory.getInstance().getDonationCampModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (id > 0) {
            try {
                DonationCampDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, request);
            } catch (Exception e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    // ✅ doPost — AnnouncementCtl jaisa pattern
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("DonationCampCtl doPost start");

        String op = DataUtility.getString(request.getParameter("operation"));

        DonationCampModelInt model = ModelFactory.getInstance().getDonationCampModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            DonationCampDTO dto = (DonationCampDTO) populateDTO(request);

            try {
                if (id > 0) {
                    // UPDATE
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Data is successfully Updated", request);
                    ServletUtility.setDto(dto, request);

                } else {
                    // SAVE
                    try {
                        model.add(dto);
                        ServletUtility.setSuccessMessage("Data is successfully Saved", request);
                    } catch (DuplicateRecordException e) {
                        ServletUtility.setDto(dto, request);
                        ServletUtility.setErrorMessage("Donation Camp already exists", request);
                    }
                }

            } catch (DuplicateRecordException e) {
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage("Donation Camp already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            DonationCampDTO dto = (DonationCampDTO) populateDTO(request);
            try {
                model.delete(dto);
            } catch (ApplicationException e) {
                e.printStackTrace();
            }
            ServletUtility.redirect(ORSView.DONATION_CAMP_LIST_CTL, request, response);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DONATION_CAMP_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DONATION_CAMP_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);

        log.debug("DonationCampCtl doPost end");
    }

    @Override
    protected String getView() {
        return ORSView.DONATION_CAMP_VIEW;
    }
}