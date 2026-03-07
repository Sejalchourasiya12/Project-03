package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.QueueDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface QueueModelInt {

    public long add(QueueDTO dto)
            throws ApplicationException, DuplicateRecordException;

    public void update(QueueDTO dto)
            throws ApplicationException, DuplicateRecordException;

    public void delete(QueueDTO dto)
            throws ApplicationException;

    public QueueDTO findByPK(long pk)
            throws ApplicationException;

    public QueueDTO findByCode(String code)
            throws ApplicationException;

    public List search(QueueDTO dto, int pageNo, int pageSize)
            throws ApplicationException;

    public List list(int pageNo, int pageSize)
            throws ApplicationException;
}
