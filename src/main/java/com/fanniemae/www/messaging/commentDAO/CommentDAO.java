package com.fanniemae.www.messaging.commentDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.fanniemae.www.messaging.commentDTO.CommentDTO;

public class CommentDAO implements ICommentDAO {

	public CommentDTO create(CommentDTO comment) throws ClassNotFoundException, SQLException {
		long cmntID = 0L;
		String cmntrName = comment.getCommentorName();
		String cmnt = comment.getComment();
		long msgID = comment.getMessageID();
		String insertQuery = "INSERT INTO SDET404.CMNT VALUES (CMNT_ID_SEQ.NEXTVAL,?,?,?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
			stmt = conn.prepareStatement(insertQuery, new String[] { "CMNT_ID" });
			stmt.setString(1, cmntrName);
			stmt.setString(2, cmnt);
			stmt.setLong(3, msgID);
			if (stmt.executeUpdate() > 0) {
				ResultSet genKeys = stmt.getGeneratedKeys();
				if (genKeys.next())
					cmntID = genKeys.getLong(1);
				comment.setCommentID(cmntID);
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			return null;
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
		}
		return comment;
	}

	public CommentDTO read(long commentID) throws SQLException, ClassNotFoundException {
		String commentorName = null;
		String cmnt = null;
		long msgID=0L;
		String selectQuery = "SELECT * FROM SDET404.CMNT WHERE CMNT_ID=?";
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean hasResult = false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
			stmt = conn.prepareStatement(selectQuery);
			stmt.setLong(1, commentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				hasResult = true;
				commentID = rs.getLong("CMNT_ID");
				commentorName = rs.getString("CMNTR_NME");
				cmnt = rs.getString("CMNT");
				msgID = rs.getLong("MSG_ID");
			}
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
		}
		if(!hasResult)
			return null;
		CommentDTO comment = new CommentDTO();
		comment.setCommentID(commentID);
		comment.setCommentorName(commentorName);
		comment.setComment(cmnt);
		comment.setMessageID(msgID);
		return comment;
	}

	public List<CommentDTO> readAll(long messageID) throws ClassNotFoundException, SQLException {
		List<CommentDTO> comments = new ArrayList<CommentDTO>();
		Connection conn = null;
		PreparedStatement stmt = null;
		String selectQuery = "SELECT * FROM SDET404.CMNT WHERE MSG_ID=?";
		boolean hasResult = false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
			stmt = conn.prepareStatement(selectQuery);
			stmt.setLong(1, messageID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				hasResult = true;
				CommentDTO comment = new CommentDTO();
				comment.setCommentID(rs.getLong("CMNT_ID"));
				comment.setCommentorName(rs.getString("CMNTR_NME"));
				comment.setComment(rs.getString("CMNT"));
				comment.setMessageID(rs.getLong("MSG_ID"));
				comments.add(comment);
			}
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
		}
		if(!hasResult)
			return null;
		return comments;
	}

	public String delete(long commentID) throws ClassNotFoundException, SQLException {
		String deleteQuery = "DELETE FROM SDET404.CMNT WHERE CMNT_ID=?";
		Connection conn = null;
		PreparedStatement stmt = null;
		int successCode;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
			stmt = conn.prepareStatement(deleteQuery);
			stmt.setLong(1, commentID);
			successCode = stmt.executeUpdate();
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
		}
		if (successCode > 0)
			return "Comment #" + commentID + " was successfully deleted.";
		else
			return "ERROR: Comment #" + commentID + " was not deleted.";
	}
}

// TODO Comments can be created that are not attributable to any message