package az.edu.turing.bff.client;


import az.edu.turing.bff.dto.ProfileDto;
import az.edu.turing.bff.dto.UserDto;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public UserDto getUserProfiles(Long userId) {
        try {
            String url = userProfileBaseUrl + "/api/v1/users/" + userId;
            System.out.println("Request URL: " + url);
            ResponseEntity<UserDto> response = restTemplate.getForEntity(url, UserDto.class);
            System.out.println("Received User with Profiles: " + response.getBody());
            return response.getBody();

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }


    public ProfileDto getProfileById(Long userId, Long profileId) {
        try {
            String url = userProfileBaseUrl + "/api/v1/users/" + userId + "/profiles/" + profileId;
            System.out.println("Request URL: " + url);
            ResponseEntity<ProfileDto> response = restTemplate.getForEntity(url, ProfileDto.class);
            System.out.println("Receive with Profiles: " + response.getBody());
            return response.getBody();

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }

}
