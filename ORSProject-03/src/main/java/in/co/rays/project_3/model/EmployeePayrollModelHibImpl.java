package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.EmployeePayrollDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * EmployeePayroll Model Hibernate Implementation
 *
 * @author Sejal Chourasiya
 */
public class EmployeePayrollModelHibImpl implements EmployeePayrollModelInt {

    @Override
    public long add(EmployeePayrollDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        EmployeePayrollDTO duplicate = findByEmployeeName(dto.getEmployeeName());
        if (duplicate != null) {
        	throw new DuplicateRecordException(
        		    "Employee Name already exists: " + dto.getEmployeeName());
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add EmployeePayroll: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(EmployeePayrollDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete EmployeePayroll: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(EmployeePayrollDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        EmployeePayrollDTO duplicate = findByEmployeeName(dto.getEmployeeName());
        if (duplicate != null && duplicate.getId() != dto.getId()) {
            throw new DuplicateRecordException("Employee Name already exists :" + dto.getEmployeeName());
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update EmployeePayroll: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(EmployeePayrollDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list EmployeePayroll: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(EmployeePayrollDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(EmployeePayrollDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(EmployeePayrollDTO.class);

            if (dto.getEmployeeName() != null && dto.getEmployeeName().trim().length() > 0) {
                criteria.add(Restrictions.like("employeeName", dto.getEmployeeName().trim() + "%"));
            }
            if (dto.getDepartment() != null && dto.getDepartment().trim().length() > 0) {
                criteria.add(Restrictions.like("department", dto.getDepartment().trim() + "%"));
            }
            if (dto.getSalary() != null && dto.getSalary().trim().length() > 0) {
                criteria.add(Restrictions.like("salary", dto.getSalary().trim() + "%"));
            }
            if (dto.getBonus() != null && dto.getBonus().trim().length() > 0) {
                criteria.add(Restrictions.like("bonus", dto.getBonus().trim() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search EmployeePayroll: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public EmployeePayrollDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        EmployeePayrollDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (EmployeePayrollDTO) session.get(EmployeePayrollDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK EmployeePayroll: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public EmployeePayrollDTO findByEmployeeName(String employeeName) throws ApplicationException {

        Session session = null;
        EmployeePayrollDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(EmployeePayrollDTO.class);
            criteria.add(Restrictions.eq("employeeName", employeeName));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (EmployeePayrollDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByEmployeeName: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}