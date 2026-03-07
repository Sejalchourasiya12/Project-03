package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.TrackingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface TrackingModelInt {
	
	public long add(TrackingDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(TrackingDTO dto)throws ApplicationException;
	public void update(TrackingDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(TrackingDTO dto)throws ApplicationException;
	public List search(TrackingDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public TrackingDTO findByPK(long pk)throws ApplicationException;
	public TrackingDTO findByName(String name)throws ApplicationException;

}
