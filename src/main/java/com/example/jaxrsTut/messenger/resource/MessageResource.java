package com.example.jaxrsTut.messenger.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.jaxrsTut.messenger.beans.MessageFilterBean;
import com.example.jaxrsTut.messenger.model.Message;
import com.example.jaxrsTut.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class MessageResource {
	
	private MessageService messageService = new MessageService();

//	@GET
//	public List<Message> getMessages(@QueryParam("year") int year,
//									@QueryParam("start") int start,
//									@QueryParam("size") int size) {
//		if (year > 0)
//			return messageService.getAllMessagesForYear(year);
//		if (start >= 0 && size > 0)
//			return messageService.getAllMessagesPaginated(start, size);
//		return messageService.getAllMessages();
//	}
	
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean bean) {
		if (bean.getYear() > 0)
			return messageService.getAllMessagesForYear(bean.getYear());
		if (bean.getStart() >= 0 && bean.getSize() > 0)
			return messageService.getAllMessagesPaginated(bean.getStart(), bean.getStart());
		return messageService.getAllMessages();
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {
		Message message =  messageService.getMessage(messageId);
		message.addLink(getUrlForSelf(uriInfo, message), "self");
		message.addLink(getUrlForProfile(uriInfo, message), "profile");
		message.addLink(getUrlForComments(uriInfo, message), "comments");
		return message;
	}

	private String getUrlForComments(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build().toString();
	}

	private String getUrlForProfile(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder()
			.path(ProfileResource.class)
			.path(message.getAuthor())
			.build().toString();
	}
	
	private String getUrlForSelf(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder()
			.path(MessageResource.class)
			.path(Long.toString(message.getId()))
			.build().toString();
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(message.getId());
		URI uri = uriInfo.getAbsolutePathBuilder()
						 .path(newId)
						 .build();
		return Response.created(uri)
					   .entity(newMessage)
					   .build();
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	// @GET
	// @Path("/context")
	// public String getContext(@Context UriInfo uriInfo, @Context HttpHeaders
	// httpHeaders) {
	// String absPath = uriInfo.getAbsolutePath().toString();
	// String baseUri = uriInfo.getBaseUri().toString();
	// String pathParams = uriInfo.getPathParameters().toString();
	// return absPath + "/n" + baseUri + "/n" + pathParams;
	// }
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
