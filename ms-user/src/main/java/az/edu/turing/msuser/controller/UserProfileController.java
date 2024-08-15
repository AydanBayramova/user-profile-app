package az.edu.turing.msuser.controller;

import az.edu.turing.msuser.model.dto.ProfileDto;
import az.edu.turing.msuser.model.dto.UserDto;
import az.edu.turing.msuser.service.ProfileService;
import az.edu.turing.msuser.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        log.info("Request to add new user: {}", user);
        UserDto savedUser = userService.save(user);
        log.info("User added successfully with ID: {}", savedUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }

    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> getUsers(Pageable pageable) {
        log.info("Request to get users with pagination");
        Page<UserDto> usersPage = userService.getAll(pageable);
        log.info("Users retrieved successfully, total users: {}", usersPage.getTotalElements());
        return ResponseEntity.ok(usersPage);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        log.info("Request to get user by ID: {}", id);
        Optional<UserDto> userDto = userService.getById(id);
        if (userDto.isPresent()) {
            log.info("User with ID: {} found", id);
            return userDto.get();
        } else {
            log.warn("User with ID: {} not found", id);
            return null;

        }
    }

    @DeleteMapping("/all")
    public void deleteAllUsers() {
        log.info("Request to delete all users");
        userService.deleteAll();
        log.info("All users deleted successfully");
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        log.info("Request to delete user by ID: {}", id);
        userService.deleteById(id);
        log.info("User with ID: {} deleted successfully", id);
    }

    @PutMapping("/{id}")
    public UserDto updateUserById(@PathVariable Long id, @RequestBody UserDto user) {
        log.info("Request to update user by ID: {}", id);
        UserDto updatedUser = userService.update(id, user);
        log.info("User with ID: {} updated successfully", id);
        return updatedUser;
    }

    @PostMapping("/{userId}/profiles")
    public ProfileDto addProfile(@Valid @PathVariable Long userId, @RequestBody ProfileDto profile) {
        log.info("Request to add new profile for user ID: {}", userId);
        ProfileDto savedProfile = profileService.addProfile(userId, profile);
        log.info("Profile added successfully for user ID: {} with profile ID: {}", userId, savedProfile.getId());
        return savedProfile;
    }

    @GetMapping("{userId}/profiles")
    public Page<ProfileDto> getProfiles(@PathVariable Long userId, Pageable pageable) {
        log.info("Request to get profiles for user ID: {}", userId);
        Page<ProfileDto> profilesPage = profileService.getAllProfiles(userId, pageable);
        log.info("Profiles retrieved successfully for user ID: {}, total profiles: {}", userId, profilesPage.getTotalElements());
        return profilesPage;
    }

    @GetMapping("{userId}/profiles/{profileId}")
    public ProfileDto getProfileById(@PathVariable Long userId, @PathVariable Long profileId) {
        log.info("Request to get profile by ID: {} for user ID: {}", profileId, userId);
        ProfileDto profileDto = profileService.getProfileById(userId, profileId);
        log.info("Profile with ID: {} for user ID: {} retrieved successfully", profileId, userId);
        return profileDto;
    }

    @DeleteMapping("{userId}/profiles/{profileId}")
    public void deleteProfileById(@PathVariable Long userId, @PathVariable Long profileId) {
        log.info("Request to delete profile by ID: {} for user ID: {}", profileId, userId);
        profileService.deleteProfileById(userId, profileId);
        log.info("Profile with ID: {} for user ID: {} deleted successfully", profileId, userId);
    }

    @PutMapping("{userId}/profiles/{profileId}")
    public ProfileDto updateProfileById(@PathVariable Long userId, @PathVariable Long profileId, @RequestBody ProfileDto profile) {
        log.info("Request to update profile by ID: {} for user ID: {}", profileId, userId);
        ProfileDto updatedProfile = profileService.updateProfile(userId, profileId, profile);
        log.info("Profile with ID: {} for user ID: {} updated successfully", profileId, userId);
        return updatedProfile;
    }
}

