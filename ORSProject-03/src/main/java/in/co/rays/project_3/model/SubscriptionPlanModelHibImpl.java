package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SubscriptionPlanDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SubscriptionPlanModelHibImpl implements SubscriptionPlanModelInt {

    @Override
    public long add(SubscriptionPlanDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        SubscriptionPlanDTO duplicate = findByPlanName(dto.getPlanName());

        if (duplicate != null) {
            throw new DuplicateRecordException("Plan Name already exists");
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

            throw new ApplicationException("Exception in add SubscriptionPlan " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(SubscriptionPlanDTO dto) throws ApplicationException {

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

            throw new ApplicationException("Exception in delete SubscriptionPlan");

        } finally {
            session.close();
        }
    }

    @Override
    public void update(SubscriptionPlanDTO dto) throws ApplicationException, DuplicateRecordException {

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

            throw new ApplicationException("Exception in update SubscriptionPlan");

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
            Criteria criteria = session.createCriteria(SubscriptionPlanDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in list SubscriptionPlan");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(SubscriptionPlanDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(SubscriptionPlanDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SubscriptionPlanDTO.class);

            if (dto.getPlanName() != null && dto.getPlanName().length() > 0) {
                criteria.add(Restrictions.like("planName", dto.getPlanName() + "%"));
            }

            if (dto.getPrice() != null && dto.getPrice() > 0) {
                criteria.add(Restrictions.eq("price", dto.getPrice()));
            }

            if (dto.getValidityDays() != null && dto.getValidityDays() > 0) {
                criteria.add(Restrictions.eq("validityDays", dto.getValidityDays()));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in search SubscriptionPlan");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public SubscriptionPlanDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        SubscriptionPlanDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (SubscriptionPlanDTO) session.get(SubscriptionPlanDTO.class, pk);

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByPK SubscriptionPlan");

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public SubscriptionPlanDTO findByPlanName(String planName) throws ApplicationException {

        Session session = null;
        SubscriptionPlanDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SubscriptionPlanDTO.class);

            criteria.add(Restrictions.eq("planName", planName));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (SubscriptionPlanDTO) list.get(0);
            }

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByPlanName");

        } finally {
            session.close();
        }

        return dto;
    }
}
