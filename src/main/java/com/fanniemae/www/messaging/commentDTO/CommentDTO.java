package com.fanniemae.www.messaging.commentDTO;

public class CommentDTO {
	
	private long commentID;
	private long messageID;
	private String commentorName;
	private String comment;
	
	public long getMessageID() {
		return messageID;
	}
	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}
	public long getCommentID() {
		return commentID;
	}
	public void setCommentID(long commentID) {
		this.commentID = commentID;
	}
	public String getCommentorName() {
		return commentorName;
	}
	public void setCommentorName(String commentorName) {
		this.commentorName = commentorName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
