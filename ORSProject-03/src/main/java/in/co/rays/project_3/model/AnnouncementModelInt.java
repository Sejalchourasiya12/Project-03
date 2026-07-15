package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.AnnouncementDTO;
import in.co.rays.project_3.dto.CollegeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
/**
 * Interface of College model
 *  @author Sejal Chourasiya
 *
 */
public interface AnnouncementModelInt {
public long add(AnnouncementDTO dto)throws ApplicationException,DuplicateRecordException;
public void delete(AnnouncementDTO dto)throws ApplicationException;
public void update(AnnouncementDTO dto)throws ApplicationException,DuplicateRecordException;
public List list()throws ApplicationException;
public List list(int pageNo,int pageSize)throws ApplicationException;
public List search(AnnouncementDTO dto)throws ApplicationException;
public List search(AnnouncementDTO dto,int pageNo,int pageSize)throws ApplicationException;
public AnnouncementDTO findByPK(long pk)throws ApplicationException;
public AnnouncementDTO findByTitle(String name)throws ApplicationException;
}
