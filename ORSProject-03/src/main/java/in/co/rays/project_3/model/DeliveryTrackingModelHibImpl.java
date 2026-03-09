package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DeliveryTrackingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DeliveryTrackingModelHibImpl implements DeliveryTrackingModelInt {

	@Override
	public long add(DeliveryTrackingDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		DeliveryTrackingDTO duplicate = findByOrderNumber(dto.getOrderNumber());

		if (duplicate != null) {
			throw new DuplicateRecordException("Order Number already exists");
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

			throw new ApplicationException("Exception in add DeliveryTracking " + e.getMessage());

		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(DeliveryTrackingDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in delete DeliveryTracking");

		} finally {
			session.close();
		}
	}

	@Override
	public void update(DeliveryTrackingDTO dto) throws ApplicationException, DuplicateRecordException {

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

			throw new ApplicationException("Exception in update DeliveryTracking");

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
			Criteria criteria = session.createCriteria(DeliveryTrackingDTO.class);

			if (pageSize > 0) {

				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (Exception e) {

			throw new ApplicationException("Exception in list DeliveryTracking");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(DeliveryTrackingDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(DeliveryTrackingDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DeliveryTrackingDTO.class);

			if (dto.getOrderNumber() != null && dto.getOrderNumber().length() > 0) {
				criteria.add(Restrictions.like("orderNumber", dto.getOrderNumber() + "%"));
			}

			if (dto.getCustomerName() != null && dto.getCustomerName().length() > 0) {
				criteria.add(Restrictions.like("customerName", dto.getCustomerName() + "%"));
			}

			if (dto.getDeliveryStatus() != null && dto.getDeliveryStatus().length() > 0) {
				criteria.add(Restrictions.like("deliveryStatus", dto.getDeliveryStatus() + "%"));
			}

			if (dto.getDeliveryDate() != null) {
				criteria.add(Restrictions.eq("deliveryDate", dto.getDeliveryDate()));
			}

			if (pageSize > 0) {

				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (Exception e) {

			throw new ApplicationException("Exception in search DeliveryTracking");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public DeliveryTrackingDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		DeliveryTrackingDTO dto = null;

		try {

			session = HibDataSource.getSession();
			dto = (DeliveryTrackingDTO) session.get(DeliveryTrackingDTO.class, pk);

		} catch (Exception e) {

			throw new ApplicationException("Exception in findByPK DeliveryTracking");

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public DeliveryTrackingDTO findByOrderNumber(String name) throws ApplicationException {

		Session session = null;
		DeliveryTrackingDTO dto = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DeliveryTrackingDTO.class);

			criteria.add(Restrictions.eq("orderNumber", name));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (DeliveryTrackingDTO) list.get(0);
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in findByOrderNumber");

		} finally {
			session.close();
		}

		return dto;
	}
}