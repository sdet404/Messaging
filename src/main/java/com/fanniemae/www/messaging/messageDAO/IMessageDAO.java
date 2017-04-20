package com.fanniemae.www.messaging.messageDAO;

import java.sql.SQLException;

import com.fanniemae.www.messaging.messageDTO.MessageDTO;

public interface IMessageDAO {
	public MessageDTO create(MessageDTO message) throws ClassNotFoundException, SQLException;
	public MessageDTO update(MessageDTO message) throws SQLException, ClassNotFoundException;
	public MessageDTO read(long messageID) throws SQLException, ClassNotFoundException;
	public String delete(long messageID) throws ClassNotFoundException, SQLException;
}
