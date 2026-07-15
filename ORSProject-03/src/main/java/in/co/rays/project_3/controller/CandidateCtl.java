package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CandidateDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CandidateModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/CandidateCtl" })
public class CandidateCtl extends BaseCtl {

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(CandidateCtl.class);

    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("candidateCode"))) {
            request.setAttribute("candidateCode",
                    PropertyReader.getValue("error.require", "Candidate Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("candidateName"))) {
            request.setAttribute("candidateName",
                    PropertyReader.getValue("error.require", "Candidate Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("candidateName"))) {
            request.setAttribute("candidateName",
                    "Candidate Name must contain alphabets only");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("email"))) {
            request.setAttribute("email",
                    PropertyReader.getValue("error.require", "Email"));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("email"))) {
            request.setAttribute("email",
                    "Invalid Email format");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("skillSet"))) {
            request.setAttribute("skillSet",
                    PropertyReader.getValue("error.require", "Skill Set"));
            pass = false;
        }

        return pass;
    }

    protected BaseDTO populateDTO(HttpServletRequest request) {

        CandidateDTO dto = new CandidateDTO();

        dto.setCandidateCode(request.getParameter("candidateCode"));
        dto.setCandidateName(request.getParameter("candidateName"));
        dto.setEmail(request.getParameter("email"));
        dto.setSkillSet(request.getParameter("skillSet"));

        populateBean(dto, request);

        return dto;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String op = request.getParameter("operation");
        long id = DataUtility.getLong(request.getParameter("id"));

        CandidateModelInt model = ModelFactory.getInstance().getCandidateModel();

        if (id > 0 || op != null) {

            CandidateDTO dto;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String op = request.getParameter("operation");
        long id = DataUtility.getLong(request.getParameter("id"));

        CandidateModelInt model = ModelFactory.getInstance().getCandidateModel();

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            CandidateDTO dto = (CandidateDTO) populateDTO(request);

            try {

                if (id > 0) {

                    dto.setId(id);
                    model.update(dto);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.setSuccessMessage("Record Successfully Updated", request);

                } else {

                    model.add(dto);
                    ServletUtility.setSuccessMessage("Record Successfully Saved", request);
                }

            } catch (ApplicationException e) {

                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;

            } catch (DuplicateRecordException e) {

                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage("Candidate Already Exists", request);
            }

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CANDIDATE_CTL, request, response);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CANDIDATE_LIST_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.CANDIDATE_VIEW;
    }
}