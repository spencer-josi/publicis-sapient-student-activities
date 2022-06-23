package com.example.model.dao;

import com.example.model.beans.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    @Query("select friend from Friend f where profileIdRef = ?1")
    public List<Friend> getFriendsFromProfile(int profileId);
}
