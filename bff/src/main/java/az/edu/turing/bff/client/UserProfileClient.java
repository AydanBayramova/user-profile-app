package az.edu.turing.bff.client;

import az.edu.turing.bff.dto.ProfileDto;
import az.edu.turing.bff.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserProfileClient {
    private final RestTemplate restTemplate;

    @Value("${ms-user.url}")
    private String userProfileBaseUrl;

    public UserProfileClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getUserById(Long userId) {
        try {
            String url = userProfileBaseUrl + "/api/v1/users/" + userId;
            System.out.println("Request URL: " + url);
            UserDto user = restTemplate.getForObject(url, UserDto.class);
            System.out.println("Received User: " + user);
            return user;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }

    public Page<UserDto> getUsers(Pageable pageable) {
        try {
            String url = userProfileBaseUrl + "/api/v1/users/all?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize();
            System.out.println("Request URL: " + url);
            ResponseEntity<Page<UserDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Page<UserDto>>() {
                    }
            );
            Page<UserDto> users = response.getBody();
            System.out.println("Received Users: " + users);
            return users;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return Page.empty();
        }
    }

    public Page<ProfileDto> getProfiles(Long userId, Pageable pageable) {
        try {
            String url = userProfileBaseUrl + "/api/v1/users/" + userId + "/profiles?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize();
            System.out.println("Request URL: " + url);
            ResponseEntity<Page<ProfileDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Page<ProfileDto>>() {
                    }
            );
            Page<ProfileDto> profiles = response.getBody();
            System.out.println("Received Profiles: " + profiles);
            return profiles;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return Page.empty();
        }
    }

    public ProfileDto getProfileById(Long userId, Long profileId) {
        try {
            String url = userProfileBaseUrl + "/api/v1/users/" + userId + "/profiles/" + profileId;
            System.out.println("Request URL: " + url);
            ProfileDto profile = restTemplate.getForObject(url, ProfileDto.class);
            System.out.println("Received Profile: " + profile);
            return profile;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }

    public ProfileDto updateUserProfile(Long userId, Long profileId, ProfileDto profileDto) {
        try {
            String url = userProfileBaseUrl + "/api/v1/users/" + userId + "/profiles";
            HttpEntity<ProfileDto> request = new HttpEntity<>(profileDto);
            ResponseEntity<ProfileDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, ProfileDto.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }  //UPDATE profile


    public void deleteUserProfile(Long userId, Long profileId) {
        try {
            String url = userProfileBaseUrl + "/api/v1/users/" + userId + "/profiles/" + profileId;
            restTemplate.delete(url);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    } //DELETE profile
}
