package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project_3.dto.QueueDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class QueueModelJDBCImpl implements QueueModelInt {

    // ================= NEXT PK =================
    public Integer nextPK() throws DatabaseException {

        int pk = 0;
        Connection con = null;

        try {
            con = JDBCDataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT MAX(ID) FROM ST_QUEUE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception : " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }

        return pk + 1;
    }

    // ================= ADD =================
    @Override
    public long add(QueueDTO dto)
            throws ApplicationException, DuplicateRecordException {

        QueueDTO existDto = findByCode(dto.getQueueCode());

        if (existDto != null) {
            throw new DuplicateRecordException("Queue Code already exists");
        }

        Connection con = null;
        long pk = 0;

        try {
            pk = nextPK();
            con = JDBCDataSource.getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ST_QUEUE VALUES(?,?,?,?,?,?,?,?,?)");

            ps.setLong(1, pk);
            ps.setString(2, dto.getQueueCode());
            ps.setString(3, dto.getQueueName());
            ps.setInt(4, dto.getQueueSize());
            ps.setString(5, dto.getQueueStatus());
            ps.setString(6, dto.getCreatedBy());
            ps.setString(7, dto.getModifiedBy());
            ps.setTimestamp(8, new Timestamp(dto.getCreatedDatetime().getTime()));
            ps.setTimestamp(9, new Timestamp(dto.getModifiedDatetime().getTime()));

            ps.executeUpdate();
            con.commit();
            ps.close();

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ex) {
            }
            throw new ApplicationException("Exception : " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }

        return pk;
    }

    // ================= UPDATE =================
    @Override
    public void update(QueueDTO dto)
            throws ApplicationException, DuplicateRecordException {

        QueueDTO existDto = findByCode(dto.getQueueCode());

        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Queue Code already exists");
        }

        Connection con = null;

        try {
            con = JDBCDataSource.getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ST_QUEUE SET "
                            + "QUEUE_CODE=?, "
                            + "QUEUE_NAME=?, "
                            + "QUEUE_SIZE=?, "
                            + "QUEUE_STATUS=?, "
                            + "CREATED_BY=?, "
                            + "MODIFIED_BY=?, "
                            + "CREATED_DATETIME=?, "
                            + "MODIFIED_DATETIME=? "
                            + "WHERE ID=?");

            ps.setString(1, dto.getQueueCode());
            ps.setString(2, dto.getQueueName());
            ps.setInt(3, dto.getQueueSize());
            ps.setString(4, dto.getQueueStatus());
            ps.setString(5, dto.getCreatedBy());
            ps.setString(6, dto.getModifiedBy());
            ps.setTimestamp(7, new Timestamp(dto.getCreatedDatetime().getTime()));
            ps.setTimestamp(8, new Timestamp(dto.getModifiedDatetime().getTime()));
            ps.setLong(9, dto.getId());

            ps.executeUpdate();
            con.commit();
            ps.close();

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ex) {
            }
            throw new ApplicationException("Exception : " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }
    }

    // ================= DELETE =================
    @Override
    public void delete(QueueDTO dto) throws ApplicationException {

        Connection con = null;

        try {
            con = JDBCDataSource.getConnection();
            PreparedStatement ps =
                    con.prepareStatement("DELETE FROM ST_QUEUE WHERE ID=?");

            ps.setLong(1, dto.getId());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }
    }

    // ================= FIND BY PK =================
    @Override
    public QueueDTO findByPK(long pk) throws ApplicationException {

        QueueDTO dto = null;
        Connection con = null;

        try {
            con = JDBCDataSource.getConnection();
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM ST_QUEUE WHERE ID=?");

            ps.setLong(1, pk);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dto = populateDTO(rs);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }

        return dto;
    }

    // ================= FIND BY CODE =================
    @Override
    public QueueDTO findByCode(String code)
            throws ApplicationException {

        QueueDTO dto = null;
        Connection con = null;

        try {
            con = JDBCDataSource.getConnection();
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM ST_QUEUE WHERE QUEUE_CODE=?");

            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dto = populateDTO(rs);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }

        return dto;
    }

    // ================= LIST =================
    @Override
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList list = new ArrayList();
        Connection con = null;

        try {
            con = JDBCDataSource.getConnection();
            StringBuffer sql = new StringBuffer("SELECT * FROM ST_QUEUE");

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                sql.append(" limit " + pageNo + "," + pageSize);
            }

            PreparedStatement ps =
                    con.prepareStatement(sql.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateDTO(rs));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }

        return list;
    }

    // ================= SEARCH =================
    @Override
    public List search(QueueDTO dto, int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList list = new ArrayList();
        Connection con = null;

        try {
            con = JDBCDataSource.getConnection();

            StringBuffer sql = new StringBuffer("SELECT * FROM ST_QUEUE WHERE 1=1");

            if (dto.getQueueName() != null && dto.getQueueName().length() > 0) {
                sql.append(" AND QUEUE_NAME like '" + dto.getQueueName() + "%'");
            }

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                sql.append(" limit " + pageNo + "," + pageSize);
            }

            PreparedStatement ps =
                    con.prepareStatement(sql.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateDTO(rs));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(con);
        }

        return list;
    }

    // ================= COMMON DTO METHOD =================
    private QueueDTO populateDTO(ResultSet rs) throws Exception {

        QueueDTO dto = new QueueDTO();

        dto.setId(rs.getLong(1));
        dto.setQueueCode(rs.getString(2));
        dto.setQueueName(rs.getString(3));
        dto.setQueueSize(rs.getInt(4));
        dto.setQueueStatus(rs.getString(5));
        dto.setCreatedBy(rs.getString(6));
        dto.setModifiedBy(rs.getString(7));
        dto.setCreatedDatetime(rs.getTimestamp(8));
        dto.setModifiedDatetime(rs.getTimestamp(9));

        return dto;
    }
}
