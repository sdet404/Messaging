package com.fanniemae.www.messaging.commentDAO;

import java.sql.SQLException;
import java.util.List;

import com.fanniemae.www.messaging.commentDTO.CommentDTO;

public interface ICommentDAO {
	public CommentDTO create(CommentDTO comment) throws ClassNotFoundException, SQLException;
	public CommentDTO read(long commentID) throws SQLException, ClassNotFoundException;
	public List<CommentDTO> readAll(long messageID) throws ClassNotFoundException, SQLException;
	public String delete(long commentID) throws ClassNotFoundException, SQLException;
}
