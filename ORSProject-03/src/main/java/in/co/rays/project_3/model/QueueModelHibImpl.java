package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.QueueDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class QueueModelHibImpl implements QueueModelInt {

    @Override
    public long add(QueueDTO dto)
            throws ApplicationException, DuplicateRecordException {

        Session session = HibDataSource.getSession();

        QueueDTO exist = findByCode(dto.getQueueCode());
        if (exist != null) {
            throw new DuplicateRecordException("Queue Code already exists");
        }

        Transaction tx = session.beginTransaction();
        Long pk = (Long) session.save(dto);
        tx.commit();
        session.close();

        return pk;
    }

    @Override
    public void update(QueueDTO dto)
            throws ApplicationException, DuplicateRecordException {

        Session session = HibDataSource.getSession();

        QueueDTO exist = findByCode(dto.getQueueCode());
        if (exist != null && !exist.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("Queue Code already exists");
        }

        Transaction tx = session.beginTransaction();
        session.update(dto);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(QueueDTO dto)
            throws ApplicationException {

        Session session = HibDataSource.getSession();
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Override
    public QueueDTO findByPK(long pk)
            throws ApplicationException {

        Session session = HibDataSource.getSession();
        QueueDTO dto = (QueueDTO) session.get(QueueDTO.class, pk);
        session.close();
        return dto;
    }

    @Override
    public QueueDTO findByCode(String code)
            throws ApplicationException {

        Session session = HibDataSource.getSession();
        Query q = session.createQuery(
                "from QueueDTO where queueCode=?");
        q.setParameter(0, code);

        List list = q.list();
        session.close();

        if (list.size() > 0) {
            return (QueueDTO) list.get(0);
        }
        return null;
    }

    @Override
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        Session session = HibDataSource.getSession();
        Query q = session.createQuery("from QueueDTO");

        if (pageSize > 0) {
            q.setFirstResult((pageNo - 1) * pageSize);
            q.setMaxResults(pageSize);
        }

        List list = q.list();
        session.close();
        return list;
    }

    @Override
    public List search(QueueDTO dto, int pageNo, int pageSize)
            throws ApplicationException {

        Session session = HibDataSource.getSession();
        Criteria criteria = session.createCriteria(QueueDTO.class);

        if (dto != null) {

            if (dto.getQueueCode() != null &&
                dto.getQueueCode().length() > 0) {
                criteria.add(Restrictions.like(
                        "queueCode",
                        dto.getQueueCode() + "%"));
            }

            if (dto.getQueueName() != null &&
                dto.getQueueName().length() > 0) {
                criteria.add(Restrictions.like(
                        "queueName",
                        dto.getQueueName() + "%"));
            }

            if (dto.getQueueStatus() != null &&
                dto.getQueueStatus().length() > 0) {
                criteria.add(Restrictions.eq(
                        "queueStatus",
                        dto.getQueueStatus()));
            }
        }

        if (pageSize > 0) {
            criteria.setFirstResult((pageNo - 1) * pageSize);
            criteria.setMaxResults(pageSize);
        }

        List list = criteria.list();
        session.close();
        return list;
    }
}
