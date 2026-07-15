package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PenaltyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface PenaltyModelInt {

    public long add(PenaltyDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(PenaltyDTO dto) throws ApplicationException;

    public void update(PenaltyDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(PenaltyDTO dto) throws ApplicationException;

    public List search(PenaltyDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public PenaltyDTO findByPK(long pk) throws ApplicationException;

    public PenaltyDTO findByPenaltyCode(String penaltyCode) throws ApplicationException;
}