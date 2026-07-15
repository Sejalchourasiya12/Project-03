package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.SmartParkingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * SmartParkingModelInt - Interface for Smart Parking Model
 *
 * @author Sejal Chourasiya
 */
public interface SmartParkingModelInt {

    public long add(SmartParkingDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(SmartParkingDTO dto) throws ApplicationException;

    public void update(SmartParkingDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(SmartParkingDTO dto) throws ApplicationException;

    public List search(SmartParkingDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public SmartParkingDTO findByPK(long pk) throws ApplicationException;

    public SmartParkingDTO findByParkingCode(String parkingCode) throws ApplicationException;
}