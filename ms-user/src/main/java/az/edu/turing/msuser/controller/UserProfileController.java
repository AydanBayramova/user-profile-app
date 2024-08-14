package az.edu.turing.msuser.controller;



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
    public UserDto addUser(@RequestBody UserDto user) {
        log.info("add user");
        return userService.save(user);
    }

    @GetMapping("/all")
    public Page<UserDto> getUsers(Pageable pageable) {
        log.info("get users with pagination");
        return userService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        log.info("get user by id");
        Optional<UserDto> byId = userService.getById(id);
        return byId.orElse(null);
    }

    @DeleteMapping("/all")
    public void deleteAllUsers() {
        log.info("delete all users");
        userService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        log.info("delete user by id");
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUserById(@PathVariable Long id, @RequestBody UserDto user) {
        log.info("update user by id");
        return userService.update(id, user);
    }

    @PostMapping("/{userId}/profiles")
    public ProfileDto addProfile(@PathVariable Long userId, @RequestBody ProfileDto profile) {
        log.info("add profile");
        return profileService.addProfile(userId, profile);
    }

    @GetMapping("{userId}/profiles")
    public Page<ProfileDto> getProfiles(@PathVariable Long userId, Pageable pageable) {
        log.info("get profiles");
        return profileService.getAllProfiles(userId, pageable);
    }

    @GetMapping("{userId}/profiles/{profileId}")
    public ProfileDto getProfileById(@PathVariable Long userId, @PathVariable Long profileId) {
        log.info("get profile by id");
        return profileService.getProfileById(userId, profileId);
    }
    @DeleteMapping("{userId}/profiles/{profileId}")
    public void deleteProfileById(@PathVariable Long userId, @PathVariable Long profileId) {
        log.info("delete profile by id");
        profileService.deleteProfileById(userId, profileId);
    }

    @PutMapping("{userId}/profiles/{profileId}")
    public ProfileDto updateProfileById(@PathVariable Long userId, @PathVariable Long profileId, @RequestBody ProfileDto profile) {
        log.info("update profile by id");
        return profileService.updateProfile(userId,profileId,profile);
    }





}
