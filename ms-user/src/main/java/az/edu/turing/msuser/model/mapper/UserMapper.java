package az.edu.turing.msuser.model.mapper;

import az.edu.turing.msuser.domain.entity.UserEntity;
import az.edu.turing.msuser.model.dto.UserDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

     UserDto toUserDto(UserEntity user);

     UserEntity toUserEntity(UserDto userDto);

}
