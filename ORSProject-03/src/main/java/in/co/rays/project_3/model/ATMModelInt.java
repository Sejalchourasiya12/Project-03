package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ATMDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface ATMModelInt {

    public long add(ATMDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(ATMDTO dto) throws ApplicationException;

    public void update(ATMDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(ATMDTO dto) throws ApplicationException;

    public List search(ATMDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public ATMDTO findByPK(long pk) throws ApplicationException;

    public ATMDTO findBySecurityCode(String securityCode) throws ApplicationException;
}