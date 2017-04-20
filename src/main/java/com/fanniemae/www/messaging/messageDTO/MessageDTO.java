package com.fanniemae.www.messaging.messageDTO;

import java.sql.Date;
import java.util.List;

import com.fanniemae.www.messaging.commentDTO.CommentDTO;

public class MessageDTO {

	private long messageID;
	private String author;
	private String message;
	private Date date;
	private List<CommentDTO> comments;
	
	public long getMessageID() {
		return messageID;
	}
	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
}
