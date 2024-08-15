package az.edu.turing.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ProfileDto {
//    private Long id;
    private String username;
    private String address;
    private String phoneNumber;
    private String email;
    private String bio;
//    private String status;
    private String gender;
    private String image;
//    private Long userId;
//    private String userUsername;
    private LocalDateTime lastSeen;
    private String lastSeenVisibility;
    private String imageVisibility;

}
