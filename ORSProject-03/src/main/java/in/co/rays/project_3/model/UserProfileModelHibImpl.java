package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.UserProfileDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class UserProfileModelHibImpl implements UserProfileModelInt {

    @Override
    public long add(UserProfileDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        UserProfileDTO duplicate = findByProfileCode(dto.getProfileCode());

        if (duplicate != null) {
            throw new DuplicateRecordException("Profile Code already exists");
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

            throw new ApplicationException("Exception in add UserProfile " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(UserProfileDTO dto) throws ApplicationException {

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

            throw new ApplicationException("Exception in delete UserProfile");

        } finally {
            session.close();
        }
    }

    @Override
    public void update(UserProfileDTO dto) throws ApplicationException, DuplicateRecordException {

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

            throw new ApplicationException("Exception in update UserProfile");

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
            Criteria criteria = session.createCriteria(UserProfileDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in list UserProfile");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(UserProfileDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(UserProfileDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(UserProfileDTO.class);

            if (dto.getProfileCode() != null && dto.getProfileCode().length() > 0) {
                criteria.add(Restrictions.like("profileCode", dto.getProfileCode() + "%"));
            }

            if (dto.getUserName() != null && dto.getUserName().length() > 0) {
                criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
            }

            if (dto.getMobileNumber() != null && dto.getMobileNumber().length() > 0) {
                criteria.add(Restrictions.like("mobileNumber", dto.getMobileNumber() + "%"));
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

            throw new ApplicationException("Exception in search UserProfile");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public UserProfileDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        UserProfileDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (UserProfileDTO) session.get(UserProfileDTO.class, pk);

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByPK UserProfile");

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public UserProfileDTO findByProfileCode(String profileCode) throws ApplicationException {

        Session session = null;
        UserProfileDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(UserProfileDTO.class);

            criteria.add(Restrictions.eq("profileCode", profileCode));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (UserProfileDTO) list.get(0);
            }

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByProfileCode");

        } finally {
            session.close();
        }

        return dto;
    }
}