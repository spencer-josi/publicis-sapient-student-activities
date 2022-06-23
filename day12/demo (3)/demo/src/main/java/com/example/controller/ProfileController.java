package com.example.controller;

import com.example.exceptions.ProfileNotFoundException;
import com.example.model.beans.Friend;
import com.example.model.beans.Profile;
import com.example.model.service.FriendService;
import com.example.model.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FriendService friendService;

    @PostMapping
    public ResponseEntity<Object> store(@RequestBody Profile profile) {
        Profile storedBody = profileService.storeProfile(profile);
        return ResponseEntity.status(HttpStatus.OK).body(storedBody);
    }

    @GetMapping
    public ResponseEntity<Object> getProfiles() {
        List<Profile> list = profileService.fetchProfiles();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProfile(@PathVariable("id") int id) {
        try {
            Profile profile = profileService.fetchProfile(id);
            return ResponseEntity.status(HttpStatus.OK).body(profile);
        } catch (ProfileNotFoundException e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("error", e.getMessage());
            map.put("status", "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
    }

    @PostMapping("/{profileId}")
    public ResponseEntity<Object> addFriend(@RequestBody Friend friend,
                                            @PathVariable int id) {
        Friend createdFriend = friendService.addFriend(id, friend);
        Map<String, String> map = new HashMap<>();
        map.put("message", "created and added friend: " + createdFriend.getName());
        map.put("profileId", "Profile ID: " + createdFriend.getProfileIdRef());
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PutMapping("/{profileId}/{phone}")
    public ResponseEntity<Object> updatePhone(@PathVariable int id,
                                               @PathVariable long phone) throws ProfileNotFoundException {
        try {
            Profile p = profileService.fetchProfile(id);
            p.setPhone(phone);
            return ResponseEntity.status(HttpStatus.OK).body(p);
        }
        catch(ProfileNotFoundException e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("error", e.getMessage());
            map.put("status", "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
    }

    @PutMapping("/{profileId}/{friendId/{phone}")
    public ResponseEntity<Object> updateFriendPhone(@PathVariable int profileId,
                                                    @PathVariable int friendId,
                                                    @PathVariable long phone) {
        try {
            Profile p = profileService.fetchProfile(profileId);
            for (Friend f : p.getFriends()) {
                if (f.getProfileIdRef() == friendId) {
                    f.setPhone(phone);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(phone);

        } catch(ProfileNotFoundException e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("error", e.getMessage());
            map.put("status", "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);

        }
    }


    @DeleteMapping("/{profileId}/{friendId/")
    public ResponseEntity<Object> deleteFriend(@PathVariable int profileId,
                                                    @PathVariable int friendId) {
        try {
            Profile p = profileService.fetchProfile(profileId);
            for (Friend f : p.getFriends()) {
                if (f.getProfileIdRef() == friendId) {
                    p.getFriends().remove(f);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(friendId);

        } catch(ProfileNotFoundException e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("error", e.getMessage());
            map.put("status", "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);

        }
    }
}
