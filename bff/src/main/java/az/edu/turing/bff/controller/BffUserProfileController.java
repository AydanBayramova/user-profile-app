package az.edu.turing.bff.controller;

import az.edu.turing.bff.dto.ProfileDto;
import az.edu.turing.bff.dto.UserDto;
import az.edu.turing.bff.service.BffUserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bff/users")
public class BffUserProfileController {

    private final BffUserProfileService userProfileService;

    public BffUserProfileController(BffUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userProfileService.getUserById(userId);
    }

    @GetMapping("/by-age")
    public List<UserDto> getAllUsersByAge(@RequestParam int age) {
        return userProfileService.getAllUsersByAge(age);
    }

    @GetMapping("/profiles/by-age")
    public List<ProfileDto> getAllProfilesByAge(@RequestParam int age) {
        return userProfileService.getAllProfilesByAge(age);
    }

    @GetMapping("/{userId}/profiles/{profileId}")
    public ProfileDto getProfileById(@PathVariable Long userId, @PathVariable Long profileId) {
        return userProfileService.getProfileById(userId, profileId);
    }

    @PutMapping("/{userId}/profiles/{profileId}")
    public ProfileDto updateProfile(@PathVariable Long userId, @PathVariable Long profileId, @RequestBody ProfileDto profileDto) {
        return userProfileService.updateProfileById(userId, profileId, profileDto);
    }

    @DeleteMapping("/{userId}/profiles/{profileId}")
    public void deleteProfile(@PathVariable Long userId, @PathVariable Long profileId) {
        userProfileService.deleteProfileById(userId, profileId);
    }
}
