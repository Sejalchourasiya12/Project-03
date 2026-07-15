package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.GenderDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class GenderModelHibImpl implements GenderModelInt {

    @Override
    public long add(GenderDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        GenderDTO duplicate = findByGenderCode(dto.getGenderCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Gender Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add Gender: " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(GenderDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete Gender: " + e.getMessage());

        } finally {
            session.close();
        }
    }

    @Override
    public void update(GenderDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update Gender: " + e.getMessage());

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
            Criteria criteria = session.createCriteria(GenderDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {
            throw new ApplicationException("Exception in list Gender: " + e.getMessage());

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(GenderDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(GenderDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(GenderDTO.class);

            if (dto.getGenderCode() != null && dto.getGenderCode().length() > 0) {
                criteria.add(Restrictions.like("genderCode", dto.getGenderCode() + "%"));
            }
            if (dto.getGenderType() != null && dto.getGenderType().length() > 0) {
                criteria.add(Restrictions.like("genderType", dto.getGenderType() + "%"));
            }
            if (dto.getDescription() != null && dto.getDescription().length() > 0) {
                criteria.add(Restrictions.like("description", dto.getDescription() + "%"));
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
            throw new ApplicationException("Exception in search Gender: " + e.getMessage());

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public GenderDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        GenderDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (GenderDTO) session.get(GenderDTO.class, pk);

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK Gender: " + e.getMessage());

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public GenderDTO findByGenderCode(String genderCode) throws ApplicationException {

        Session session = null;
        GenderDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(GenderDTO.class);
            criteria.add(Restrictions.eq("genderCode", genderCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (GenderDTO) list.get(0);
            }

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByGenderCode: " + e.getMessage());

        } finally {
            session.close();
        }

        return dto;
    }
}