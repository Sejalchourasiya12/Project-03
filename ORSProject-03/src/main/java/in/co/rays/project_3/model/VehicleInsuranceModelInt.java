package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.VehicleInsuranceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * VehicleInsuranceModelInt - Interface for Vehicle Insurance Model
 *
 * @author Sejal Chourasiya
 */
public interface VehicleInsuranceModelInt {

    public long add(VehicleInsuranceDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(VehicleInsuranceDTO dto) throws ApplicationException;

    public void update(VehicleInsuranceDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(VehicleInsuranceDTO dto) throws ApplicationException;

    public List search(VehicleInsuranceDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public VehicleInsuranceDTO findByPK(long pk) throws ApplicationException;

    public VehicleInsuranceDTO findByVehicleNumber(String vehicleNumber) throws ApplicationException;
}