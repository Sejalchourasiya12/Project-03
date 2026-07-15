package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.HibDataSource;

public class BankModelHibImp implements BankModelInt {

	@Override
	public long add(BankDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
				/* log.debug("BankModel hib start"); */

				BankDTO existDto = null;
				existDto = findByAccountNo(dto.getAccountNo());
				if (existDto != null) {
					throw new DuplicateRecordException("Account NO already exist");
				}
				Session session = HibDataSource.getSession();
				Transaction tx = null;
				try {

					int pk = 0;
					tx = session.beginTransaction();

					session.save(dto);

					tx.commit();

				} catch (HibernateException e) {
					e.printStackTrace();
					// TODO: handle exception
					if (tx != null) {
						tx.rollback();

					}
					throw new ApplicationException("Exception in Bank Add " + e.getMessage());
				} finally {
					session.close();
				}
				/* log.debug("Model add End"); */
				return dto.getId();

			}

	@Override
	public void delete(BankDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
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
					throw new ApplicationException("Exception in Bank Delete" + e.getMessage());
				} finally {
					session.close();
				}
			}

	@Override
	public void update(BankDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		BankDTO existDto = findByAccountNo(dto.getAccountNo());
		// Check if updated LoginId already exist
		if (existDto != null && existDto.getId() != dto.getId()) {
			 throw new DuplicateRecordException("LoginId is already exist");
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
			throw new ApplicationException("Exception in Bank update" + e.getMessage());
		} finally {
			session.close();
		}
	}

		
	
	@Override
	public BankDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
				Session session = null;
				BankDTO dto = null;
				try {
					session = HibDataSource.getSession();
					dto = (BankDTO) session.get(BankDTO.class, pk);

				} catch (HibernateException e) {
					throw new ApplicationException("Exception : Exception in getting Bank by pk");
				} finally {
					session.close();
				}

				return dto;
			}


	@Override
	public BankDTO findByAccountNo(String accountNo) throws ApplicationException {
		Session session = HibDataSource.getSession();
		BankDTO dto = null;

		try {
			Query q = session.createQuery(
				"from BankDTO where accountNo = :accountNo");
			q.setString("accountNo", accountNo);

			List list = q.list();
			if (list.size() > 0) {
				dto = (BankDTO) list.get(0);
			}
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in findByAccountNo");
		} finally {
			session.close();
		}
		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
				return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Users list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(BankDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(BankDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		ArrayList<BankDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BankDTO.class);
			if (dto != null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.like("id", dto.getId()));
				}
				if (dto.getAccountNo()!= null && dto.getAccountNo().length() > 0) {
					criteria.add(Restrictions.like("accountNo", dto.getAccountNo() + "%"));
				}

				if (dto.getAccountHolder() != null &&dto.getAccountHolder().length() > 0) {
					criteria.add(Restrictions.like("accountHolder", dto.getAccountHolder() + "%"));
				}
				
				if (dto.getOpeningDate() != null && dto.getOpeningDate().getDate() > 0) {
					criteria.add(Restrictions.like("openingDate", dto.getOpeningDate() + "%"));
				}
				
				
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<BankDTO>) criteria.list();
			}
		}catch (HibernateException e) {
			throw new ApplicationException("Exception in Bank search");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public boolean deposit(long bankId, double amount)
	        throws ApplicationException, RecordNotFoundException {

	    Session session = null;
	    Transaction tx = null;

	    if (amount <= 0) {
	        throw new ApplicationException("Invalid deposit amount");
	    }

	    try {
	        session = HibDataSource.getSession();
	        tx = session.beginTransaction();

	        BankDTO dto = (BankDTO) session.get(BankDTO.class, bankId);

	        if (dto == null) {
	            throw new RecordNotFoundException("Bank account not found");
	        }

	        double newBalance = dto.getBalance() + amount;
	        dto.setBalance(newBalance);

	        session.update(dto);
	        tx.commit();
	        return true;

	    } catch (HibernateException e) {
	        if (tx != null) tx.rollback();
	        throw new ApplicationException("Exception in deposit " + e.getMessage());
	    } finally {
	        session.close();
	    }
	}


	@Override
	public boolean withdraw(long bankId, double amount)
			throws ApplicationException, RecordNotFoundException {

		if (amount <= 0) {
			throw new ApplicationException("Withdraw amount must be greater than zero");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			BankDTO dto = (BankDTO) session.get(BankDTO.class, bankId);

			if (dto == null) {
				throw new RecordNotFoundException("Bank account not found");
			}

			double balance = dto.getBalance();

			if (balance < amount) {
				throw new ApplicationException("Insufficient balance");
			}

			dto.setBalance(balance - amount);

			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

			return true;

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in withdraw : " + e.getMessage());
		} finally {
			session.close();
		}
	}

	


}
