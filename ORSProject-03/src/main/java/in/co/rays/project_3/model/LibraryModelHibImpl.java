package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.LibraryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class LibraryModelHibImpl implements LibraryModelInt {

    @Override
    public long add(LibraryDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        // FIX: Duplicate check add kiya — same bookName already exist karta hai toh exception
        LibraryDTO duplicate = findByBookName(dto.getBookName());
        if (duplicate != null) {
            throw new DuplicateRecordException("Book Name already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add Library: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(LibraryDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete Library: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(LibraryDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        // FIX: Update mein bhi duplicate check — same bookName kisi aur record mein na ho
        LibraryDTO duplicate = findByBookName(dto.getBookName());
        if (duplicate != null && duplicate.getId() != dto.getId()) {
            throw new DuplicateRecordException("Book Name already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update Library: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(LibraryDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list Library: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(LibraryDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(LibraryDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(LibraryDTO.class);

            // FIX: ilike use kiya — case-insensitive search
            if (dto.getBookName() != null && dto.getBookName().trim().length() > 0) {
                criteria.add(Restrictions.ilike("bookName", dto.getBookName().trim(), MatchMode.START));
            }
            if (dto.getAuthorName() != null && dto.getAuthorName().trim().length() > 0) {
                criteria.add(Restrictions.ilike("authorName", dto.getAuthorName().trim(), MatchMode.START));
            }
            if (dto.getIssueDate() != null && dto.getIssueDate().trim().length() > 0) {
                criteria.add(Restrictions.ilike("issueDate", dto.getIssueDate().trim(), MatchMode.START));
            }
            if (dto.getPrice() != null && dto.getPrice().trim().length() > 0) {
                criteria.add(Restrictions.ilike("price", dto.getPrice().trim(), MatchMode.START));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search Library: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public LibraryDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        LibraryDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (LibraryDTO) session.get(LibraryDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK Library: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public LibraryDTO findByBookName(String bookName) throws ApplicationException {

        Session session = null;
        LibraryDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(LibraryDTO.class);
            criteria.add(Restrictions.eq("bookName", bookName));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (LibraryDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByBookName: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}