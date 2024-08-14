package az.edu.turing.bff.service;

import az.edu.turing.bff.dto.ProfileDto;
import az.edu.turing.bff.client.UserProfileClient;


import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BffUserProfileService {

    private final UserProfileClient userProfileClient;

    public BffUserProfileService(UserProfileClient userProfileClient) {
        this.userProfileClient = userProfileClient;
    }

    public ProfileDto getUserProfile(Long userId) {
        return userProfileClient.getUserProfileById(userId);
    }


}
