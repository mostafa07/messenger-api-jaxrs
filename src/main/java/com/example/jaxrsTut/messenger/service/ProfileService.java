package com.example.jaxrsTut.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.jaxrsTut.messenger.database.DatabaseClass;
import com.example.jaxrsTut.messenger.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = DatabaseClass.getProfiles();

	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());
	}
	
//	public Profile getProfile(long id) {
//		return profiles.get(id);
//	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty() /*|| profile.getProfileName() == null*/)
			return null;
		
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
