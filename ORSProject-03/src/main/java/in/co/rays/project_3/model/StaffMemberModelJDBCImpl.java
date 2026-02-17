package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.project_3.dto.StaffMemberDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC Implementation of StaffMember Model
 * 
 * @author Sejal Chourasiya
 *
 */
public class StaffMemberModelJDBCImpl implements StaffMemberModelInt {

    public long nextPK() throws ApplicationException {
        long pk = 0;
        try (Connection con = JDBCDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT MAX(ID) FROM ST_STAFF_MEMBER");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                pk = rs.getLong(1);
            }
        } catch (Exception e) {
            throw new ApplicationException("Database Exception in nextPK: " + e.getMessage());
        }
        return pk + 1;
    }

    @Override
    public long add(StaffMemberDTO dto) throws ApplicationException, DuplicateRecordException {
        Connection con = null;
        long pk = 0;

        // Check for duplicate previousEmployeeId
        if (dto.getPreviousEmployeeId() > 0) {
            StaffMemberDTO exist = null;
            List<StaffMemberDTO> list = search(new StaffMemberDTO());
            for (StaffMemberDTO s : list) {
                if (s.getPreviousEmployeeId() == dto.getPreviousEmployeeId()) {
                    throw new DuplicateRecordException("Previous Employee ID already exists");
                }
            }
        }

        try {
            con = JDBCDataSource.getConnection();
            con.setAutoCommit(false);

            pk = nextPK();
            dto.setId((long) pk);

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ST_STAFF_MEMBER (ID, FULL_NAME, JOINING_DATE, DIVISION, PREVIOUS_EMPLOYEE_ID, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME) VALUES (?,?,?,?,?,?,?,?,?)"
            );
            ps.setLong(1, pk);
            ps.setString(2, dto.getFullName());
            ps.setDate(3, new java.sql.Date(dto.getJoiningDate().getTime()));
            ps.setString(4, dto.getDivision());
            ps.setInt(5, dto.getPreviousEmployeeId());
            ps.setString(6, dto.getCreatedBy());
            ps.setString(7, dto.getModifiedBy());
            ps.setTimestamp(8, dto.getCreatedDatetime() != null ? dto.getCreatedDatetime() : new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(9, dto.getModifiedDatetime() != null ? dto.getModifiedDatetime() : new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in StaffMember add: " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }

        return pk;
    }

    @Override
    public void delete(StaffMemberDTO dto) throws ApplicationException {
        Connection con = null;
        try {
            con = JDBCDataSource.getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement("DELETE FROM ST_STAFF_MEMBER WHERE ID=?");
            ps.setLong(1, dto.getId());
            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in StaffMember delete: " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }
    }

    @Override
    public void update(StaffMemberDTO dto) throws ApplicationException, DuplicateRecordException {
        Connection con = null;

        // Check duplicate previousEmployeeId
        if (dto.getPreviousEmployeeId() > 0) {
            List<StaffMemberDTO> list = search(new StaffMemberDTO());
            for (StaffMemberDTO s : list) {
                if (s.getPreviousEmployeeId() == dto.getPreviousEmployeeId() && s.getId() != dto.getId()) {
                    throw new DuplicateRecordException("Previous Employee ID already exists");
                }
            }
        }

        try {
            con = JDBCDataSource.getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ST_STAFF_MEMBER SET FULL_NAME=?, JOINING_DATE=?, DIVISION=?, PREVIOUS_EMPLOYEE_ID=?, MODIFIED_BY=?, MODIFIED_DATETIME=? WHERE ID=?"
            );
            ps.setString(1, dto.getFullName());
            ps.setDate(2, new java.sql.Date(dto.getJoiningDate().getTime()));
            ps.setString(3, dto.getDivision());
            ps.setInt(4, dto.getPreviousEmployeeId());
            ps.setString(5, dto.getModifiedBy());
            ps.setTimestamp(6, dto.getModifiedDatetime() != null ? dto.getModifiedDatetime() : new Timestamp(System.currentTimeMillis()));
            ps.setLong(7, dto.getId());

            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in StaffMember update: " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }
    }

    @Override
    public StaffMemberDTO findByPK(long pk) throws ApplicationException {
        StaffMemberDTO dto = null;
        try (Connection con = JDBCDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_STAFF_MEMBER WHERE ID=?")) {

            ps.setLong(1, pk);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = populateDTO(rs);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in StaffMember findByPK: " + e.getMessage());
        }
        return dto;
    }

    @Override
    public List<StaffMemberDTO> list() throws ApplicationException {
        return list(0, 0);
    }

    @Override
    public List<StaffMemberDTO> list(int pageNo, int pageSize) throws ApplicationException {
        List<StaffMemberDTO> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT * FROM ST_STAFF_MEMBER WHERE 1=1");
        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + "," + pageSize);
        }

        try (Connection con = JDBCDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(populateDTO(rs));
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception in StaffMember list: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<StaffMemberDTO> search(StaffMemberDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List<StaffMemberDTO> search(StaffMemberDTO dto, int pageNo, int pageSize) throws ApplicationException {
        List<StaffMemberDTO> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT * FROM ST_STAFF_MEMBER WHERE 1=1");

        if (dto != null) {
            if (dto.getId() > 0) sql.append(" AND ID=" + dto.getId());
            if (dto.getFullName() != null && !dto.getFullName().isEmpty())
                sql.append(" AND FULL_NAME LIKE '" + dto.getFullName() + "%'");
            if (dto.getDivision() != null && !dto.getDivision().isEmpty())
                sql.append(" AND DIVISION LIKE '" + dto.getDivision() + "%'");
            if (dto.getJoiningDate() != null)
                sql.append(" AND JOINING_DATE='" + new java.sql.Date(dto.getJoiningDate().getTime()) + "'");
            if (dto.getPreviousEmployeeId() > 0)
                sql.append(" AND PREVIOUS_EMPLOYEE_ID=" + dto.getPreviousEmployeeId());
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + "," + pageSize);
        }

        try (Connection con = JDBCDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(populateDTO(rs));
            }

        } catch (Exception e) {
            throw new ApplicationException("Exception in StaffMember search: " + e.getMessage());
        }

        return list;
    }

    // Helper method to populate DTO from ResultSet
    private StaffMemberDTO populateDTO(ResultSet rs) throws Exception {
        StaffMemberDTO dto = new StaffMemberDTO();
        dto.setId((long) rs.getInt("ID"));
        dto.setFullName(rs.getString("FULL_NAME"));
        dto.setJoiningDate(rs.getDate("JOINING_DATE"));
        dto.setDivision(rs.getString("DIVISION"));
        dto.setPreviousEmployeeId(rs.getInt("PREVIOUS_EMPLOYEE_ID"));
        dto.setCreatedBy(rs.getString("CREATED_BY"));
        dto.setModifiedBy(rs.getString("MODIFIED_BY"));
        dto.setCreatedDatetime(rs.getTimestamp("CREATED_DATETIME"));
        dto.setModifiedDatetime(rs.getTimestamp("MODIFIED_DATETIME"));
        return dto;
    }
}
