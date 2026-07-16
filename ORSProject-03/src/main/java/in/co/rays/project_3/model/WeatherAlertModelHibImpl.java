package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.WeatherAlertDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * WeatherAlertModelHibImpl - Hibernate Implementation for Weather Alert
 *
 * @author Sejal Chourasiya
 */
public class WeatherAlertModelHibImpl implements WeatherAlertModelInt {

    @Override
    public long add(WeatherAlertDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        WeatherAlertDTO duplicate = findByAlertCode(dto.getAlertCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Alert Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add WeatherAlert: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(WeatherAlertDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete WeatherAlert: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(WeatherAlertDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        WeatherAlertDTO duplicate = findByAlertCode(dto.getAlertCode());
        if (duplicate != null && !duplicate.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("Alert Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update WeatherAlert: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public List list() throws ApplicationException {
        return list(0, 0);
    }

    @Override
    public List list(int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(WeatherAlertDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list WeatherAlert: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(WeatherAlertDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(WeatherAlertDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(WeatherAlertDTO.class);

            if (dto.getAlertCode() != null && dto.getAlertCode().trim().length() > 0) {
                criteria.add(Restrictions.like("alertCode", dto.getAlertCode().trim() + "%"));
            }
            if (dto.getCityName() != null && dto.getCityName().trim().length() > 0) {
                criteria.add(Restrictions.like("cityName", dto.getCityName().trim() + "%"));
            }
            if (dto.getTemperature() != null && dto.getTemperature().trim().length() > 0) {
                criteria.add(Restrictions.like("temperature", dto.getTemperature().trim() + "%"));
            }
            if (dto.getStatus() != null && dto.getStatus().trim().length() > 0) {
                criteria.add(Restrictions.like("status", dto.getStatus().trim() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search WeatherAlert: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public WeatherAlertDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        WeatherAlertDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (WeatherAlertDTO) session.get(WeatherAlertDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK WeatherAlert: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public WeatherAlertDTO findByAlertCode(String alertCode) throws ApplicationException {

        Session session = null;
        WeatherAlertDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(WeatherAlertDTO.class);
            criteria.add(Restrictions.eq("alertCode", alertCode));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (WeatherAlertDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByAlertCode: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}