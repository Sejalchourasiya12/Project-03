package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PromotionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PromotionModelHibImpl implements PromotionModelInt {

	@Override
	public long add(PromotionDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		PromotionDTO duplicate = findByName(dto.getPromotionTitle());

		if (duplicate != null) {
			throw new DuplicateRecordException("Promotion Title already exists");
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

			throw new ApplicationException("Exception in add Promotion " + e.getMessage());

		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(PromotionDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in delete Promotion");

		} finally {
			session.close();
		}
	}

	@Override
	public void update(PromotionDTO dto) throws ApplicationException, DuplicateRecordException {

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

			throw new ApplicationException("Exception in update Promotion");

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
			Criteria criteria = session.createCriteria(PromotionDTO.class);

			if (pageSize > 0) {

				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (Exception e) {

			throw new ApplicationException("Exception in list Promotion");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(PromotionDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(PromotionDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PromotionDTO.class);

			if (dto.getPromotionCode() != null && dto.getPromotionCode().length() > 0) {
				criteria.add(Restrictions.like("promotionCode", dto.getPromotionCode() + "%"));
			}

			if (dto.getPromotionTitle() != null && dto.getPromotionTitle().length() > 0) {
				criteria.add(Restrictions.like("promotionTitle", dto.getPromotionTitle() + "%"));
			}

			if (dto.getPromotionStatus() != null && dto.getPromotionStatus().length() > 0) {
				criteria.add(Restrictions.like("promotionStatus", dto.getPromotionStatus() + "%"));
			}

			if (dto.getStartDate() != null) {
				criteria.add(Restrictions.eq("startDate", dto.getStartDate()));
			}

			if (pageSize > 0) {

				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (Exception e) {

			throw new ApplicationException("Exception in search Promotion");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public PromotionDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		PromotionDTO dto = null;

		try {

			session = HibDataSource.getSession();
			dto = (PromotionDTO) session.get(PromotionDTO.class, pk);

		} catch (Exception e) {

			throw new ApplicationException("Exception in findByPK Promotion");

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public PromotionDTO findByName(String name) throws ApplicationException {

	    Session session = null;
	    PromotionDTO dto = null;

	    try {
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(PromotionDTO.class);
	        criteria.add(Restrictions.eq("promotionTitle", name));

	        List list = criteria.list();

	        if (list.size() == 1) {
	            dto = (PromotionDTO) list.get(0);
	        }

	    } catch (Exception e) {
	        throw new ApplicationException("Exception in findByName: " + e.getMessage()); // also log the real error!

	    } finally {
	        if (session != null) { // ✅ null check added
	            session.close();
	        }
	    }

	    return dto;
	}
}