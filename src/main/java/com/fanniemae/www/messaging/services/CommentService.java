package com.fanniemae.www.messaging.services;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.fanniemae.www.messaging.commentDAO.CommentDAO;
import com.fanniemae.www.messaging.commentDAO.ICommentDAO;
import com.fanniemae.www.messaging.commentDTO.CommentDTO;

@Path("/comments")
public class CommentService {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CommentDTO create(CommentDTO comment) throws ClassNotFoundException, SQLException{
		ICommentDAO commentsDAO = new CommentDAO();
		return commentsDAO.create(comment);
	}
	
	@GET
	@Path("/{commentID}")
	@Produces(MediaType.APPLICATION_JSON)
	public CommentDTO read(@PathParam("commentID")long commentID) throws SQLException, ClassNotFoundException{
		ICommentDAO commentsDAO = new CommentDAO();
		return commentsDAO.read(commentID);
	}
	
	@GET
	@Path("readAll/{messageID}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CommentDTO> readAll(@PathParam("messageID")long messageID) throws ClassNotFoundException, SQLException{
		ICommentDAO commentsDAO = new CommentDAO();
		return commentsDAO.readAll(messageID);
	}
	
	@DELETE
	@Path("{commentID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("commentID")long commentID) throws ClassNotFoundException, SQLException, JSONException{
		ICommentDAO commentsDAO = new CommentDAO();
		String message = commentsDAO.delete(commentID);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", message);
		return Response.status(200).entity(jsonObject.toString()).build();
	}
}
