package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BloodBankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class BloodBankModelHibImpl implements BloodBankModelInt {

    // ==================== ADD ====================
    @Override
    public long add(BloodBankDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        // Business Validation: Duplicate Contact Number check
        BloodBankDTO existingDTO = findByContactNumber(dto.getContactNumber());
        if (existingDTO != null) {
            throw new DuplicateRecordException("Contact Number already exists: " + dto.getContactNumber());
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add BloodBank: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }

        return dto.getId();
    }

    // ==================== DELETE ====================
    @Override
    public void delete(BloodBankDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete BloodBank: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    // ==================== UPDATE ====================
    @Override
    public void update(BloodBankDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        // Business Validation: Duplicate Contact Number check
        // Exclude current record (same ID) — apna hi number update karna valid hai
        BloodBankDTO existingDTO = findByContactNumber(dto.getContactNumber());
        if (existingDTO != null && existingDTO.getId() != dto.getId()) {
            throw new DuplicateRecordException("Contact Number already exists: " + dto.getContactNumber());
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update BloodBank: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    // ==================== LIST (all records) ====================
    @Override
    public List list() throws ApplicationException {
        return list(0, 0);
    }

    // ==================== LIST (with pagination) ====================
    @Override
    public List list(int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BloodBankDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list BloodBank: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }

        return list;
    }

    // ==================== SEARCH (without pagination) ====================
    @Override
    public List search(BloodBankDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    // ==================== SEARCH (with pagination) ====================
    @Override
    public List search(BloodBankDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BloodBankDTO.class);

            if (dto.getDonorName() != null && dto.getDonorName().length() > 0) {
                criteria.add(Restrictions.like("donorName", dto.getDonorName() + "%"));
            }
            if (dto.getBloodGroup() != null && dto.getBloodGroup().length() > 0) {
                criteria.add(Restrictions.like("bloodGroup", dto.getBloodGroup() + "%"));
            }
            if (dto.getContactNumber() != null && dto.getContactNumber().length() > 0) {
                criteria.add(Restrictions.like("contactNumber", dto.getContactNumber() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search BloodBank: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }

        return list;
    }

    // ==================== FIND BY PRIMARY KEY ====================
    @Override
    public BloodBankDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        BloodBankDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (BloodBankDTO) session.get(BloodBankDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK BloodBank: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }

        return dto;
    }

    // ==================== FIND BY CONTACT NUMBER ====================
    @Override
    public BloodBankDTO findByContactNumber(String contactNumber) throws ApplicationException {

        Session session = null;
        BloodBankDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BloodBankDTO.class);
            criteria.add(Restrictions.eq("contactNumber", contactNumber));
            List list = criteria.list();

            if (list.size() == 1) {
                dto = (BloodBankDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByContactNumber BloodBank: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }

        return dto;
    }

}