package az.edu.turing.bff.service;

import az.edu.turing.bff.client.UserProfileClient;
import az.edu.turing.bff.dto.ProfileDto;
import az.edu.turing.bff.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BffUserProfileService {

    private final UserProfileClient userProfileClient;

    public BffUserProfileService(UserProfileClient userProfileClient) {
        this.userProfileClient = userProfileClient;
    }

    public UserDto getUserById(Long userId) {
        return userProfileClient.getUserById(userId);
    }

    public List<UserDto> getAllUsersByAge(int age) {
        List<UserDto> allUsers = userProfileClient.getUsers(Pageable.unpaged()).getContent();
        return allUsers.stream()
                .filter(user -> calculateAge(user.getBirthday()) == age)
                .collect(Collectors.toList());
    }

    public List<ProfileDto> getAllProfilesByAge(int age) {
        List<UserDto> allUsers = userProfileClient.getUsers(Pageable.unpaged()).getContent();
        List<Long> userIds = allUsers.stream()
                .filter(user -> calculateAge(user.getBirthday()) == age)
                .map(UserDto::getId)
                .toList();

        if (userIds.isEmpty()) {
            return List.of();
        }

        return userIds.stream()
                .flatMap(userId -> userProfileClient.getProfiles(userId, Pageable.unpaged()).getContent().stream())
                .collect(Collectors.toList());
    }

    public ProfileDto getProfileById(Long userId, Long profileId) {
        return userProfileClient.getProfileById(userId, profileId);
    }

    private int calculateAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
