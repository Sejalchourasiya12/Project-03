package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DonationCampDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface DonationCampModelInt {
	
	public long add(DonationCampDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(DonationCampDTO dto)throws ApplicationException;
	public void update(DonationCampDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(DonationCampDTO dto)throws ApplicationException;
	public List search(DonationCampDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public DonationCampDTO findByPK(long pk)throws ApplicationException;
	public DonationCampDTO findByOrderNumber(String name)throws ApplicationException;

}
