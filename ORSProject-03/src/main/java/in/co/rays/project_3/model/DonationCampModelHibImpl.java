package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DonationCampDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DonationCampModelHibImpl implements DonationCampModelInt {

    @Override
    public long add(DonationCampDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        DonationCampDTO duplicate = findByOrderNumber(dto.getCampName());

        if (duplicate != null) {
            throw new DuplicateRecordException("Camp Name already exists");
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

            throw new ApplicationException("Exception in add DonationCamp " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(DonationCampDTO dto) throws ApplicationException {

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

            throw new ApplicationException("Exception in delete DonationCamp");

        } finally {
            session.close();
        }
    }

    @Override
    public void update(DonationCampDTO dto) throws ApplicationException, DuplicateRecordException {

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

            throw new ApplicationException("Exception in update DonationCamp");

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

            Criteria criteria = session.createCriteria(DonationCampDTO.class);

            if (pageSize > 0) {

                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in list DonationCamp");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(DonationCampDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(DonationCampDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {

            session = HibDataSource.getSession();

            Criteria criteria = session.createCriteria(DonationCampDTO.class);

            if (dto.getCampName() != null && dto.getCampName().length() > 0) {
                criteria.add(Restrictions.like("campName", dto.getCampName() + "%"));
            }

            if (dto.getOrganizer() != null && dto.getOrganizer().length() > 0) {
                criteria.add(Restrictions.like("organizer", dto.getOrganizer() + "%"));
            }

            if (dto.getCampDate() != null) {
                criteria.add(Restrictions.eq("campDate", dto.getCampDate()));
            }

            if (pageSize > 0) {

                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {

            throw new ApplicationException("Exception in search DonationCamp");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public DonationCampDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        DonationCampDTO dto = null;

        try {

            session = HibDataSource.getSession();

            dto = (DonationCampDTO) session.get(DonationCampDTO.class, pk);

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByPK DonationCamp");

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public DonationCampDTO findByOrderNumber(String name) throws ApplicationException {

        Session session = null;
        DonationCampDTO dto = null;

        try {

            session = HibDataSource.getSession();

            Criteria criteria = session.createCriteria(DonationCampDTO.class);

            criteria.add(Restrictions.eq("campName", name));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (DonationCampDTO) list.get(0);
            }

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByCampName");

        } finally {
            session.close();
        }

        return dto;
    }
}