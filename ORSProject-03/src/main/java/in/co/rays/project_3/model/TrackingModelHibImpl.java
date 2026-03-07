package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.TrackingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class TrackingModelHibImpl implements TrackingModelInt {

    @Override
    public long add(TrackingDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        TrackingDTO duplicateTracking = findByName(dto.getTrackingNumber());

        if (duplicateTracking != null) {
            throw new DuplicateRecordException("Tracking Number already exists");
        }

        try {

            session = HibDataSource.getSession();
            tx = session.beginTransaction();

            session.save(dto);
            tx.commit();

        } catch (HibernateException e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new ApplicationException("Exception in Tracking Add " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(TrackingDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {

            session = HibDataSource.getSession();
            tx = session.beginTransaction();

            session.delete(dto);
            tx.commit();

        } catch (HibernateException e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new ApplicationException("Exception in Tracking Delete " + e.getMessage());

        } finally {
            session.close();
        }
    }

    @Override
    public void update(TrackingDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        try {

            session = HibDataSource.getSession();
            tx = session.beginTransaction();

            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new ApplicationException("Exception in Tracking Update " + e.getMessage());

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

            Criteria criteria = session.createCriteria(TrackingDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Tracking List");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(TrackingDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(TrackingDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {

            session = HibDataSource.getSession();

            Criteria criteria = session.createCriteria(TrackingDTO.class);

            if (dto.getTrackingNumber() != null && dto.getTrackingNumber().length() > 0) {
                criteria.add(Restrictions.like("trackingNumber", dto.getTrackingNumber() + "%"));
            }

            if (dto.getCurrentLocation() != null && dto.getCurrentLocation().length() > 0) {
                criteria.add(Restrictions.like("currentLocation", dto.getCurrentLocation() + "%"));
            }

            if (dto.getStatus() != null && dto.getStatus().length() > 0) {
                criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Tracking Search");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public TrackingDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        TrackingDTO dto = null;

        try {

            session = HibDataSource.getSession();

            dto = (TrackingDTO) session.get(TrackingDTO.class, pk);

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Tracking findByPK");

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public TrackingDTO findByName(String name) throws ApplicationException {

        Session session = null;
        TrackingDTO dto = null;

        try {

            session = HibDataSource.getSession();

            Criteria criteria = session.createCriteria(TrackingDTO.class);

            criteria.add(Restrictions.eq("trackingNumber", name));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (TrackingDTO) list.get(0);
            }

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Tracking findByName");

        } finally {
            session.close();
        }

        return dto;
    }
}