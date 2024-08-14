package az.edu.turing.msuser.model.mapper;

import az.edu.turing.msuser.domain.entity.ProfileEntity;
import az.edu.turing.msuser.model.dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileEntity toprofileEntity(ProfileDto profileDto);
    ProfileDto toprofileDto(ProfileEntity profileEntity);
    void updateProfileEntityFromDto(ProfileDto profileDto, @MappingTarget ProfileEntity profileEntity);
}
