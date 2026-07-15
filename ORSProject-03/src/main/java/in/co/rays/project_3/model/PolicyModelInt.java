package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.PolicyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * PolicyModelInt - Interface for Policy Model
 * 
 * @author Sejal Chourasiya
 */
public interface PolicyModelInt {

    public long add(PolicyDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(PolicyDTO dto) throws ApplicationException;

    public void update(PolicyDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(PolicyDTO dto) throws ApplicationException;

    public List search(PolicyDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public PolicyDTO findByPK(long pk) throws ApplicationException;

    public PolicyDTO findByPolicyCode(String policyCode) throws ApplicationException;
}