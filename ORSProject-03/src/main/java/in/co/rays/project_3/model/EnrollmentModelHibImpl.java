package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.EnrollmentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class EnrollmentModelHibImpl implements EnrollmentModelInt {

    @Override
    public long add(EnrollmentDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        EnrollmentDTO duplicate = findByEnrollmentCode(dto.getEnrollmentCode());

        if (duplicate != null) {
            throw new DuplicateRecordException("Enrollment Code already exists");
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

            throw new ApplicationException("Exception in add Enrollment " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(EnrollmentDTO dto) throws ApplicationException {

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

            throw new ApplicationException("Exception in delete Enrollment");

        } finally {
            session.close();
        }
    }

    @Override
    public void update(EnrollmentDTO dto) throws ApplicationException, DuplicateRecordException {

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

            throw new ApplicationException("Exception in update Enrollment");

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
            Criteria criteria = session.createCriteria(EnrollmentDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in list Enrollment");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(EnrollmentDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(EnrollmentDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {

            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(EnrollmentDTO.class);

            if (dto.getEnrollmentCode() != null && dto.getEnrollmentCode().length() > 0) {
                criteria.add(Restrictions.like("enrollmentCode", dto.getEnrollmentCode() + "%"));
            }

            if (dto.getStudentName() != null && dto.getStudentName().length() > 0) {
                criteria.add(Restrictions.like("studentName", dto.getStudentName() + "%"));
            }

            if (dto.getEnrollmentStatus() != null && dto.getEnrollmentStatus().length() > 0) {
                criteria.add(Restrictions.like("enrollmentStatus", dto.getEnrollmentStatus() + "%"));
            }

            if (dto.getEnrollmentDate() != null) {
                criteria.add(Restrictions.eq("enrollmentDate", dto.getEnrollmentDate()));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in search Enrollment");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public EnrollmentDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        EnrollmentDTO dto = null;

        try {

            session = HibDataSource.getSession();
            dto = (EnrollmentDTO) session.get(EnrollmentDTO.class, pk);

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByPK Enrollment");

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public EnrollmentDTO findByEnrollmentCode(String enrollmentCode) throws ApplicationException {

        Session session = null;
        EnrollmentDTO dto = null;

        try {

            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(EnrollmentDTO.class);

            criteria.add(Restrictions.eq("enrollmentCode", enrollmentCode));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (EnrollmentDTO) list.get(0);
            }

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByEnrollmentCode");

        } finally {
            session.close();
        }

        return dto;
    }
}
