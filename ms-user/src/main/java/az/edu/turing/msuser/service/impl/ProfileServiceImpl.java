package az.edu.turing.msuser.service.impl;


import az.edu.turing.msuser.domain.entity.ProfileEntity;
import az.edu.turing.msuser.domain.entity.UserEntity;
import az.edu.turing.msuser.domain.repository.ProfileRepository;
import az.edu.turing.msuser.domain.repository.UserRepository;
import az.edu.turing.msuser.model.dto.ProfileDto;
import az.edu.turing.msuser.model.mapper.ProfileMapper;
import az.edu.turing.msuser.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ProfileDto addProfile(Long userId, ProfileDto profileDto) {
        UserEntity user = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("user not found"));
        if (user.getAge() < 18) {
            throw new IllegalArgumentException("Age must be 18");
        }
        ProfileEntity profileEntity = profileMapper.toprofileEntity(profileDto);
        profileEntity.setUser(user);
        profileRepository.save(profileEntity);
        return profileMapper.toprofileDto(profileEntity);
    }

    @Transactional
    @Override
    public ProfileDto updateProfile(Long userId, Long profileId, ProfileDto profileDto) {
        Optional<ProfileEntity> existingProfileOpt = profileRepository.findByUserIdAndId(userId, profileId);
        if (existingProfileOpt.isPresent()) {
            ProfileEntity existingProfile = existingProfileOpt.get();
            profileMapper.updateProfileEntityFromDto(profileDto, existingProfile);
            ProfileEntity updatedProfile = profileRepository.save(existingProfile);
            return profileMapper.toprofileDto(updatedProfile);
        } else {
            throw new RuntimeException("Profile not found");
        }
    }


    @Override
    public ProfileDto getProfileById(Long userId,Long profileId) {
        return profileMapper.toprofileDto(profileRepository.findByUserIdAndId(userId,profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found")));
    }

    @Transactional
    @Override
    public void deleteProfileById(Long userId , Long profileId) {
        ProfileEntity profile = profileRepository.findByUserIdAndId(userId,profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profileRepository.delete(profile);
    }

    @Transactional
    @Override
    public Page<ProfileDto> getAllProfiles(Long userId, Pageable pageable) {
        return profileRepository.findAllByUserId(userId, pageable).map(profileMapper::toprofileDto);
    }
//    @Transactional
//    @Override
//    public void deleteAllProfiles() {
//        profileRepository.deleteAll();
//    }
}
