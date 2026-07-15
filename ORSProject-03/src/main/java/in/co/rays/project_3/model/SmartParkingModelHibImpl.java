package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SmartParkingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * SmartParkingModelHibImpl - Hibernate Implementation for Smart Parking
 *
 * @author Sejal Chourasiya
 */
public class SmartParkingModelHibImpl implements SmartParkingModelInt {

    @Override
    public long add(SmartParkingDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        SmartParkingDTO duplicate = findByParkingCode(dto.getParkingCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Parking Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add SmartParking: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(SmartParkingDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete SmartParking: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(SmartParkingDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        SmartParkingDTO duplicate = findByParkingCode(dto.getParkingCode());
        if (duplicate != null && !duplicate.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("Parking Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update SmartParking: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(SmartParkingDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list SmartParking: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(SmartParkingDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(SmartParkingDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SmartParkingDTO.class);

            if (dto.getParkingCode() != null && dto.getParkingCode().trim().length() > 0) {
                criteria.add(Restrictions.like("parkingCode", dto.getParkingCode().trim() + "%"));
            }
            if (dto.getVechicleNumber() != null && dto.getVechicleNumber().trim().length() > 0) {
                criteria.add(Restrictions.like("vechicleNumber", dto.getVechicleNumber().trim() + "%"));
            }
            if (dto.getSlotNumber() != null && dto.getSlotNumber().trim().length() > 0) {
                criteria.add(Restrictions.like("slotNumber", dto.getSlotNumber().trim() + "%"));
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
            throw new ApplicationException("Exception in search SmartParking: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public SmartParkingDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        SmartParkingDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (SmartParkingDTO) session.get(SmartParkingDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK SmartParking: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public SmartParkingDTO findByParkingCode(String parkingCode) throws ApplicationException {

        Session session = null;
        SmartParkingDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SmartParkingDTO.class);
            criteria.add(Restrictions.eq("parkingCode", parkingCode));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (SmartParkingDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByParkingCode: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}