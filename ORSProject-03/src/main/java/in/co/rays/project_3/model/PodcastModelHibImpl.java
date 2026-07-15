package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PodcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PodcastModelHibImpl implements PodcastModelInt {

    @Override
    public long add(PodcastDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        PodcastDTO duplicate = findByPodcastCode(dto.getPodcastCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Podcast Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add Podcast: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(PodcastDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete Podcast: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(PodcastDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update Podcast: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(PodcastDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list Podcast: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(PodcastDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(PodcastDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PodcastDTO.class);

            if (dto.getPodcastCode() != null && dto.getPodcastCode().length() > 0) {
                criteria.add(Restrictions.like("podcastCode", dto.getPodcastCode() + "%"));
            }
            if (dto.getPodcastTitle() != null && dto.getPodcastTitle().length() > 0) {
                criteria.add(Restrictions.like("podcastTitle", dto.getPodcastTitle() + "%"));
            }
            if (dto.getHostName() != null && dto.getHostName().length() > 0) {
                criteria.add(Restrictions.like("hostName", dto.getHostName() + "%"));
            }
            if (dto.getStatus() != null && dto.getStatus().length() > 0) {
                criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search Podcast: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public PodcastDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        PodcastDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (PodcastDTO) session.get(PodcastDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK Podcast: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public PodcastDTO findByPodcastCode(String podcastCode) throws ApplicationException {

        Session session = null;
        PodcastDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PodcastDTO.class);
            criteria.add(Restrictions.eq("podcastCode", podcastCode));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (PodcastDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPodcastCode: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}