package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PolicyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * PolicyModelHibImpl - Hibernate Implementation for Policy
 * 
 * @author Sejal Chourasiya
 */
public class PolicyModelHibImpl implements PolicyModelInt {

    @Override
    public long add(PolicyDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        PolicyDTO duplicate = findByPolicyCode(dto.getPolicyCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Policy Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add Policy: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(PolicyDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete Policy: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(PolicyDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        PolicyDTO duplicate = findByPolicyCode(dto.getPolicyCode());
        if (duplicate != null && !duplicate.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("Policy Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update Policy: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(PolicyDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list Policy: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(PolicyDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(PolicyDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PolicyDTO.class);

            if (dto.getPolicyCode() != null && dto.getPolicyCode().trim().length() > 0) {
                criteria.add(Restrictions.like("policyCode", dto.getPolicyCode().trim() + "%"));
            }
            if (dto.getPolicyName() != null && dto.getPolicyName().trim().length() > 0) {
                criteria.add(Restrictions.like("policyName", dto.getPolicyName().trim() + "%"));
            }
            if (dto.getPremiumAmount() != null && dto.getPremiumAmount().trim().length() > 0) {
                criteria.add(Restrictions.like("premiumAmount", dto.getPremiumAmount().trim() + "%"));
            }
            if (dto.getPolicyStatus() != null && dto.getPolicyStatus().trim().length() > 0) {
                criteria.add(Restrictions.like("policyStatus", dto.getPolicyStatus().trim() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search Policy: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public PolicyDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        PolicyDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (PolicyDTO) session.get(PolicyDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK Policy: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public PolicyDTO findByPolicyCode(String policyCode) throws ApplicationException {

        Session session = null;
        PolicyDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PolicyDTO.class);
            criteria.add(Restrictions.eq("policyCode", policyCode));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (PolicyDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPolicyCode: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}