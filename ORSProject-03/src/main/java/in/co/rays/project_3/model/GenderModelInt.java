package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.GenderDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface GenderModelInt {

    public long add(GenderDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(GenderDTO dto) throws ApplicationException;

    public void update(GenderDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(GenderDTO dto) throws ApplicationException;

    public List search(GenderDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public GenderDTO findByPK(long pk) throws ApplicationException;

    public GenderDTO findByGenderCode(String genderCode) throws ApplicationException;
}