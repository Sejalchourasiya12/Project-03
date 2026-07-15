package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ConsumerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ConsumerModelHibImpl implements ConsumerModelInt {

    @Override
    public long add(ConsumerDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        ConsumerDTO duplicate = findByConsumerCode(dto.getConsumerCode());

        if (duplicate != null) {
            throw new DuplicateRecordException("Consumer Code already exists");
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

            throw new ApplicationException("Exception in add Consumer " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(ConsumerDTO dto) throws ApplicationException {

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

            throw new ApplicationException("Exception in delete Consumer");

        } finally {
            session.close();
        }
    }

    @Override
    public void update(ConsumerDTO dto) throws ApplicationException, DuplicateRecordException {

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

            throw new ApplicationException("Exception in update Consumer");

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
            Criteria criteria = session.createCriteria(ConsumerDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in list Consumer");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(ConsumerDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(ConsumerDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ConsumerDTO.class);

            if (dto.getConsumerCode() != null && dto.getConsumerCode().length() > 0) {
                criteria.add(Restrictions.like("consumerCode", dto.getConsumerCode() + "%"));
            }

            if (dto.getConsumerGroup() != null && dto.getConsumerGroup().length() > 0) {
                criteria.add(Restrictions.like("consumerGroup", dto.getConsumerGroup() + "%"));
            }

            if (dto.getTopicName() != null && dto.getTopicName().length() > 0) {
                criteria.add(Restrictions.like("topicName", dto.getTopicName() + "%"));
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

            throw new ApplicationException("Exception in search Consumer");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public ConsumerDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        ConsumerDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (ConsumerDTO) session.get(ConsumerDTO.class, pk);

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByPK Consumer");

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public ConsumerDTO findByConsumerCode(String consumerCode) throws ApplicationException {

        Session session = null;
        ConsumerDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ConsumerDTO.class);

            criteria.add(Restrictions.eq("consumerCode", consumerCode));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (ConsumerDTO) list.get(0);
            }

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByConsumerCode");

        } finally {
            session.close();
        }

        return dto;
    }
}