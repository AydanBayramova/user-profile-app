package az.edu.turing.msuser.model.dto;

import az.edu.turing.msuser.domain.enums.Gender;
import az.edu.turing.msuser.domain.enums.Status;
import az.edu.turing.msuser.domain.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long id;
    private String username;
    private String address;
    private String phoneNumber;
    private String email;
    private String bio;
    private Status status;
    private Gender gender;
    private String image;
    private Long userId;
    private String userUsername;

    private LocalDateTime lastSeen;

    private Visibility lastSeenVisibility;

    private Visibility imageVisibility;
}
