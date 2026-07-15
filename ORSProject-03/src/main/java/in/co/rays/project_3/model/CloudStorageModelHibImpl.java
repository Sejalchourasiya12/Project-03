package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CloudStorageDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class CloudStorageModelHibImpl implements CloudStorageModelInt {

    @Override
    public long add(CloudStorageDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        CloudStorageDTO duplicate = findByFileName(dto.getFileName());
        if (duplicate != null) {
            throw new DuplicateRecordException("File Name already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add CloudStorage: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(CloudStorageDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete CloudStorage: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(CloudStorageDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        CloudStorageDTO duplicate = findByFileName(dto.getFileName());
        if (duplicate != null && !duplicate.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("File Name already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update CloudStorage: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(CloudStorageDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list CloudStorage: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(CloudStorageDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(CloudStorageDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CloudStorageDTO.class);

            if (dto.getFileName() != null && dto.getFileName().trim().length() > 0) {
                criteria.add(Restrictions.like("fileName", dto.getFileName().trim() + "%"));
            }
            if (dto.getFileSize() != null && dto.getFileSize().trim().length() > 0) {
                criteria.add(Restrictions.like("fileSize", dto.getFileSize().trim() + "%"));
            }
            if (dto.getStorageType() != null && dto.getStorageType().trim().length() > 0) {
                criteria.add(Restrictions.like("storageType", dto.getStorageType().trim() + "%"));
            }
            if (dto.getUploadDate() != null) {
                criteria.add(Restrictions.eq("uploadDate", dto.getUploadDate()));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search CloudStorage: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public CloudStorageDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        CloudStorageDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (CloudStorageDTO) session.get(CloudStorageDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK CloudStorage: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public CloudStorageDTO findByFileName(String fileName) throws ApplicationException {

        Session session = null;
        CloudStorageDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CloudStorageDTO.class);
            criteria.add(Restrictions.eq("fileName", fileName));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (CloudStorageDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByFileName: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}