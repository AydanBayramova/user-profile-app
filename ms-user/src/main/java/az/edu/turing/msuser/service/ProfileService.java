package az.edu.turing.msuser.service;


import az.edu.turing.msuser.model.dto.ProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface ProfileService {

    ProfileDto addProfile(Long userId, ProfileDto profileDto);

    ProfileDto updateProfile(Long userId,Long profileId, ProfileDto profileDto);

    ProfileDto getProfileById(Long userId,Long profileId);

    void deleteProfileById(Long userId, Long profileId);

    Page<ProfileDto> getAllProfiles(Long id,Pageable pageable);

//    void deleteAllProfiles();

}
