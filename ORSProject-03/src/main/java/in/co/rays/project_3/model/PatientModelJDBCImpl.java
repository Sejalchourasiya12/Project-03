package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.PatientDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class PatientModelJDBCImpl implements PatientModelInt {

    private static Logger log = Logger.getLogger(PatientModelJDBCImpl.class);

    public long nextPK() throws DatabaseException {
        Connection con = null;
        long pk = 0;
        try {
            con = JDBCDataSource.getConnection();
            PreparedStatement ps =
                con.prepareStatement("SELECT MAX(ID) FROM ST_PATIENT");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pk = rs.getLong(1);
            }
        } catch (Exception e) {
            throw new DatabaseException("Database Exception " + e);
        } finally {
            JDBCDataSource.closeConnection(con);
        }
        return pk + 1;
    }

    @Override
    public long add(PatientDTO dto)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        long pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            conn.setAutoCommit(false);

            PreparedStatement ps =
                conn.prepareStatement("INSERT INTO ST_PATIENT VALUES (?,?,?,?,?,?,?,?)");

            ps.setLong(1, pk);
            ps.setString(2, dto.getName());
            ps.setDate(3, new java.sql.Date(dto.getDob().getTime()));
            ps.setString(4, dto.getMobileNo());
            ps.setString(5, dto.getDecease());
            ps.setString(6, dto.getCreatedBy());
            ps.setString(7, dto.getModifiedBy());
            ps.setTimestamp(8, dto.getCreatedDatetime());

            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            throw new ApplicationException("Exception in Patient Add");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    @Override
    public void delete(PatientDTO dto) throws ApplicationException {
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps =
                conn.prepareStatement("DELETE FROM ST_PATIENT WHERE ID=?");
            ps.setLong(1, dto.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in Patient Delete");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }
    
    @Override
    public void update(PatientDTO dto)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps =
                conn.prepareStatement(
                    "UPDATE ST_PATIENT SET NAME=?, DOB=?, MOBILE_NO=?, DECEASE=?, MODIFIED_BY=?, MODIFIED_DATETIME=? WHERE ID=?");

            ps.setString(1, dto.getName());
            ps.setDate(2, new java.sql.Date(dto.getDob().getTime()));
            ps.setString(3, dto.getMobileNo());
            ps.setString(4, dto.getDecease());
            ps.setString(5, dto.getModifiedBy());
            ps.setTimestamp(6, dto.getModifiedDatetime());
            ps.setLong(7, dto.getId());

            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            throw new ApplicationException("Exception in Patient Update");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }


    @Override
    public PatientDTO findByPK(long pk) throws ApplicationException {

        Connection conn = null;
        PatientDTO dto = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps =
                conn.prepareStatement("SELECT * FROM ST_PATIENT WHERE ID=?");
            ps.setLong(1, pk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                dto = new PatientDTO();
                dto.setId(rs.getLong(1));
                dto.setName(rs.getString(2));
                dto.setDob(rs.getDate(3));
                dto.setMobileNo(rs.getString(4));
                dto.setDecease(rs.getString(5));
            }
            rs.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in Patient findByPK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return dto;
    }

    @Override
    public List search(PatientDTO dto, int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList list = new ArrayList();
        StringBuffer sql =
            new StringBuffer("SELECT * FROM ST_PATIENT WHERE 1=1");

        if (dto != null) {
            if (dto.getName() != null) {
                sql.append(" AND NAME LIKE '" + dto.getName() + "%'");
            }
            if (dto.getMobileNo() != null) {
                sql.append(" AND MOBILE_NO LIKE '" + dto.getMobileNo() + "%'");
            }
            if (dto.getDecease() != null) {
                sql.append(" AND DECEASE LIKE '" + dto.getDecease() + "%'");
            }
        }

        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PatientDTO p = new PatientDTO();
                p.setId(rs.getLong(1));
                p.setName(rs.getString(2));
                p.setDob(rs.getDate(3));
                p.setMobileNo(rs.getString(4));
                p.setDecease(rs.getString(5));
                list.add(p);
            }
            rs.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in Patient Search");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }

    @Override
    public List search(PatientDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

	

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize)
	        throws ApplicationException {

	    ArrayList list = new ArrayList();
	    StringBuffer sql = new StringBuffer("SELECT * FROM ST_PATIENT");

	    if (pageSize > 0) {
	        pageNo = (pageNo - 1) * pageSize;
	        sql.append(" LIMIT " + pageNo + ", " + pageSize);
	    }

	    Connection conn = null;

	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement ps = conn.prepareStatement(sql.toString());
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            PatientDTO dto = new PatientDTO();
	            dto.setId(rs.getLong(1));
	            dto.setName(rs.getString(2));
	            dto.setDob(rs.getDate(3));
	            dto.setMobileNo(rs.getString(4));
	            dto.setDecease(rs.getString(5));
	            list.add(dto);
	        }

	        rs.close();
	        ps.close();

	    } catch (Exception e) {
	        throw new ApplicationException("Exception in Patient List");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }

	    return list;
	}

	
}
