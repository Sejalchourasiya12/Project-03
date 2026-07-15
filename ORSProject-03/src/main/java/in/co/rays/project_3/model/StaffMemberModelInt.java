package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.StaffMemberDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of StaffMember model
 * 
 * @author Sejal Chourasiya
 *
 */
public interface StaffMemberModelInt {

    public long add(StaffMemberDTO dto)
            throws ApplicationException, DuplicateRecordException;

    public void delete(StaffMemberDTO dto)
            throws ApplicationException;

    public void update(StaffMemberDTO dto)
            throws ApplicationException, DuplicateRecordException;

    public StaffMemberDTO findByPK(long pk)
            throws ApplicationException;

    public List list()
            throws ApplicationException;

    public List list(int pageNo, int pageSize)
            throws ApplicationException;

    

    public List search(StaffMemberDTO dto, int pageNo, int pageSize)
            throws ApplicationException, DuplicateRecordException;
    
    
    public List<StaffMemberDTO> search(StaffMemberDTO dto)
            throws ApplicationException;

}

