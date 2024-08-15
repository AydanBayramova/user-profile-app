package az.edu.turing.msuser.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import az.edu.turing.msuser.model.dto.ProfileDto;
import az.edu.turing.msuser.model.dto.UserDto;
import az.edu.turing.msuser.service.ProfileService;
import az.edu.turing.msuser.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j

public class UserProfileController {

    private final UserService userService;
    private final ProfileService profileService;


    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
        log.info("Request to add a new user: {}", user);
        UserDto savedUser = userService.save(user);
        log.info("User successfully added with ID: {}", savedUser.getId());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> getUsers(Pageable pageable) {
        log.info("Request to retrieve all users with pagination: {}", pageable);
        Page<UserDto> users = userService.getAll(pageable);
        log.info("Users retrieved successfully. Total users: {}", users.getTotalElements());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        log.info("Request to retrieve user with ID: {}", id);
        Optional<UserDto> byId = userService.getById(id);
        if (byId.isPresent()) {
            log.info("User retrieved successfully: {}", byId.get());
            return new ResponseEntity<>(byId.get(), HttpStatus.OK);
        } else {
            log.warn("User with ID: {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllUsers() {
        log.info("Request to delete all users");
        userService.deleteAll();
        log.info("All users successfully deleted");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        log.info("Request to delete user with ID: {}", id);
        userService.deleteById(id);
        log.info("User with ID: {} successfully deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable Long id, @RequestBody UserDto user) {
        log.info("Request to update user with ID: {}. New details: {}", id, user);
        UserDto updatedUser = userService.update(id, user);
        log.info("User with ID: {} successfully updated", id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/{userId}/profiles")
    public ResponseEntity<ProfileDto> addProfile(@PathVariable Long userId, @RequestBody ProfileDto profile) {
        log.info("Request to add profile for user with ID: {}. Profile details: {}", userId, profile);
        ProfileDto addedProfile = profileService.addProfile(userId, profile);
        log.info("Profile successfully added with ID: {}", addedProfile.getId());
        return new ResponseEntity<>(addedProfile, HttpStatus.CREATED);
    }

    @GetMapping("{userId}/profiles")
    public ResponseEntity<Page<ProfileDto>> getProfiles(@PathVariable Long userId, Pageable pageable) {
        log.info("Request to retrieve profiles for user with ID: {}. Pagination details: {}", userId, pageable);
        Page<ProfileDto> profiles = profileService.getAllProfiles(userId, pageable);
        log.info("Profiles retrieved successfully. Total profiles: {}", profiles.getTotalElements());
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping("{userId}/profiles/{profileId}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable Long userId, @PathVariable Long profileId) {
        log.info("Request to retrieve profile with ID: {} for user with ID: {}", profileId, userId);
        ProfileDto profile = profileService.getProfileById(userId, profileId);
        if (profile != null) {
            log.info("Profile retrieved successfully: {}", profile);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            log.warn("Profile with ID: {} not found for user with ID: {}", profileId, userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{userId}/profiles/{profileId}")
    public ResponseEntity<Void> deleteProfileById(@PathVariable Long userId, @PathVariable Long profileId) {
        log.info("Request to delete profile with ID: {} for user with ID: {}", profileId, userId);
        profileService.deleteProfileById(userId, profileId);
        log.info("Profile with ID: {} successfully deleted for user with ID: {}", profileId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{userId}/profiles/{profileId}")
    public ResponseEntity<ProfileDto> updateProfileById(@PathVariable Long userId, @PathVariable Long profileId, @RequestBody ProfileDto profile) {
        log.info("Request to update profile with ID: {} for user with ID: {}. New details: {}", profileId, userId, profile);
        ProfileDto updatedProfile = profileService.updateProfile(userId, profileId, profile);
        log.info("Profile with ID: {} successfully updated for user with ID: {}", profileId, userId);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }
}