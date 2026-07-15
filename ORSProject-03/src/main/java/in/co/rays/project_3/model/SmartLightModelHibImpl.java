package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SmartLightDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SmartLightModelHibImpl implements SmartLightModelInt {

    @Override
    public long add(SmartLightDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        SmartLightDTO duplicate = findByLightCode(dto.getLightCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Light Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add SmartLight: " + e.getMessage());

        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(SmartLightDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete SmartLight: " + e.getMessage());

        } finally {
            session.close();
        }
    }

    @Override
    public void update(SmartLightDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update SmartLight: " + e.getMessage());

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
            Criteria criteria = session.createCriteria(SmartLightDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (Exception e) {
            throw new ApplicationException("Exception in list SmartLight: " + e.getMessage());

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(SmartLightDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(SmartLightDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SmartLightDTO.class);

            if (dto.getLightCode() != null && dto.getLightCode().length() > 0) {
                criteria.add(Restrictions.like("lightCode", dto.getLightCode() + "%"));
            }
            if (dto.getRoomName() != null && dto.getRoomName().length() > 0) {
                criteria.add(Restrictions.like("roomName", dto.getRoomName() + "%"));
            }
            if (dto.getBrightnessLevel() != null && dto.getBrightnessLevel() > 0) {
                criteria.add(Restrictions.eq("brightnessLevel", dto.getBrightnessLevel()));
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
            throw new ApplicationException("Exception in search SmartLight: " + e.getMessage());

        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public SmartLightDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        SmartLightDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (SmartLightDTO) session.get(SmartLightDTO.class, pk);

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK SmartLight: " + e.getMessage());

        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public SmartLightDTO findByLightCode(String lightCode) throws ApplicationException {

        Session session = null;
        SmartLightDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SmartLightDTO.class);
            criteria.add(Restrictions.eq("lightCode", lightCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (SmartLightDTO) list.get(0);
            }

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByLightCode: " + e.getMessage());

        } finally {
            session.close();
        }

        return dto;
    }
}