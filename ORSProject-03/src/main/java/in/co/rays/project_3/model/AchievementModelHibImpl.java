package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AchievementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class AchievementModelHibImpl implements AchievementModelInt {

    @Override
    public long add(AchievementDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        AchievementDTO duplicate = findByAchievementCode(dto.getAchievementCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Achievement Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add Achievement: " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(AchievementDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete Achievement: " + e.getMessage());

        } finally {
            session.close();
        }
    }

    @Override
    public void update(AchievementDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update Achievement: " + e.getMessage());

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
            Criteria criteria = session.createCriteria(AchievementDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {
            throw new ApplicationException("Exception in list Achievement: " + e.getMessage());

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(AchievementDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(AchievementDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AchievementDTO.class);

            if (dto.getAchievementCode() != null && dto.getAchievementCode().length() > 0) {
                criteria.add(Restrictions.like("achievementCode", dto.getAchievementCode() + "%"));
            }
            if (dto.getAchievementName() != null && dto.getAchievementName().length() > 0) {
                criteria.add(Restrictions.like("achievementName", dto.getAchievementName() + "%"));
            }
            if (dto.getEarnedBy() != null && dto.getEarnedBy().length() > 0) {
                criteria.add(Restrictions.like("earnedBy", dto.getEarnedBy() + "%"));
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
            throw new ApplicationException("Exception in search Achievement: " + e.getMessage());

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public AchievementDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        AchievementDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (AchievementDTO) session.get(AchievementDTO.class, pk);

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK Achievement: " + e.getMessage());

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public AchievementDTO findByAchievementCode(String achievementCode) throws ApplicationException {

        Session session = null;
        AchievementDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AchievementDTO.class);
            criteria.add(Restrictions.eq("achievementCode", achievementCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (AchievementDTO) list.get(0);
            }

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByAchievementCode: " + e.getMessage());

        } finally {
            session.close();
        }

        return dto;
    }
}