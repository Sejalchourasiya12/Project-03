package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.EnergyConsumptionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * EnergyConsumptionModelHibImpl - Hibernate Implementation for Energy Consumption
 *
 * @author Sejal Chourasiya
 */
public class EnergyConsumptionModelHibImpl implements EnergyConsumptionModelInt {

    @Override
    public long add(EnergyConsumptionDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        EnergyConsumptionDTO duplicate = findByEnergyCode(dto.getEnergyCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Energy Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add EnergyConsumption: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(EnergyConsumptionDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete EnergyConsumption: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(EnergyConsumptionDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        EnergyConsumptionDTO duplicate = findByEnergyCode(dto.getEnergyCode());
        if (duplicate != null && !duplicate.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("Energy Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update EnergyConsumption: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(EnergyConsumptionDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list EnergyConsumption: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(EnergyConsumptionDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(EnergyConsumptionDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(EnergyConsumptionDTO.class);

            if (dto.getEnergyCode() != null && dto.getEnergyCode().trim().length() > 0) {
                criteria.add(Restrictions.like("energyCode", dto.getEnergyCode().trim() + "%"));
            }
            if (dto.getDeviceName() != null && dto.getDeviceName().trim().length() > 0) {
                criteria.add(Restrictions.like("deviceName", dto.getDeviceName().trim() + "%"));
            }
            if (dto.getUnitsConsumed() != null && dto.getUnitsConsumed().trim().length() > 0) {
                criteria.add(Restrictions.like("unitsConsumed", dto.getUnitsConsumed().trim() + "%"));
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
            throw new ApplicationException("Exception in search EnergyConsumption: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public EnergyConsumptionDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        EnergyConsumptionDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (EnergyConsumptionDTO) session.get(EnergyConsumptionDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK EnergyConsumption: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public EnergyConsumptionDTO findByEnergyCode(String energyCode) throws ApplicationException {

        Session session = null;
        EnergyConsumptionDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(EnergyConsumptionDTO.class);
            criteria.add(Restrictions.eq("energyCode", energyCode));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (EnergyConsumptionDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByEnergyCode: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}