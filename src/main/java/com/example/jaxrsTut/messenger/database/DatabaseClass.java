package com.example.jaxrsTut.messenger.database;

import java.util.HashMap;
import java.util.Map;

import com.example.jaxrsTut.messenger.model.Comment;
import com.example.jaxrsTut.messenger.model.Message;
import com.example.jaxrsTut.messenger.model.Profile;

// Database Simulation
public class DatabaseClass {

	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();

	static {
		messages.put(1L, new Message(1L, "hello, world", "Mostafa"));
		messages.put(2L, new Message(2L, "hello, jersey", "Sherif"));
		
//		Comment comment = new Comment(11L, "hello, comment", "sherif");
//		messages.get(1).getComments().put(1L, comment);
//		messages.get(1).getComments().put(2L, comment);
//		messages.get(2).getComments().put(10L, comment);

		profiles.put("mostafa", new Profile(1L, "mostafa", "Mostafa", "Mahmoud"));
		profiles.put("sherif", new Profile(1L, "sherif", "Sherif", "Hossam"));
	}
	
	public static Map<Long, Message> getMessages() {
		return messages;
	}

	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
}
