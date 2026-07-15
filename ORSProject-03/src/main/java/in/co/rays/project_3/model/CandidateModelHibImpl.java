package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CandidateDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class CandidateModelHibImpl implements CandidateModelInt {

    public long add(CandidateDTO dto) throws ApplicationException, DuplicateRecordException {
        Session session = null;
        Transaction tx = null;

        CandidateDTO duplicateCandidate = findByName(dto.getCandidateName());
        if (duplicateCandidate != null) {
            throw new DuplicateRecordException("Candidate Name already exist");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            throw new ApplicationException("Exception in Candidate Add " + e.getMessage());
        } finally {
            session.close();
        }
        return dto.getId();
    }

    public void delete(CandidateDTO dto) throws ApplicationException {
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
            throw new ApplicationException("Exception in Candidate Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void update(CandidateDTO dto) throws ApplicationException, DuplicateRecordException {
        Session session = null;
        Transaction tx = null;

        CandidateDTO dtoExist = findByName(dto.getCandidateName());

        /*
        if (dtoExist != null && dtoExist.getId() != dto.getId()) {
            throw new DuplicateRecordException("Candidate already exist");
        }
        */

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            System.out.println("before update");

            session.saveOrUpdate(dto);

            System.out.println("after update");
            tx.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            throw new ApplicationException("Exception in Candidate update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public List list() throws ApplicationException {
        return list(0, 0);
    }

    public List list(int pageNo, int pageSize) throws ApplicationException {
        Session session = null;
        List list = null;
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CandidateDTO.class);

            if (pageSize > 0) {
                pageNo = ((pageNo - 1) * pageSize) + 1;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Candidate list");
        } finally {
            session.close();
        }
        return list;
    }

    public List search(CandidateDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(CandidateDTO dto, int pageNo, int pageSize) throws ApplicationException {
        Session session = null;
        List list = null;
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CandidateDTO.class);

            if (dto.getId() > 0) {
                criteria.add(Restrictions.eq("id", dto.getId()));
            }

            if (dto.getCandidateCode() != null && dto.getCandidateCode().length() > 0) {
                criteria.add(Restrictions.like("candidateCode", dto.getCandidateCode() + "%"));
            }

            if (dto.getCandidateName() != null && dto.getCandidateName().length() > 0) {
                criteria.add(Restrictions.like("candidateName", dto.getCandidateName() + "%"));
            }

            if (dto.getEmail() != null && dto.getEmail().length() > 0) {
                criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
            }

            if (dto.getSkillSet() != null && dto.getSkillSet().length() > 0) {
                criteria.add(Restrictions.like("skillSet", dto.getSkillSet() + "%"));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in Candidate search");
        } finally {
            session.close();
        }
        return list;
    }

    public CandidateDTO findByPK(long pk) throws ApplicationException {
        System.out.println("====== " + pk + " -----------");
        Session session = null;
        CandidateDTO dto = null;
        try {
            session = HibDataSource.getSession();
            dto = (CandidateDTO) session.get(CandidateDTO.class, pk);
            System.out.println(dto);
        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Candidate by pk");
        } finally {
            session.close();
        }
        System.out.println("++++ " + dto);
        return dto;
    }

    public CandidateDTO findByName(String name) throws ApplicationException {
        Session session = null;
        CandidateDTO dto = null;
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CandidateDTO.class);
            criteria.add(Restrictions.eq("candidateName", name));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (CandidateDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException(
                    "Exception in getting Candidate by Name " + e.getMessage());
        } finally {
            session.close();
        }
        return dto;
    }

	
}