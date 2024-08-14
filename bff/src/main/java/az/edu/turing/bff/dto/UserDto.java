package az.edu.turing.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String pin;
    private LocalDate birthday;
    private LocalDate createDate;
    private LocalDate updateDate;
    private List<ProfileDto> profiles;


}
