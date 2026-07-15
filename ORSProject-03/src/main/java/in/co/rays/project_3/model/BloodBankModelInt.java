package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BloodBankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface BloodBankModelInt {

    public long add(BloodBankDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(BloodBankDTO dto) throws ApplicationException;

    public void update(BloodBankDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(BloodBankDTO dto) throws ApplicationException;

    public List search(BloodBankDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public BloodBankDTO findByPK(long pk) throws ApplicationException;

    public BloodBankDTO findByContactNumber(String contactNumber) throws ApplicationException;
}