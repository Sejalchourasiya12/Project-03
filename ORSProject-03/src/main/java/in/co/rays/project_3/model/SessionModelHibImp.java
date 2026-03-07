package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SessionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SessionModelHibImp implements SessionModelInt {

    @Override
    public long add(SessionDTO dto) throws DatabaseException, DuplicateRecordException {
    	
    	SessionDTO existdto = findByToken(dto.getSessionToken());
    	if (existdto != null) {
			throw new DuplicateRecordException("token already exists");
		}

        Session session = HibDataSource.getSession();
        Transaction tx = null;
        long pk = 0;

        try {
            tx = session.beginTransaction();
            pk = (Long) session.save(dto); // save will return generated ID
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
            	tx.rollback();
            }
            throw new DatabaseException("Exception in adding Session: " + e.getMessage());
        } finally {
            session.close();
        }

        return pk;
    }

    @Override
    public void update(SessionDTO dto) throws DatabaseException, DuplicateRecordException {
    	
    	SessionDTO existdto = findByToken(dto.getSessionToken());
    	if (existdto != null && existdto.getId() != dto.getId()) {
			throw new DuplicateRecordException("token already exists");
		}

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(dto); // or saveOrUpdate
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new DatabaseException("Exception in updating Session: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(SessionDTO dto) throws DatabaseException {

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new DatabaseException("Exception in deleting Session: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public List list() throws DatabaseException {
        return list(0, 0);
    }

    @Override
    public List list(int pageNo, int pageSize) throws DatabaseException {

        Session session = HibDataSource.getSession();
        List list = null;

        try {
            Criteria criteria = session.createCriteria(SessionDTO.class);
            if (pageSize > 0) {
                int firstResult = (pageNo - 1) * pageSize;
                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (HibernateException e) {
            throw new DatabaseException("Exception in listing Session: " + e.getMessage());
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public List search(SessionDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = HibDataSource.getSession();
        List list = null;

        try {
            Criteria criteria = session.createCriteria(SessionDTO.class);

            if (dto != null) {

                if (dto.getSessionToken() != null && !dto.getSessionToken().isEmpty()) {
                    criteria.add(Restrictions.like("sessionToken", dto.getSessionToken() + "%"));
                }
                if (dto.getUserName() != null && !dto.getUserName().isEmpty()) {
                    criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
                }
                if (dto.getLoginTime() != null) {
                    criteria.add(Restrictions.eq("loginTime", dto.getLoginTime()));
                }
                if (dto.getSessionStatus() != null && !dto.getSessionStatus().isEmpty()) {
                    criteria.add(Restrictions.eq("sessionStatus", dto.getSessionStatus()));
                }
            }

            if (pageSize > 0) {
                int firstResult = (pageNo - 1) * pageSize;
                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in searching Session: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public SessionDTO findByPK(long pk) throws DatabaseException {

        Session session = HibDataSource.getSession();
        SessionDTO dto = null;

        try {
            dto = (SessionDTO) session.get(SessionDTO.class, pk);
        } catch (HibernateException e) {
            throw new DatabaseException("Exception in finding Session by PK: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public SessionDTO findByToken(String token) throws DatabaseException {

        Session session = HibDataSource.getSession();
        SessionDTO dto = null;

        try {
            Criteria criteria = session.createCriteria(SessionDTO.class);
            criteria.add(Restrictions.eq("sessionToken", token));
            List list = criteria.list();
            if (!list.isEmpty()) dto = (SessionDTO) list.get(0);
        } catch (HibernateException e) {
            throw new DatabaseException("Exception in finding Session by token: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}