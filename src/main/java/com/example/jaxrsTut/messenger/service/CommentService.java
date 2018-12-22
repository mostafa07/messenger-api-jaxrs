package com.example.jaxrsTut.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.jaxrsTut.messenger.model.Message;
import com.example.jaxrsTut.messenger.database.DatabaseClass;
import com.example.jaxrsTut.messenger.model.Comment;
import com.example.jaxrsTut.messenger.model.ErrorMessage;

public class CommentService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public List<Comment> getAllComments(long messageId) {
		 Map<Long, Comment> comments = messages.get(messageId).getComments();
		 return new ArrayList<Comment>(comments.values());
	}

	public Comment getComment(long messageId, long commentId) {
		ErrorMessage errorMessage = new ErrorMessage("Not found", "https://www.google.com/", 404);
		Response response =  Response.status(Status.NOT_FOUND)
									.entity(errorMessage)
									.build();
		
		Message message = messages.get(messageId);
		if (message == null)
			throw new WebApplicationException(response);
		Comment comment = message.getComments().get(commentId);
		if (comment == null)
			throw new WebApplicationException(response);
		return comment;
	}

	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		long newId = comments.size() + 1;
		comment.setId(newId);
		comments.put(newId, comment);
		return comment;
	}

	public Comment updateComment(long messageId, Comment comment) {
		if (comment.getId() <= 0)
			return null;
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comments.put(comment.getId(), comment);
		return comment;
	}

	public void removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comments.remove(commentId);
	}
}
