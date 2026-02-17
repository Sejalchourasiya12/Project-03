package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.JDBCDataSource;

public class BankModelJDBCImpl implements BankModelInt {

	/* ================= ADD ================= */
	@Override
	public long add(BankDTO dto)
			throws ApplicationException, DuplicateRecordException {

		BankDTO existDto = findByAccountNo(dto.getAccountNo());
		if (existDto != null) {
			throw new DuplicateRecordException("Account No already exists");
		}

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(
				"INSERT INTO ST_BANK (ACCOUNTNO, ACCOUNTHOLDER, ACCOUNTTYPE, OPENINGDATE, BALANCE, ACCOUNTLIMIT, USER_ID) "
			  + "VALUES (?, ?, ?, ?, ?, ?, ?)");

			ps.setString(1, dto.getAccountNo());
			ps.setString(2, dto.getAccountHolder());
			ps.setString(3, dto.getAccountType());
			ps.setDate(4, new java.sql.Date(dto.getOpeningDate().getTime()));
			ps.setDouble(5, dto.getBalance());
			ps.setDouble(6, dto.getAccountLimit());
			
			ps.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException("Exception in Bank Add");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return 0;
	}

	/* ================= UPDATE ================= */
	@Override
	public void update(BankDTO dto) throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(
				"UPDATE ST_BANK SET ACCOUNTHOLDER=?, ACCOUNTTYPE=?, BALANCE=?, ACCOUNTLIMIT=? WHERE ID=?");

			ps.setString(1, dto.getAccountHolder());
			ps.setString(2, dto.getAccountType());
			ps.setDouble(3, dto.getBalance());
			ps.setDouble(4, dto.getAccountLimit());
			ps.setLong(5, dto.getId());

			ps.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException("Exception in Bank Update");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/* ================= DELETE ================= */
	@Override
	public void delete(BankDTO dto) throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("DELETE FROM ST_BANK WHERE ID=?");
			ps.setLong(1, dto.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException("Exception in Bank Delete");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/* ================= FIND BY PK ================= */
	@Override
	public BankDTO findByPK(long pk) throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BankDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_BANK WHERE ID=?");
			ps.setLong(1, pk);

			rs = ps.executeQuery();
			if (rs.next()) {
				dto = new BankDTO();
				dto.setId(rs.getLong("ID"));
				dto.setAccountNo(rs.getString("ACCOUNTNO"));
				dto.setAccountHolder(rs.getString("ACCOUNTHOLDER"));
				dto.setAccountType(rs.getString("ACCOUNTTYPE"));
				dto.setBalance(rs.getDouble("BALANCE"));
				dto.setAccountLimit(rs.getDouble("ACCOUNTLIMIT"));
				
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	/* ================= FIND BY ACCOUNT NO ================= */
	@Override
	public BankDTO findByAccountNo(String accountNo) throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BankDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(
				"SELECT * FROM ST_BANK WHERE ACCOUNTNO=?");
			ps.setString(1, accountNo);

			rs = ps.executeQuery();
			if (rs.next()) {
				dto = new BankDTO();
				dto.setId(rs.getLong("ID"));
				dto.setAccountNo(rs.getString("ACCOUNTNO"));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in findByAccountNo");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	/* ================= LIST ================= */
	@Override
	public List list() throws ApplicationException {

		Connection conn = null;
		List list = new ArrayList();

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps =
				conn.prepareStatement("SELECT * FROM ST_BANK");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BankDTO dto = new BankDTO();
				dto.setId(rs.getLong("ID"));
				dto.setAccountNo(rs.getString("ACCOUNTNO"));
				dto.setAccountHolder(rs.getString("ACCOUNTHOLDER"));
				dto.setBalance(rs.getDouble("BALANCE"));
				list.add(dto);
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in Bank List");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	/* ================= SEARCH ================= */
	@Override
	public List search(BankDTO dto) throws ApplicationException {

		Connection conn = null;
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_BANK WHERE 1=1");

		if (dto.getAccountNo() != null && dto.getAccountNo().length() > 0) {
			sql.append(" AND ACCOUNTNO LIKE '" + dto.getAccountNo() + "%'");
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BankDTO b = new BankDTO();
				b.setId(rs.getLong("ID"));
				b.setAccountNo(rs.getString("ACCOUNTNO"));
				b.setAccountHolder(rs.getString("ACCOUNTHOLDER"));
				b.setBalance(rs.getDouble("BALANCE"));
				list.add(b);
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in Bank Search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	/* ================= DEPOSIT ================= */
	@Override
	public boolean deposit(long bankId, double amount)
			throws ApplicationException, RecordNotFoundException {

		BankDTO dto = findByPK(bankId);
		if (dto == null) {
			throw new RecordNotFoundException("Bank Account not found");
		}

		dto.setBalance(dto.getBalance() + amount);
		update(dto);
		return true;
	}

	/* ================= WITHDRAW ================= */
	@Override
	public boolean withdraw(long bankId, double amount)
			throws ApplicationException, RecordNotFoundException {

		BankDTO dto = findByPK(bankId);
		if (dto == null) {
			throw new RecordNotFoundException("Bank Account not found");
		}

		if (dto.getBalance() < amount) {
			throw new ApplicationException("Insufficient Balance");
		}

		dto.setBalance(dto.getBalance() - amount);
		update(dto);
		return true;
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List list = new ArrayList();

		int start = (pageNo - 1) * pageSize;

		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(
				"SELECT * FROM ST_BANK LIMIT ?, ?");
			ps.setInt(1, start);
			ps.setInt(2, pageSize);

			rs = ps.executeQuery();

			while (rs.next()) {
				BankDTO dto = new BankDTO();
				dto.setId(rs.getLong("ID"));
				dto.setAccountNo(rs.getString("ACCOUNTNO"));
				dto.setAccountHolder(rs.getString("ACCOUNTHOLDER"));
				dto.setAccountType(rs.getString("ACCOUNTTYPE"));
				dto.setBalance(rs.getDouble("BALANCE"));
				list.add(dto);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in Bank List Pagination");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}


	@Override
	public List search(BankDTO dto, int pageNo, int pageSize)
			throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List list = new ArrayList();

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_BANK WHERE 1=1");

		if (dto != null) {

			if (dto.getAccountNo() != null && dto.getAccountNo().length() > 0) {
				sql.append(" AND ACCOUNTNO LIKE '" + dto.getAccountNo() + "%'");
			}

			if (dto.getAccountHolder() != null && dto.getAccountHolder().length() > 0) {
				sql.append(" AND ACCOUNTHOLDER LIKE '" + dto.getAccountHolder() + "%'");
			}
		}

		int start = (pageNo - 1) * pageSize;
		sql.append(" LIMIT " + start + "," + pageSize);

		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				BankDTO b = new BankDTO();
				b.setId(rs.getLong("ID"));
				b.setAccountNo(rs.getString("ACCOUNTNO"));
				b.setAccountHolder(rs.getString("ACCOUNTHOLDER"));
				b.setAccountType(rs.getString("ACCOUNTTYPE"));
				b.setBalance(rs.getDouble("BALANCE"));
				list.add(b);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in Bank Search Pagination");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}
