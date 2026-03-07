package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.FoodDeliveryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class FoodDeliveryModelHibImpl implements FoodDeliveryModelInt {

    public long add(FoodDeliveryDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        FoodDeliveryDTO existDTO = findByOrderNumber(dto.getOrderNumber());
        if (existDTO != null) {
            throw new DuplicateRecordException("Order Number already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in FoodDelivery Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    public void delete(FoodDeliveryDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in FoodDelivery Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void update(FoodDeliveryDTO dto)
            throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in FoodDelivery Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public List list() throws ApplicationException {
        return list(0, 0);
    }

    public List list(int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(FoodDeliveryDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in FoodDelivery List");
        } finally {
            session.close();
        }

        return list;
    }

    public List search(FoodDeliveryDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(FoodDeliveryDTO dto, int pageNo, int pageSize)
            throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(FoodDeliveryDTO.class);

            if (dto.getId() != null && dto.getId() > 0) {
                criteria.add(Restrictions.eq("id", dto.getId()));
            }

            if (dto.getOrderNumber() != null && dto.getOrderNumber().length() > 0) {
                criteria.add(Restrictions.like("orderNumber", dto.getOrderNumber() + "%"));
            }

            if (dto.getRestaurantName() != null && dto.getRestaurantName().length() > 0) {
                criteria.add(Restrictions.like("restaurantName", dto.getRestaurantName() + "%"));
            }

            if (dto.getDeliveryPartner() != null && dto.getDeliveryPartner().length() > 0) {
                criteria.add(Restrictions.like("deliveryPartner", dto.getDeliveryPartner() + "%"));
            }

            if (dto.getDeliveryStatus() != null && dto.getDeliveryStatus().length() > 0) {
                criteria.add(Restrictions.like("deliveryStatus", dto.getDeliveryStatus() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in FoodDelivery Search");
        } finally {
            session.close();
        }

        return list;
    }

    public FoodDeliveryDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        FoodDeliveryDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (FoodDeliveryDTO) session.get(FoodDeliveryDTO.class, pk);

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in FoodDelivery FindByPK");
        } finally {
            session.close();
        }

        return dto;
    }

    public FoodDeliveryDTO findByTitle(String name) throws ApplicationException {
        return findByOrderNumber(name);
    }

    public FoodDeliveryDTO findByOrderNumber(String orderNumber)
            throws ApplicationException {

        Session session = null;
        FoodDeliveryDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(FoodDeliveryDTO.class);
            criteria.add(Restrictions.eq("orderNumber", orderNumber));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (FoodDeliveryDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in FoodDelivery FindByOrderNumber");
        } finally {
            session.close();
        }

        return dto;
    }
}