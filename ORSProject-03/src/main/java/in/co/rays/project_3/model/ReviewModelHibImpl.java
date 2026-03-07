package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ReviewDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ReviewModelHibImpl implements ReviewModelInt {

    @Override
    public long add(ReviewDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        ReviewDTO duplicateReview = findByName(dto.getReviewCode());

        if (duplicateReview != null) {
            throw new DuplicateRecordException("Review Code already exists");
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

            throw new ApplicationException("Exception in Review Add " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(ReviewDTO dto) throws ApplicationException {

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

            throw new ApplicationException("Exception in Review Delete " + e.getMessage());

        } finally {
            session.close();
        }
    }

    @Override
    public void update(ReviewDTO dto) throws ApplicationException, DuplicateRecordException {

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

            throw new ApplicationException("Exception in Review Update " + e.getMessage());

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
            Criteria criteria = session.createCriteria(ReviewDTO.class);

            if (pageSize > 0) {

                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Review List");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(ReviewDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(ReviewDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {

            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ReviewDTO.class);

            if (dto.getReviewCode() != null && dto.getReviewCode().length() > 0) {
                criteria.add(Restrictions.like("reviewCode", dto.getReviewCode() + "%"));
            }

            if (dto.getUserId() != null && dto.getUserId() > 0) {
                criteria.add(Restrictions.eq("userId", dto.getUserId()));
            }

            if (dto.getRating() != null && dto.getRating() > 0) {
                criteria.add(Restrictions.eq("rating", dto.getRating()));
            }

            if (dto.getComment() != null && dto.getComment().length() > 0) {
                criteria.add(Restrictions.like("comment", dto.getComment() + "%"));
            }

            if (pageSize > 0) {

                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Review Search");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public ReviewDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        ReviewDTO dto = null;

        try {

            session = HibDataSource.getSession();
            dto = (ReviewDTO) session.get(ReviewDTO.class, pk);

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Review findByPK");

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public ReviewDTO findByName(String name) throws ApplicationException {

        Session session = null;
        ReviewDTO dto = null;

        try {

            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ReviewDTO.class);
            criteria.add(Restrictions.eq("reviewCode", name));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (ReviewDTO) list.get(0);
            }

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Review findByName");

        } finally {
            session.close();
        }

        return dto;
    }
}