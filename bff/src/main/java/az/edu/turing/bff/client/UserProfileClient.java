package az.edu.turing.bff.client;


import az.edu.turing.bff.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserProfileClient {
    private final RestTemplate restTemplate;

    @Value("${ms-user.url}")
    private String userProfileBaseUrl;

    public UserProfileClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProfileDto getUserProfileById(Long userId) {
        try {
            String url = userProfileBaseUrl + "/api/v1/users/" + userId + "/profiles";
            System.out.println("Request URL: " + url);
            ProfileDto profile = restTemplate.getForObject(url, ProfileDto.class);
            System.out.println("Received Profile: " + profile);
            return profile;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }



}
