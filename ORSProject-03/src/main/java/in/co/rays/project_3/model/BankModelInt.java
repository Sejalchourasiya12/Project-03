package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

/**
 * Interface of Bank model
 * 
 * @author Sejal Chourasiya
 */
public interface BankModelInt {

	public long add(BankDTO dto) 
			throws ApplicationException, DuplicateRecordException;

	public void delete(BankDTO dto) 
			throws ApplicationException;

	public void update(BankDTO dto) 
			throws ApplicationException, DuplicateRecordException;

	public BankDTO findByPK(long pk) 
			throws ApplicationException;

	public BankDTO findByAccountNo(String accountNo) 
			throws ApplicationException;

	public List list() 
			throws ApplicationException;

	public List list(int pageNo, int pageSize) 
			throws ApplicationException;

	public List search(BankDTO dto) 
			throws ApplicationException;

	public List search(BankDTO dto, int pageNo, int pageSize) 
			throws ApplicationException;

	public boolean deposit(long bankId, double amount) 
			throws ApplicationException, RecordNotFoundException;

	public boolean withdraw(long bankId, double amount) 
			throws ApplicationException, RecordNotFoundException;

	
}
