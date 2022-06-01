package polimi.provafinale.trickytickets.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import polimi.provafinale.trickytickets.bean.CommentBean;
import polimi.provafinale.trickytickets.bean.TicketBean;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.exception.DatabaseException;
import polimi.provafinale.trickytickets.exception.DuplicateRecordException;
import polimi.provafinale.trickytickets.util.JDBCDataSource;

/* Classe per la gestione della tabella Commenti del DB */

public class CommentModel {

	public Integer nextPK() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM Comment");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			throw new DatabaseException("Errore nel trovare nuovo ID Commento");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}
	
	public long add(CommentBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

			TicketBean cBean=new TicketModel().findByPK(bean.getTicketId());
		
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO Comment VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2,cBean.getTicketNo());
			pstmt.setLong(3, cBean.getId());
			pstmt.setString(4,cBean.getTitle());
			pstmt.setString(5, bean.getUserName());
			pstmt.setDate(6, new java.sql.Date(new Date().getTime()));
			pstmt.setString(7, bean.getComment());
			pstmt.setString(8,bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Errore nel rollback nuovo commento" + ex.getMessage());
			}
			throw new ApplicationException("Errore nel creare nuovo commento");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}
	
	public List<CommentBean> search(CommentBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List<CommentBean> search(CommentBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM Comment WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getTicketId() > 0) {
				sql.append(" AND TicketId = " + bean.getTicketId());
			}
			
			
			if (bean.getTicketNo() > 0) {
				sql.append(" AND TicketNo = " + bean.getTicketNo());
			}
			
			if (bean.getTicketTitle() != null && bean.getTicketTitle().length() > 0) {
				sql.append(" AND TicketTitle like '" + bean.getTicketTitle() + "%'");
			}
			
			
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList<CommentBean> list = new ArrayList<CommentBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CommentBean();
				bean.setId(rs.getLong(1));
				bean.setTicketNo(rs.getLong(2));
				bean.setTicketId(rs.getLong(3));
				bean.setTicketTitle(rs.getString(4));
				bean.setUserName(rs.getString(5));
				bean.setDate(rs.getDate(6));
				bean.setComment(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Errore nella ricerca commenti");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public void delete(CommentBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Comment WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Errore nel rollback eliminazione commento" + ex.getMessage());
			}
			throw new ApplicationException("Errore nell'eliminazione commento");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
}
