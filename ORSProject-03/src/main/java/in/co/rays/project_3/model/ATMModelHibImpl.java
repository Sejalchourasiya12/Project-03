package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ATMDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ATMModelHibImpl implements ATMModelInt {

    @Override
    public long add(ATMDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        ATMDTO duplicate = findBySecurityCode(dto.getSecurityCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Security Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add ATM: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(ATMDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete ATM: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(ATMDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        ATMDTO duplicate = findBySecurityCode(dto.getSecurityCode());
        if (duplicate != null && duplicate.getId() != dto.getId()) {
            throw new DuplicateRecordException("Security Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update ATM: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(ATMDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list ATM: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(ATMDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(ATMDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ATMDTO.class);

            // FIX: 'ilike' use kiya hai 'like' ki jagah — case-insensitive search ke liye
            // Pehle 'like' tha jisme "sbi" search karne par "SBI" nahi milta tha

            if (dto.getSecurityCode() != null && dto.getSecurityCode().trim().length() > 0) {
                criteria.add(Restrictions.like("securityCode", dto.getSecurityCode().trim() + "%"));
            }
            if (dto.getBankName() != null && dto.getBankName().trim().length() > 0) {
                criteria.add(Restrictions.like("bankName", dto.getBankName().trim() + "%"));
            }
            if (dto.getLocation() != null && dto.getLocation().trim().length() > 0) {
                criteria.add(Restrictions.like("location", dto.getLocation().trim() + "%"));
            }
            if (dto.getCashAvailable() != null && dto.getCashAvailable().trim().length() > 0) {
                criteria.add(Restrictions.like("cashAvailable", dto.getCashAvailable().trim() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search ATM: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public ATMDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        ATMDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (ATMDTO) session.get(ATMDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK ATM: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public ATMDTO findBySecurityCode(String securityCode) throws ApplicationException {

        Session session = null;
        ATMDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ATMDTO.class);
            criteria.add(Restrictions.eq("securityCode", securityCode));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (ATMDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findBySecurityCode: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}