package polimi.provafinale.trickytickets.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import polimi.provafinale.trickytickets.util.PropertyReader;
import polimi.provafinale.trickytickets.bean.UserBean;
import polimi.provafinale.trickytickets.email.*;
import polimi.provafinale.trickytickets.exception.ApplicationException;
import polimi.provafinale.trickytickets.exception.DatabaseException;
import polimi.provafinale.trickytickets.exception.DuplicateRecordException;
import polimi.provafinale.trickytickets.exception.RecordNotFoundException;
import polimi.provafinale.trickytickets.util.JDBCDataSource;

/* Classe per la gestione della tabella Utenti del DB */

public class UserModel {


	public Integer nextPK() throws DatabaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM User");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			throw new DatabaseException("Impossibile recuperare nuovo ID");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	public long add(UserBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		UserBean existbean = findByUserName(bean.getUserName());

		if (existbean != null) {
			throw new DuplicateRecordException("Username non valida");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO User VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getUserName());
			pstmt.setString(4, bean.getPassword());
			pstmt.setString(5, bean.getEmail());
			pstmt.setString(6, bean.getContactNo());
			pstmt.setString(7, bean.getBusinessArea());
			pstmt.setLong(8,bean.getRoleId());
			pstmt.setString(9, bean.getRoleName());
			pstmt.setString(10, bean.getCreatedBy());
			pstmt.setString(11, bean.getModifiedBy());
			pstmt.setTimestamp(12, bean.getCreatedDatetime());
			pstmt.setTimestamp(13, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Errore nel rollback nuovo utente " + ex.getMessage());
			}
			throw new ApplicationException("Errore nella registrazione nuovo utente");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public UserBean findByUserName(String UserName) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM User WHERE USERNAME=?");
		UserBean bean = null;
		Connection conn = null;
	
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, UserName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setEmail(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setBusinessArea(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setRoleName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public UserBean findByPK(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM User WHERE ID=?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setEmail(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setBusinessArea(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setRoleName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public void update(UserBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		UserBean beanExist = findByUserName(bean.getUserName());
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("Username già esistente");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Inizio transazione
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE User SET NAME=?,USERNAME=?,PASSWORD=?,EMAIL=?,CONTACTNO=?,BUSINESSAREA=?,roleid=?,roleName=?,"
							+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getUserName());
			pstmt.setString(3, bean.getPassword());
			pstmt.setString(4, bean.getEmail());
			pstmt.setString(5, bean.getContactNo());
			pstmt.setString(6, bean.getBusinessArea());
			pstmt.setLong(7,bean.getRoleId());
			pstmt.setString(8, bean.getRoleName());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());
			pstmt.setLong(13, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // Fine transazione
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public List<UserBean> search(UserBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List<UserBean> search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM User WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			
			if (bean.getRoleId() > 0) {
				sql.append(" AND RoleId = " + bean.getRoleId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}

			if (bean.getUserName() != null && bean.getUserName().length() > 0) {
				sql.append(" AND USERNAME like '" + bean.getUserName() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND Email like '" + bean.getEmail()+"'");
			}

			if (bean.getContactNo() != null && bean.getContactNo().length() > 0) {
				sql.append(" AND CONTACT_NO = " + bean.getContactNo());
			}
			

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList<UserBean> list = new ArrayList<UserBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setEmail(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setBusinessArea(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setRoleName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Errore nella ricerca utenti");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public List<UserBean> list() throws ApplicationException {
		return list(0, 0);
	}

	public List<UserBean> list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		StringBuffer sql = new StringBuffer("select * from User");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				UserBean bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setEmail(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setBusinessArea(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setRoleName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Errore nel creare lista di utenti");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}

	public UserBean authenticate(String UserName, String password) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM User WHERE USERNAME = ? AND PASSWORD = ?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, UserName);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setEmail(rs.getString(5));
				bean.setContactNo(rs.getString(6));
				bean.setBusinessArea(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setRoleName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Errore nell' autenticazione");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {

		boolean flag = false;

		UserBean beanExist = null;

		beanExist = findByPK(id);

		if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
			beanExist.setPassword(newPassword);
			try {
				update(beanExist);
			} catch (DuplicateRecordException e) {
				throw new ApplicationException("Username già in uso");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Vecchia Password non valida");
		}
		return flag;

	}

	public long registerUser(UserBean bean) throws ApplicationException, DuplicateRecordException {

		long pk = add(bean);

		return pk;
	}

	public String forgetPassword(String login)
			throws ApplicationException, RecordNotFoundException {
		
		UserBean UserData = findByUserName(login);
	
		if (UserData == null) {
			throw new RecordNotFoundException("Username inesistente");
		}		
	
		String host = PropertyReader.getSmtp();
		String port = PropertyReader.getPort();
		String user = PropertyReader.getLogin();
		String pass = PropertyReader.getPwd();
		String dest = UserData.getEmail();
		String pwd = UserData.getPassword();
		String subj = "Recupero password Tricky Tickets";
		
        try {
        	System.out.println(host+" "+port+" "+user+" "+pass+" "+dest+" "+subj+" "+pwd);
            EmailUtility.sendEmail(host, port, user, pass, dest, subj, pwd);
    
        } catch (Exception ex) {
            ex.printStackTrace();
             }
	
		return "Password inviata all'indirizzo: "+UserData.getEmail() ;
	}
	
	public void delete(UserBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM User WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Errore nel rollback cancellazione utente" + ex.getMessage());
			}
			throw new ApplicationException("Errore nella cancellazione utente");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
}
