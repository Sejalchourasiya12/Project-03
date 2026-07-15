package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.VoiceCommandDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * VoiceCommandModelHibImpl - Hibernate Implementation for Voice Command
 *
 * @author Sejal Chourasiya
 */
public class VoiceCommandModelHibImpl implements VoiceCommandModelInt {

    @Override
    public long add(VoiceCommandDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        VoiceCommandDTO duplicate = findByCommandCode(dto.getCommandCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Command Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add VoiceCommand: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(VoiceCommandDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete VoiceCommand: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(VoiceCommandDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        VoiceCommandDTO duplicate = findByCommandCode(dto.getCommandCode());
        if (duplicate != null && !duplicate.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("Command Code already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update VoiceCommand: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(VoiceCommandDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list VoiceCommand: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(VoiceCommandDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(VoiceCommandDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(VoiceCommandDTO.class);

            if (dto.getCommandCode() != null && dto.getCommandCode().trim().length() > 0) {
                criteria.add(Restrictions.like("commandCode", dto.getCommandCode().trim() + "%"));
            }
            if (dto.getUsername() != null && dto.getUsername().trim().length() > 0) {
                criteria.add(Restrictions.like("username", dto.getUsername().trim() + "%"));
            }
            if (dto.getCommandText() != null && dto.getCommandText().trim().length() > 0) {
                criteria.add(Restrictions.like("commandText", dto.getCommandText().trim() + "%"));
            }
            if (dto.getStatus() != null && dto.getStatus().trim().length() > 0) {
                criteria.add(Restrictions.like("status", dto.getStatus().trim() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search VoiceCommand: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public VoiceCommandDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        VoiceCommandDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (VoiceCommandDTO) session.get(VoiceCommandDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK VoiceCommand: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public VoiceCommandDTO findByCommandCode(String commandCode) throws ApplicationException {

        Session session = null;
        VoiceCommandDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(VoiceCommandDTO.class);
            criteria.add(Restrictions.eq("commandCode", commandCode));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (VoiceCommandDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByCommandCode: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}