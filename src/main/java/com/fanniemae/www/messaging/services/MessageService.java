package com.fanniemae.www.messaging.services;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.fanniemae.www.messaging.messageDAO.IMessageDAO;
import com.fanniemae.www.messaging.messageDAO.MessageDAO;
import com.fanniemae.www.messaging.messageDTO.MessageDTO;

@Path("/messages")
public class MessageService {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MessageDTO create(MessageDTO message) throws ClassNotFoundException, SQLException {
		IMessageDAO MessageDAO = new MessageDAO();
		return MessageDAO.create(message);
	}

	@GET
	@Path("/{messageID}")
	@Produces(MediaType.APPLICATION_JSON)
	public MessageDTO read(@PathParam("messageID") long messageID) throws SQLException, ClassNotFoundException {
		IMessageDAO MessageDAO = new MessageDAO();
		return MessageDAO.read(messageID);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MessageDTO update(MessageDTO message) throws ClassNotFoundException, SQLException {
		IMessageDAO messageDAO = new MessageDAO();
		return messageDAO.update(message);
	}

	@DELETE
	@Path("{messageID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("messageID") long messageID)
			throws ClassNotFoundException, SQLException, JSONException {
		IMessageDAO MessageDAO = new MessageDAO();
		String message = MessageDAO.delete(messageID);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", message);
		return Response.status(200).entity(jsonObject.toString()).build();
	}
}
