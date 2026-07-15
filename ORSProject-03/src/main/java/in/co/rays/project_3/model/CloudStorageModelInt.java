package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.CloudStorageDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface CloudStorageModelInt {

    public long add(CloudStorageDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(CloudStorageDTO dto) throws ApplicationException;

    public void update(CloudStorageDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(CloudStorageDTO dto) throws ApplicationException;

    public List search(CloudStorageDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public CloudStorageDTO findByPK(long pk) throws ApplicationException;

    public CloudStorageDTO findByFileName(String fileName) throws ApplicationException;
    
}