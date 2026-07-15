package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PromotionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface PromotionModelInt {
	public long add(PromotionDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(PromotionDTO dto)throws ApplicationException;
	public void update(PromotionDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(PromotionDTO dto)throws ApplicationException;
	public List search(PromotionDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public PromotionDTO findByPK(long pk)throws ApplicationException;
	public PromotionDTO findByName(String name)throws ApplicationException;

}
