package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PenaltyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PenaltyModelHibImpl implements PenaltyModelInt {

    @Override
    public long add(PenaltyDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        PenaltyDTO duplicate = findByPenaltyCode(dto.getPenaltyCode());

        if (duplicate != null) {
            throw new DuplicateRecordException("Penalty Code already exists");
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

            throw new ApplicationException("Exception in add Penalty " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(PenaltyDTO dto) throws ApplicationException {

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

            throw new ApplicationException("Exception in delete Penalty");

        } finally {
            session.close();
        }
    }

    @Override
    public void update(PenaltyDTO dto) throws ApplicationException, DuplicateRecordException {

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

            throw new ApplicationException("Exception in update Penalty");

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
            Criteria criteria = session.createCriteria(PenaltyDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in list Penalty");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(PenaltyDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(PenaltyDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PenaltyDTO.class);

            if (dto.getPenaltyCode() != null && dto.getPenaltyCode().length() > 0) {
                criteria.add(Restrictions.like("penaltyCode", dto.getPenaltyCode() + "%"));
            }

            if (dto.getPenaltyReason() != null && dto.getPenaltyReason().length() > 0) {
                criteria.add(Restrictions.like("penaltyReason", dto.getPenaltyReason() + "%"));
            }

            if (dto.getPenaltyStatus() != null && dto.getPenaltyStatus().length() > 0) {
                criteria.add(Restrictions.like("penaltyStatus", dto.getPenaltyStatus() + "%"));
            }

            if (dto.getPenaltyAmount() != null && dto.getPenaltyAmount().compareTo(java.math.BigDecimal.ZERO) > 0) {
                criteria.add(Restrictions.eq("penaltyAmount", dto.getPenaltyAmount()));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in search Penalty");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public PenaltyDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        PenaltyDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (PenaltyDTO) session.get(PenaltyDTO.class, pk);

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByPK Penalty");

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public PenaltyDTO findByPenaltyCode(String penaltyCode) throws ApplicationException {

        Session session = null;
        PenaltyDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PenaltyDTO.class);

            criteria.add(Restrictions.eq("penaltyCode", penaltyCode));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (PenaltyDTO) list.get(0);
            }

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByPenaltyCode");

        } finally {
            session.close();
        }

        return dto;
    }
}