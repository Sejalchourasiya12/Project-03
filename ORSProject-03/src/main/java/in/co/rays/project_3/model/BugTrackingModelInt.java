package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BugTrackingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface BugTrackingModelInt {

    public long add(BugTrackingDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(BugTrackingDTO dto) throws ApplicationException;

    public void update(BugTrackingDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(BugTrackingDTO dto) throws ApplicationException;

    public List search(BugTrackingDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public BugTrackingDTO findByPK(long pk) throws ApplicationException;

    public BugTrackingDTO findByProjectName(String projectName) throws ApplicationException;
}