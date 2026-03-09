package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DeliveryTrackingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface DeliveryTrackingModelInt {
	
	public long add(DeliveryTrackingDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(DeliveryTrackingDTO dto)throws ApplicationException;
	public void update(DeliveryTrackingDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(DeliveryTrackingDTO dto)throws ApplicationException;
	public List search(DeliveryTrackingDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public DeliveryTrackingDTO findByPK(long pk)throws ApplicationException;
	public DeliveryTrackingDTO findByOrderNumber(String name)throws ApplicationException;

}
