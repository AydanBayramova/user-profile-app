package az.edu.turing.bff.service;

import az.edu.turing.bff.dto.ProfileDto;
import az.edu.turing.bff.client.UserProfileClient;

import az.edu.turing.bff.dto.UserDto;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class BffUserProfileService {

    private final UserProfileClient userProfileClient;

    public BffUserProfileService(UserProfileClient userProfileClient) {
        this.userProfileClient = userProfileClient;
    }

    public UserDto getUserProfiles(Long userId) {
        return userProfileClient.getUserProfiles(userId);
    }

  public ProfileDto getProfileById(Long userId,Long profileId) {
        return userProfileClient.getProfileById(userId,profileId);
  }


}
