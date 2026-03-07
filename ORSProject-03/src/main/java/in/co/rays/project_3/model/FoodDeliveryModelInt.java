
	
	package in.co.rays.project_3.model;

	import java.util.List;

	import in.co.rays.project_3.dto.FoodDeliveryDTO;
	
	import in.co.rays.project_3.exception.ApplicationException;
	import in.co.rays.project_3.exception.DuplicateRecordException;
	/**
	 * Interface of College model
	 *  @author Sejal Chourasiya
	 *
	 */
	public interface FoodDeliveryModelInt {
	public long add(FoodDeliveryDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(FoodDeliveryDTO dto)throws ApplicationException;
	public void update(FoodDeliveryDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(FoodDeliveryDTO dto)throws ApplicationException;
	public List search(FoodDeliveryDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public FoodDeliveryDTO findByPK(long pk)throws ApplicationException;
	public FoodDeliveryDTO findByTitle(String name)throws ApplicationException;
	}



