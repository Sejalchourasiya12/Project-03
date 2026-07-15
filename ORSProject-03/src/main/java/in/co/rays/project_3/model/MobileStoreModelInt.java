package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.MobileStoreDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * MobileStoreModelInt - Interface for Mobile Store Model
 *
 * @author Sejal Chourasiya
 */
public interface MobileStoreModelInt {

    public long add(MobileStoreDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(MobileStoreDTO dto) throws ApplicationException;

    public void update(MobileStoreDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(MobileStoreDTO dto) throws ApplicationException;

    public List search(MobileStoreDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public MobileStoreDTO findByPK(long pk) throws ApplicationException;

    public MobileStoreDTO findByModelName(String modelName) throws ApplicationException;
}