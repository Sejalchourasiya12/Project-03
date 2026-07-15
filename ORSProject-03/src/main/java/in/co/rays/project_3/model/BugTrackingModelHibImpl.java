package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BugTrackingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class BugTrackingModelHibImpl implements BugTrackingModelInt {

    @Override
    public long add(BugTrackingDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add BugTracking: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(BugTrackingDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete BugTracking: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(BugTrackingDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update BugTracking: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(BugTrackingDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list BugTracking: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(BugTrackingDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(BugTrackingDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BugTrackingDTO.class);

            if (dto.getProjectName() != null && dto.getProjectName().length() > 0) {
                criteria.add(Restrictions.like("projectName", dto.getProjectName() + "%"));
            }
            if (dto.getSeverity() != null && dto.getSeverity().length() > 0) {
                criteria.add(Restrictions.like("severity", dto.getSeverity() + "%"));
            }
            if (dto.getAssignedTo() != null && dto.getAssignedTo().length() > 0) {
                criteria.add(Restrictions.like("assignedTo", dto.getAssignedTo() + "%"));
            }
            if (dto.getStatus() != null && dto.getStatus().length() > 0) {
                criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search BugTracking: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public BugTrackingDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        BugTrackingDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (BugTrackingDTO) session.get(BugTrackingDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK BugTracking: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public BugTrackingDTO findByProjectName(String projectName) throws ApplicationException {

        Session session = null;
        BugTrackingDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BugTrackingDTO.class);
            criteria.add(Restrictions.eq("projectName", projectName));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (BugTrackingDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByProjectName: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}