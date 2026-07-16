package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.WeatherAlertDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * WeatherAlertModelInt - Interface for Weather Alert Model
 *
 * @author Sejal Chourasiya
 */
public interface WeatherAlertModelInt {

    public long add(WeatherAlertDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(WeatherAlertDTO dto) throws ApplicationException;

    public void update(WeatherAlertDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(WeatherAlertDTO dto) throws ApplicationException;

    public List search(WeatherAlertDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public WeatherAlertDTO findByPK(long pk) throws ApplicationException;

    public WeatherAlertDTO findByAlertCode(String alertCode) throws ApplicationException;
}