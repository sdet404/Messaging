package com.fanniemae.www.messaging.messageDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.fanniemae.www.messaging.commentDTO.CommentDTO;
import com.fanniemae.www.messaging.messageDTO.MessageDTO;

public class MessageDAO implements IMessageDAO {

	public MessageDTO create(MessageDTO message) throws ClassNotFoundException, SQLException {
		long msgID = 0L;
		List<CommentDTO> msgComments = message.getComments();
		String msgAuthor = message.getAuthor();
		Date msgDate = message.getDate();
		String msgMessage = message.getMessage();
		String insertQuery = "INSERT INTO SDET404.MSG VALUES (MSG_ID_SEQ.NEXTVAL,?,?,?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
			stmt = conn.prepareStatement(insertQuery, new String[] { "MSG_ID" });
			stmt.setString(1, msgMessage);
			stmt.setString(2, msgAuthor);
			stmt.setDate(3, msgDate);
			if (stmt.executeUpdate() > 0) {
				ResultSet genKeys = stmt.getGeneratedKeys();
				if (genKeys != null && genKeys.next())
					msgID = genKeys.getLong(1);
				message.setMessageID(msgID);
			}
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
		}
		for (CommentDTO comment : msgComments) {
			String cmntrName = comment.getCommentorName();
			String cmnt = comment.getComment();
			msgID = message.getMessageID();
			long cmntID = 0L;
			insertQuery = "INSERT INTO SDET404.CMNT VALUES (CMNT_ID_SEQ.NEXTVAL,?,?,?)";
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
				stmt = conn.prepareStatement(insertQuery, new String[] { "CMNT_ID" });
				stmt.setString(1, cmntrName);
				stmt.setString(2, cmnt);
				stmt.setLong(3, msgID);
				if (stmt.executeUpdate() > 0) {
					ResultSet genKeys = stmt.getGeneratedKeys();
					if (genKeys != null && genKeys.next())
						cmntID = genKeys.getLong(1);
					comment.setCommentID(cmntID);
				}
			} finally {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
			}
		}
		message.setComments(msgComments);
		return message;
	}

	public MessageDTO update(MessageDTO message) throws SQLException, ClassNotFoundException {
		String author = message.getAuthor();
		List<CommentDTO> comments = message.getComments();
		Date date = message.getDate();
		String msg = message.getMessage();
		long msgID = message.getMessageID();
		String updateQuery = "UPDATE SDET404.MSG SET MSG= ?, AUTHOR=?, DT=?  WHERE MSG_ID=?";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
			stmt = conn.prepareStatement(updateQuery);
			stmt.setString(1, msg);
			stmt.setString(2, author);
			stmt.setDate(3, date);
			stmt.setLong(4, msgID);
			if (stmt.executeUpdate() == 0)
				return null;
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
		}

		Iterator<CommentDTO> it = comments.iterator();
		while (it.hasNext()) {
			CommentDTO comment = it.next();
			String cmnt = comment.getComment();
			long commentID = comment.getCommentID();
			String commentorName = comment.getCommentorName();
			updateQuery = "UPDATE SDET404.CMNT SET CMNTR_NME=?, CMNT=?, MSG_ID=? WHERE CMNT_ID=?";
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
				stmt = conn.prepareStatement(updateQuery);
				stmt.setString(1, commentorName);
				stmt.setString(2, cmnt);
				stmt.setLong(3, msgID);
				stmt.setLong(4, commentID);
				if (stmt.executeUpdate() <= 0) {
					it.remove();
				}
			} finally {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
			}
		}
		message.setComments(comments);
		return message;
	}

	public MessageDTO read(long messageID) throws SQLException, ClassNotFoundException {
		MessageDTO message = new MessageDTO();
		String selectQuery = "SELECT * FROM SDET404.MSG WHERE MSG_ID=?";
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean hasResult = false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
			stmt = conn.prepareStatement(selectQuery);
			stmt.setLong(1, messageID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				hasResult = true;
				message.setMessageID(rs.getLong(1));
				message.setMessage(rs.getString(2));
				message.setAuthor(rs.getString(3));
				message.setDate(rs.getDate(4));
			}
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
		}
		if (!hasResult)
			return null;

		List<CommentDTO> comments = new ArrayList<CommentDTO>();
		selectQuery = "SELECT * FROM SDET404.CMNT WHERE MSG_ID=?";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
			stmt = conn.prepareStatement(selectQuery);
			stmt.setLong(1, messageID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
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
		message.setComments(comments);
		return message;
	}

	public String delete(long messageID) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		String deleteQuery = "DELETE FROM SDET404.CMNT WHERE MSG_ID=?";
		// delete from CMNT table first to avoid integrity constraint violations
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
			stmt = conn.prepareStatement(deleteQuery);
			stmt.setLong(1, messageID);
			stmt.executeUpdate();
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
		}
		deleteQuery = "DELETE FROM SDET404.MSG WHERE MSG_ID=?";
		int successCode;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sdet404", "password");
			stmt = conn.prepareStatement(deleteQuery);
			stmt.setLong(1, messageID);
			successCode = stmt.executeUpdate();
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
		}
		if (successCode > 0)
			return "Message ID #" + messageID + " was successfully deleted.";
		else
			return "ERROR: Message ID #" + messageID + " was not deleted.";
	}
	
}
