package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PatientDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PatientModelHibImpl implements PatientModelInt {

    @Override
    public long add(PatientDTO dto)
            throws ApplicationException, DuplicateRecordException {

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in Patient Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(PatientDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in Patient Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(PatientDTO dto)
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
            throw new ApplicationException("Exception in Patient Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public PatientDTO findByPK(long pk)
            throws ApplicationException {

        Session session = null;
        PatientDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (PatientDTO) session.get(PatientDTO.class, pk);

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Patient findByPK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public List list() throws ApplicationException {
        return list(0, 0);
    }

    @Override
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PatientDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Patient List");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(PatientDTO dto, int pageNo, int pageSize)
            throws ApplicationException {

    	 Session session = null;
	        List list = null;
	        try {
	            session = HibDataSource.getSession();
	            Criteria criteria = session.createCriteria(PatientDTO.class);

	       
	            if (dto.getName() != null && dto.getName().length() > 0) {
	                criteria.add(Restrictions.like("name", dto.getName() + "%"));
	            }
	            if (dto.getDecease() != null && dto.getDecease().length() > 0) {
	                criteria.add(Restrictions.like("decease", dto.getDecease()
	                        + "%"));
	            }
	            
	            

	            // if page size is greater than zero the apply pagination
	            if (pageSize > 0) {
	                criteria.setFirstResult(((pageNo - 1) * pageSize));
	                criteria.setMaxResults(pageSize);
	            }

	            list = criteria.list();
	        } catch (HibernateException e) {
	            
	            throw new ApplicationException("Exception in course search");
	        } finally {
	            session.close();
	        }

	       
	        return list;
    }

	@Override
	public List search(PatientDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		 return search(dto, 0, 0);
	}
}
