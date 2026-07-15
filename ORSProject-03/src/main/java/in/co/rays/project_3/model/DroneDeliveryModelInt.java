package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.DroneDeliveryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * DroneDeliveryModelInt - Interface for Drone Delivery Model
 *
 * @author Sejal Chourasiya
 */
public interface DroneDeliveryModelInt {

    public long add(DroneDeliveryDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(DroneDeliveryDTO dto) throws ApplicationException;

    public void update(DroneDeliveryDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(DroneDeliveryDTO dto) throws ApplicationException;

    public List search(DroneDeliveryDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public DroneDeliveryDTO findByPK(long pk) throws ApplicationException;

    public DroneDeliveryDTO findByDroneCode(String droneCode) throws ApplicationException;
}