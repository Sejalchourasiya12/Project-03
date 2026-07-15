package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DroneDeliveryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * DroneDeliveryModelHibImpl - Hibernate Implementation for Drone Delivery
 *
 * @author Sejal Chourasiya
 */
public class DroneDeliveryModelHibImpl implements DroneDeliveryModelInt {

    @Override
    public long add(DroneDeliveryDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        DroneDeliveryDTO duplicate = findByDroneCode(dto.getDroneCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Drone Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add DroneDelivery: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(DroneDeliveryDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete DroneDelivery: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(DroneDeliveryDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        DroneDeliveryDTO duplicate = findByDroneCode(dto.getDroneCode());
        if (duplicate != null && !duplicate.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("Drone Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update DroneDelivery: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(DroneDeliveryDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list DroneDelivery: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(DroneDeliveryDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(DroneDeliveryDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(DroneDeliveryDTO.class);

            if (dto.getDroneCode() != null && dto.getDroneCode().trim().length() > 0) {
                criteria.add(Restrictions.like("droneCode", dto.getDroneCode().trim() + "%"));
            }
            if (dto.getOperatorName() != null && dto.getOperatorName().trim().length() > 0) {
                criteria.add(Restrictions.like("operatorName", dto.getOperatorName().trim() + "%"));
            }
            if (dto.getDeliveryZone() != null && dto.getDeliveryZone().trim().length() > 0) {
                criteria.add(Restrictions.like("deliveryZone", dto.getDeliveryZone().trim() + "%"));
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
            throw new ApplicationException("Exception in search DroneDelivery: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public DroneDeliveryDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        DroneDeliveryDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (DroneDeliveryDTO) session.get(DroneDeliveryDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK DroneDelivery: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public DroneDeliveryDTO findByDroneCode(String droneCode) throws ApplicationException {

        Session session = null;
        DroneDeliveryDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(DroneDeliveryDTO.class);
            criteria.add(Restrictions.eq("droneCode", droneCode));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (DroneDeliveryDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByDroneCode: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}