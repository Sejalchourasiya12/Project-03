package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BookIssueDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class BookIssueModelHibImpl implements BookIssueModelInt {

    @Override
    public long add(BookIssueDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        BookIssueDTO duplicate = findByIssueCode(dto.getIssueCode());

        if (duplicate != null) {
            throw new DuplicateRecordException("Issue Code already exists");
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

            throw new ApplicationException("Exception in add BookIssue " + e.getMessage());

        } finally {
            session.close();
        }
        

        return dto.getId();
    }

    @Override
    public void delete(BookIssueDTO dto) throws ApplicationException {

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

            throw new ApplicationException("Exception in delete BookIssue");

        } finally {
            session.close();
        }
    }

    @Override
    public void update(BookIssueDTO dto) throws ApplicationException, DuplicateRecordException {

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

            throw new ApplicationException("Exception in update BookIssue");

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
            Criteria criteria = session.createCriteria(BookIssueDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in list BookIssue");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(BookIssueDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(BookIssueDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BookIssueDTO.class);

            if (dto.getIssueCode() != null && dto.getIssueCode().length() > 0) {
                criteria.add(Restrictions.like("issueCode", dto.getIssueCode() + "%"));
            }

            if (dto.getBookName() != null && dto.getBookName().length() > 0) {
                criteria.add(Restrictions.like("bookName", dto.getBookName() + "%"));
            }

            if (dto.getIssueStatus() != null && dto.getIssueStatus().length() > 0) {
                criteria.add(Restrictions.like("issueStatus", dto.getIssueStatus() + "%"));
            }

            if (dto.getIssueDate() != null) {
                criteria.add(Restrictions.eq("issueDate", dto.getIssueDate()));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in search BookIssue");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public BookIssueDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        BookIssueDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (BookIssueDTO) session.get(BookIssueDTO.class, pk);

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByPK BookIssue");

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public BookIssueDTO findByIssueCode(String issueCode) throws ApplicationException {

        Session session = null;
        BookIssueDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BookIssueDTO.class);

            criteria.add(Restrictions.eq("issueCode", issueCode));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (BookIssueDTO) list.get(0);
            }

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByIssueCode");

        } finally {
            session.close();
        }

        return dto;
    }
}