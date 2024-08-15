package az.edu.turing.msuser.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonIgnore
    private Long id;

    private String firstname;
    private String lastname;
    private String pin;
    private LocalDate birthday;
    private LocalDate createDate;
    private LocalDate updateDate;

    @JsonIgnore
    private List<ProfileDto> profiles;

}
