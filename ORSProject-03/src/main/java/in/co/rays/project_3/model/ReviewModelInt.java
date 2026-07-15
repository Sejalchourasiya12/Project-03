package in.co.rays.project_3.model;
import java.util.List;
import in.co.rays.project_3.dto.ReviewDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface ReviewModelInt {
		
		
		public long add(ReviewDTO dto)throws ApplicationException,DuplicateRecordException;
		public void delete(ReviewDTO dto)throws ApplicationException;
		public void update(ReviewDTO dto)throws ApplicationException,DuplicateRecordException;
		public List list()throws ApplicationException;
		public List list(int pageNo,int pageSize)throws ApplicationException;
		public List search(ReviewDTO dto)throws ApplicationException;
		public List search(ReviewDTO dto,int pageNo,int pageSize)throws ApplicationException;
		public ReviewDTO findByPK(long pk)throws ApplicationException;
		public ReviewDTO findByName(String name)throws ApplicationException;

	}



