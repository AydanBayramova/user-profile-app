package az.edu.turing.msuser.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "First name of the user", example = "John")
    private String firstname;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastname;

    @Schema(description = "User's PIN", example = "1234567890")
    private String pin;

    @Schema(description = "User's date of birth", example = "1990-01-01")
    private LocalDate birthday;

    @Schema(description = "Date when the user was created", example = "2023-01-01")
    private LocalDate createDate;

    @Schema(description = "Date when the user was last updated", example = "2024-08-14")
    private LocalDate updateDate;

    @Schema(description = "List of profiles associated with the user")
    private List<ProfileDto> profiles;
}
