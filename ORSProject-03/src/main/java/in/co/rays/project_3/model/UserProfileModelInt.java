package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.UserProfileDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface UserProfileModelInt {

    public long add(UserProfileDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(UserProfileDTO dto) throws ApplicationException;

    public void update(UserProfileDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(UserProfileDTO dto) throws ApplicationException;

    public List search(UserProfileDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public UserProfileDTO findByPK(long pk) throws ApplicationException;

    public UserProfileDTO findByProfileCode(String profileCode) throws ApplicationException;
}