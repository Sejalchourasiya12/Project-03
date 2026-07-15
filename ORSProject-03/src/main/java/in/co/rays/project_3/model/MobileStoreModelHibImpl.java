package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.MobileStoreDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * MobileStoreModelHibImpl - Hibernate Implementation for Mobile Store
 *
 * @author Sejal Chourasiya
 */
public class MobileStoreModelHibImpl implements MobileStoreModelInt {

    @Override
    public long add(MobileStoreDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        MobileStoreDTO duplicate = findByModelName(dto.getModelName());
        if (duplicate != null) {
            throw new DuplicateRecordException("Model Name already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add MobileStore: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(MobileStoreDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete MobileStore: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(MobileStoreDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        MobileStoreDTO duplicate = findByModelName(dto.getModelName());
        if (duplicate != null && !duplicate.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("Model Name already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update MobileStore: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(MobileStoreDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list MobileStore: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(MobileStoreDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(MobileStoreDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(MobileStoreDTO.class);

            if (dto.getBrandName() != null && dto.getBrandName().trim().length() > 0) {
                criteria.add(Restrictions.like("brandName", dto.getBrandName().trim() + "%"));
            }
            if (dto.getModelName() != null && dto.getModelName().trim().length() > 0) {
                criteria.add(Restrictions.like("modelName", dto.getModelName().trim() + "%"));
            }
            if (dto.getRamSize() != null && dto.getRamSize().trim().length() > 0) {
                criteria.add(Restrictions.like("ramSize", dto.getRamSize().trim() + "%"));
            }
            if (dto.getPrice() != null && dto.getPrice().trim().length() > 0) {
                criteria.add(Restrictions.like("price", dto.getPrice().trim() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search MobileStore: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public MobileStoreDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        MobileStoreDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (MobileStoreDTO) session.get(MobileStoreDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK MobileStore: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public MobileStoreDTO findByModelName(String modelName) throws ApplicationException {

        Session session = null;
        MobileStoreDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(MobileStoreDTO.class);
            criteria.add(Restrictions.eq("modelName", modelName));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (MobileStoreDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByModelName: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}