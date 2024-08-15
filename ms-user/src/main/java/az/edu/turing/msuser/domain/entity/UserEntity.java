package az.edu.turing.msuser.domain.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false, length = 15)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 15)
    private String lastname;

    @Column(name = "pin", nullable = false, unique = true, length = 7)
    @Size(min = 7, max = 7, message = ("Pin must be exactly 7 symbol!"))
    private String pin;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "create_date",
            updatable = false)
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProfileEntity> profiles;


}
