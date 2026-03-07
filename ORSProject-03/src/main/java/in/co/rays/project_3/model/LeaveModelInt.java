package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.LeaveDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Leave model
 * 
 * @author sejal chourasiya
 *
 */
public interface LeaveModelInt {

    public long add(LeaveDTO dto) 
            throws DatabaseException, DuplicateRecordException;

    public void delete(LeaveDTO dto) 
            throws DatabaseException;

    public void update(LeaveDTO dto) 
            throws DatabaseException, DuplicateRecordException;

    public List list() 
            throws DatabaseException;

    public List search(LeaveDTO dto, int pageNo, int pageSize) 
            throws DatabaseException;

    public LeaveDTO findByPK(long pk) 
            throws DatabaseException;

    public LeaveDTO findByLeaveCode(String leaveCode) 
            throws DatabaseException;

    List list(int pageNo, int pageSize) 
            throws DatabaseException;

}