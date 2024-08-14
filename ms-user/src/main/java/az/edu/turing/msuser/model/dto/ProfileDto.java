package az.edu.turing.msuser.model.dto;

import az.edu.turing.msuser.domain.enums.Gender;
import az.edu.turing.msuser.domain.enums.Status;
import az.edu.turing.msuser.domain.enums.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class ProfileDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("image")
    private String image;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("user_username")
    private String userUsername;

    @JsonProperty("last_seen")
    private LocalDateTime lastSeen;

    @JsonProperty("last_seen_visibility")
    private Visibility lastSeenVisibility;

    @JsonProperty("image_visibility")
    private Visibility imageVisibility;
}

