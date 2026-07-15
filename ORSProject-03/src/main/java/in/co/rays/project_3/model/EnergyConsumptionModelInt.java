package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.EnergyConsumptionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * EnergyConsumptionModelInt - Interface for Energy Consumption Model
 *
 * @author Sejal Chourasiya
 */
public interface EnergyConsumptionModelInt {

    public long add(EnergyConsumptionDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(EnergyConsumptionDTO dto) throws ApplicationException;

    public void update(EnergyConsumptionDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(EnergyConsumptionDTO dto) throws ApplicationException;

    public List search(EnergyConsumptionDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public EnergyConsumptionDTO findByPK(long pk) throws ApplicationException;

    public EnergyConsumptionDTO findByEnergyCode(String energyCode) throws ApplicationException;
}