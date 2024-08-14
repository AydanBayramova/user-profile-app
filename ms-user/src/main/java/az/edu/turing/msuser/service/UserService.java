package az.edu.turing.msuser.service;






import az.edu.turing.msuser.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface UserService {

    UserDto save(UserDto userDto);

    Page<UserDto> getAll(Pageable pageable);

    Optional<UserDto> getById(Long id);

    void deleteAll();

    void deleteById(Long id);

    UserDto update(Long id, UserDto userDto);
}
