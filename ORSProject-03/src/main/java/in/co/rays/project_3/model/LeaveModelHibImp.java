package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.LeaveDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class LeaveModelHibImp implements LeaveModelInt {

    @Override
    public long add(LeaveDTO dto) throws DatabaseException, DuplicateRecordException {
        
        LeaveDTO existdto = findByLeaveCode(dto.getLeaveCode());
        if (existdto != null) {
            throw new DuplicateRecordException("Leave code already exists");
        }

        Session session = HibDataSource.getSession();
        Transaction tx = null;
        long pk = 0;

        try {
            tx = session.beginTransaction();
            pk = (Long) session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in adding Leave: " + e.getMessage());
        } finally {
            session.close();
        }

        return pk;
    }

    @Override
    public void update(LeaveDTO dto) throws DatabaseException, DuplicateRecordException {
        
        LeaveDTO existdto = findByLeaveCode(dto.getLeaveCode());
        if (existdto != null && existdto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Leave code already exists");
        }

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new DatabaseException("Exception in updating Leave: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(LeaveDTO dto) throws DatabaseException {

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new DatabaseException("Exception in deleting Leave: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(LeaveDTO.class);
            if (pageSize > 0) {
                int firstResult = (pageNo - 1) * pageSize;
                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (HibernateException e) {
            throw new DatabaseException("Exception in listing Leave: " + e.getMessage());
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public List search(LeaveDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = HibDataSource.getSession();
        List list = null;

        try {
            Criteria criteria = session.createCriteria(LeaveDTO.class);

            if (dto != null) {

                if (dto.getLeaveCode() != null && !dto.getLeaveCode().isEmpty()) {
                    criteria.add(Restrictions.like("leaveCode", dto.getLeaveCode() + "%"));
                }
                if (dto.getEmployeeName() != null && !dto.getEmployeeName().isEmpty()) {
                    criteria.add(Restrictions.like("employeeName", dto.getEmployeeName() + "%"));
                }
                if (dto.getLeaveStartDate() != null) {
                    criteria.add(Restrictions.eq("leaveStartDate", dto.getLeaveStartDate()));
                }
                if (dto.getLeaveStatus() != null && !dto.getLeaveStatus().isEmpty()) {
                    criteria.add(Restrictions.eq("leaveStatus", dto.getLeaveStatus()));
                }
            }

            if (pageSize > 0) {
                int firstResult = (pageNo - 1) * pageSize;
                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in searching Leave: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public LeaveDTO findByPK(long pk) throws DatabaseException {

        Session session = HibDataSource.getSession();
        LeaveDTO dto = null;

        try {
            dto = (LeaveDTO) session.get(LeaveDTO.class, pk);
        } catch (HibernateException e) {
            throw new DatabaseException("Exception in finding Leave by PK: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public LeaveDTO findByLeaveCode(String leaveCode) throws DatabaseException {

        Session session = HibDataSource.getSession();
        LeaveDTO dto = null;

        try {
            Criteria criteria = session.createCriteria(LeaveDTO.class);
            criteria.add(Restrictions.eq("leaveCode", leaveCode));
            List list = criteria.list();
            if (!list.isEmpty()) dto = (LeaveDTO) list.get(0);
        } catch (HibernateException e) {
            throw new DatabaseException("Exception in finding Leave by code: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}