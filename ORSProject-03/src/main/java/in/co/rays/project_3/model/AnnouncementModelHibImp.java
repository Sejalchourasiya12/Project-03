package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AnnouncementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class AnnouncementModelHibImp implements AnnouncementModelInt {

    @Override
    public long add(AnnouncementDTO dto)
            throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        AnnouncementDTO existDto = findByTitle(dto.getTitle());

        if (existDto != null) {
            throw new DuplicateRecordException("Announcement Title already exists");
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
            throw new ApplicationException("Exception in Announcement Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(AnnouncementDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Announcement Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(AnnouncementDTO dto)
            throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        AnnouncementDTO existDto = findByTitle(dto.getTitle());

        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Announcement Title already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new ApplicationException("Exception in Announcement Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public AnnouncementDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        AnnouncementDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (AnnouncementDTO) session.get(AnnouncementDTO.class, pk);

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Announcement by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    public AnnouncementDTO findByTitle(String title)
            throws ApplicationException {

        Session session = null;
        AnnouncementDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AnnouncementDTO.class);
            criteria.add(Restrictions.eq("title", title));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (AnnouncementDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in findByTitle " + e.getMessage());
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
            Criteria criteria = session.createCriteria(AnnouncementDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Announcement List");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(AnnouncementDTO dto)
            throws ApplicationException {

        return search(dto, 0, 0);
    }

    @Override
    public List search(AnnouncementDTO dto, int pageNo, int pageSize)
            throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AnnouncementDTO.class);

            if (dto != null) {

                if (dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getTitle() != null && dto.getTitle().length() > 0) {
                    criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
                }

                if (dto.getAnnouncementCode() != null
                        && dto.getAnnouncementCode().length() > 0) {
                    criteria.add(Restrictions.like("announcementCode",
                            dto.getAnnouncementCode() + "%"));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Announcement Search");
        } finally {
            session.close();
        }

        return list;
    }
}

    

	
