package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.EnrollmentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface EnrollmentModelInt {

    public long add(EnrollmentDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(EnrollmentDTO dto) throws ApplicationException;

    public void update(EnrollmentDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(EnrollmentDTO dto) throws ApplicationException;

    public List search(EnrollmentDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public EnrollmentDTO findByPK(long pk) throws ApplicationException;

    public EnrollmentDTO findByEnrollmentCode(String enrollmentCode) throws ApplicationException;

}