package com.example.model.service;

import com.example.exceptions.ProfileNotFoundException;
import com.example.model.beans.Profile;

import java.util.List;

public interface ProfileService {

    public Profile storeProfile(Profile profile);

    public List<Profile> fetchProfiles();

    public Profile fetchProfile(int id) throws ProfileNotFoundException;
}
