package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.StaffMemberDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of StaffMember model
 * 
 * @author Sejal
 *
 */
public class StaffMemberModelHibImp implements StaffMemberModelInt {

	@Override
	public long add(StaffMemberDTO dto) throws ApplicationException, DuplicateRecordException {

	
	
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			int pk = 0;
			tx = session.beginTransaction();

			session.save(dto);

			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in User Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();

	}

	@Override
	public void delete(StaffMemberDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in StaffMember delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(StaffMemberDTO dto) throws ApplicationException, DuplicateRecordException {

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
			throw new ApplicationException("Exception in StaffMember update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public StaffMemberDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		StaffMemberDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (StaffMemberDTO) session.get(StaffMemberDTO.class, pk);
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in StaffMember findByPK");
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
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(StaffMemberDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in StaffMember list");
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List search(StaffMemberDTO dto, int pageNo, int pageSize) throws ApplicationException {

	    Session session = null;
	    List<StaffMemberDTO> list = null;

	    try {
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(StaffMemberDTO.class);

	        if (dto != null) {

	            if (dto.getId() > 0) {
	                criteria.add(Restrictions.eq("id", dto.getId()));
	            }

	            if (dto.getFullName() != null && dto.getFullName().trim().length() > 0) {
	                criteria.add(Restrictions.like("fullName", dto.getFullName() + "%"));
	            }

	            if (dto.getDivision() != null && dto.getDivision().trim().length() > 0) {
	                criteria.add(Restrictions.like("division", dto.getDivision() + "%"));
	            }

	            if (dto.getJoiningDate() != null) {
	                criteria.add(Restrictions.eq("joiningDate", dto.getJoiningDate()));
	            }

	            if (dto.getPreviousEmployeeId() > 0) {
	                criteria.add(Restrictions.eq("previousEmployeeId", dto.getPreviousEmployeeId()));
	            }
	        }

	        if (pageSize > 0) {
	            int firstResult = (pageNo - 1) * pageSize;
	            if (firstResult < 0) {
	                firstResult = 0;
	            }
	            criteria.setFirstResult(firstResult);
	            criteria.setMaxResults(pageSize);
	        }

	        list = criteria.list();

	    } catch (HibernateException e) {
	        throw new ApplicationException("Exception in StaffMember search");
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	    return list;
	}

	@Override
	public List<StaffMemberDTO> search(StaffMemberDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}


	

}
