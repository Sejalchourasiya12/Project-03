package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AIRecommendationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * AIRecommendationModelHibImpl - Hibernate Implementation for AI Recommendation
 *
 * @author Sejal Chourasiya
 */
public class AIRecommendationModelHibImpl implements AIRecommendationModelInt {

    @Override
    public long add(AIRecommendationDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        AIRecommendationDTO duplicate = findByRecommendationCode(dto.getRecommendationCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Recommendation Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add AIRecommendation: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(AIRecommendationDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete AIRecommendation: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(AIRecommendationDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        AIRecommendationDTO duplicate = findByRecommendationCode(dto.getRecommendationCode());
        if (duplicate != null && !duplicate.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("Recommendation Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update AIRecommendation: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(AIRecommendationDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list AIRecommendation: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(AIRecommendationDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(AIRecommendationDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AIRecommendationDTO.class);

            if (dto.getRecommendationCode() != null && dto.getRecommendationCode().trim().length() > 0) {
                criteria.add(Restrictions.like("recommendationCode", dto.getRecommendationCode().trim() + "%"));
            }
            if (dto.getUserName() != null && dto.getUserName().trim().length() > 0) {
                criteria.add(Restrictions.like("userName", dto.getUserName().trim() + "%"));
            }
            if (dto.getRecommendationType() != null && dto.getRecommendationType().trim().length() > 0) {
                criteria.add(Restrictions.like("recommendationType", dto.getRecommendationType().trim() + "%"));
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
            throw new ApplicationException("Exception in search AIRecommendation: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public AIRecommendationDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        AIRecommendationDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (AIRecommendationDTO) session.get(AIRecommendationDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK AIRecommendation: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public AIRecommendationDTO findByRecommendationCode(String recommendationCode) throws ApplicationException {

        Session session = null;
        AIRecommendationDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AIRecommendationDTO.class);
            criteria.add(Restrictions.eq("recommendationCode", recommendationCode));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (AIRecommendationDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByRecommendationCode: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}