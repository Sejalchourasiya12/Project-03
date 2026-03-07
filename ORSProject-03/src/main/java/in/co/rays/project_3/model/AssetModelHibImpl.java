package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AssetDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class AssetModelHibImpl implements AssetModelInt {

    @Override
    public long add(AssetDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        AssetDTO duplicateAsset = findByName(dto.getAssetName());
        if (duplicateAsset != null) {
            throw new DuplicateRecordException("Asset Name already exists");
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
            throw new ApplicationException("Exception in Asset Add " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();   // ⚠ Make sure BaseDTO me id ho
    }

    @Override
    public void delete(AssetDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Asset Delete " + e.getMessage());

        } finally {
            session.close();
        }
    }

    @Override
    public void update(AssetDTO dto)
            throws ApplicationException, DuplicateRecordException {

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
            throw new ApplicationException("Exception in Asset Update " + e.getMessage());

        } finally {
            session.close();
        }
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
            Criteria criteria = session.createCriteria(AssetDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Asset List");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(AssetDTO dto)
            throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(AssetDTO dto, int pageNo, int pageSize)
            throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AssetDTO.class);

            if (dto.getAssetCode() != null && dto.getAssetCode().length() > 0) {
                criteria.add(Restrictions.like("assetCode", dto.getAssetCode() + "%"));
            }

            if (dto.getAssetName() != null && dto.getAssetName().length() > 0) {
                criteria.add(Restrictions.like("assetName", dto.getAssetName() + "%"));
            }

            if (dto.getAssignedTo() != null && dto.getAssignedTo().length() > 0) {
                criteria.add(Restrictions.like("assignedTo", dto.getAssignedTo() + "%"));
            }

            if (dto.getAssetStatus() != null && dto.getAssetStatus().length() > 0) {
                criteria.add(Restrictions.like("assetStatus", dto.getAssetStatus() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Asset Search");

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public AssetDTO findByPK(long pk)
            throws ApplicationException {

        Session session = null;
        AssetDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (AssetDTO) session.get(AssetDTO.class, pk);

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Asset findByPK");

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public AssetDTO findByName(String name)
            throws ApplicationException {

        Session session = null;
        AssetDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AssetDTO.class);
            criteria.add(Restrictions.eq("assetName", name));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (AssetDTO) list.get(0);
            }

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Asset findByName");

        } finally {
            session.close();
        }

        return dto;
    }
}