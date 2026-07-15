package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.VehicleInsuranceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * VehicleInsuranceModelHibImpl - Hibernate Implementation for Vehicle Insurance
 *
 * @author Sejal Chourasiya
 */
public class VehicleInsuranceModelHibImpl implements VehicleInsuranceModelInt {

    @Override
    public long add(VehicleInsuranceDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        VehicleInsuranceDTO duplicate = findByVehicleNumber(dto.getVehicleNumber());
        if (duplicate != null) {
            throw new DuplicateRecordException("Vehicle Number already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in add VehicleInsurance: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(VehicleInsuranceDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in delete VehicleInsurance: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(VehicleInsuranceDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        VehicleInsuranceDTO duplicate = findByVehicleNumber(dto.getVehicleNumber());
        if (duplicate != null && !duplicate.getId().equals(dto.getId())) {
            throw new DuplicateRecordException("Vehicle Number already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in update VehicleInsurance: " + e.getMessage());
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
            Criteria criteria = session.createCriteria(VehicleInsuranceDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in list VehicleInsurance: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(VehicleInsuranceDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(VehicleInsuranceDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(VehicleInsuranceDTO.class);

            if (dto.getOwnerName() != null && dto.getOwnerName().trim().length() > 0) {
                criteria.add(Restrictions.like("ownerName", dto.getOwnerName().trim() + "%"));
            }
            if (dto.getVehicleNumber() != null && dto.getVehicleNumber().trim().length() > 0) {
                criteria.add(Restrictions.like("vehicleNumber", dto.getVehicleNumber().trim() + "%"));
            }
            if (dto.getVehicleType() != null && dto.getVehicleType().trim().length() > 0) {
                criteria.add(Restrictions.like("vehicleType", dto.getVehicleType().trim() + "%"));
            }
            if (dto.getInsuranceCompany() != null && dto.getInsuranceCompany().trim().length() > 0) {
                criteria.add(Restrictions.like("insuranceCompany", dto.getInsuranceCompany().trim() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (Exception e) {
            throw new ApplicationException("Exception in search VehicleInsurance: " + e.getMessage());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public VehicleInsuranceDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        VehicleInsuranceDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (VehicleInsuranceDTO) session.get(VehicleInsuranceDTO.class, pk);
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK VehicleInsurance: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public VehicleInsuranceDTO findByVehicleNumber(String vehicleNumber) throws ApplicationException {

        Session session = null;
        VehicleInsuranceDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(VehicleInsuranceDTO.class);
            criteria.add(Restrictions.eq("vehicleNumber", vehicleNumber));
            List list = criteria.list();
            if (list.size() == 1) {
                dto = (VehicleInsuranceDTO) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByVehicleNumber: " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }
}