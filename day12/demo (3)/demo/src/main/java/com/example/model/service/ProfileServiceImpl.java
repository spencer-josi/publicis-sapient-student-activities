package com.example.model.service;

import com.example.exceptions.ProfileNotFoundException;
import com.example.model.beans.Friend;
import com.example.model.beans.Profile;
import com.example.model.dao.FriendRepository;
import com.example.model.dao.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileDao;

    private FriendRepository friendDao;
    @Override
    public Profile storeProfile(Profile profile) {
        Profile pro = profileDao.save(profile);
        return pro;
    }

    @Override
    public List<Profile> fetchProfiles() {
        return profileDao.findAll();
    }

    @Override
    public Profile fetchProfile(int id) throws ProfileNotFoundException {
        Profile pro = profileDao.findById(id).orElse(null);
        if (pro == null) {
            throw new ProfileNotFoundException("Profile not found, fool");
        }
        List<Friend> friends = friendDao.getFriendsFromProfile(id);
        pro.setFriends(friends);
        return pro;
    }
}
