package az.edu.turing.bff.controller;

import az.edu.turing.bff.dto.ProfileDto;
import az.edu.turing.bff.dto.UserDto;
import az.edu.turing.bff.service.BffUserProfileService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bff/users")
public class BffUserProfileController {

  private final BffUserProfileService userProfileService;

    public BffUserProfileController(BffUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{userId}/profiles")
    public UserDto getUserProfiles(@PathVariable Long userId) {
        return userProfileService.getUserProfiles(userId);
    }

    @GetMapping("{userId}/profiles/{profileId}")
    public ProfileDto getProfileById(@PathVariable Long userId, @PathVariable Long profileId) {
    return userProfileService.getProfileById(userId,profileId);
    }
}
