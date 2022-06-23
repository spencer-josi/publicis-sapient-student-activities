package com.example.model.service;

import com.example.model.beans.Friend;
import com.example.model.dao.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendDao;

    @Override
    @Transactional
    public Friend addFriend(int profileIdRef, Friend friend) {
        friend.setProfileIdRef(profileIdRef);
        return friendDao.save(friend);
    }
}
