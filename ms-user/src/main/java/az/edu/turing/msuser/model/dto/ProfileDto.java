package az.edu.turing.msuser.model.dto;

import az.edu.turing.msuser.domain.enums.Gender;
import az.edu.turing.msuser.domain.enums.Status;
import az.edu.turing.msuser.domain.enums.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {


    @JsonProperty("id")
    @Schema(description = "Profile ID", example = "1")
    private Long id;

    @JsonProperty("username")
    @Schema(description = "Profile username", example = "john_doe")
    private String username;

    @JsonProperty("address")
    @Schema(description = "Profile address", example = "123 Main St")
    private String address;

    @JsonProperty("phone_number")
    @Schema(description = "Profile phone number in international format", example = "+994501234567")
    private String phoneNumber;

    @JsonProperty("email")
    @Schema(description = "Email address of the profile", example = "john.doe@example.com")
    private String email;

    @JsonProperty("bio")
    @Schema(description = "Profile bio", example = "Software Engineer with 5 years of experience.")
    private String bio;

    @JsonProperty("status")
    @Schema(description = "Profile status", example = "ACTIVE")
    private Status status;

    @JsonProperty("gender")
    @Schema(description = "Gender of the profile owner", example = "MALE")
    private Gender gender;

    @JsonProperty("image")
    @Schema(description = "Profile image URL", example = "https://example.com/images/john_doe.jpg")
    private String image;

    @JsonProperty("user_id")
    @Schema(description = "User ID associated with the profile", example = "10")
    private Long userId;

    @JsonProperty("user_username")
    @Schema(description = "Username of the associated user", example = "user123")
    private String userUsername;

    @JsonProperty("last_seen")
    @Schema(description = "Last seen timestamp", example = "2024-08-14T18:47:09.366Z")
    private LocalDateTime lastSeen;

    @JsonProperty("last_seen_visibility")
    @Schema(description = "Visibility of the last seen status", example = "PUBLIC")
    private Visibility lastSeenVisibility;

    @JsonProperty("image_visibility")
    @Schema(description = "Visibility of the profile image", example = "PUBLIC")
    private Visibility imageVisibility;
}
