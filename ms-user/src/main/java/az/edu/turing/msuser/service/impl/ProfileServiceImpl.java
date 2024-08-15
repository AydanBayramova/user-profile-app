package az.edu.turing.msuser.service.impl;

import az.edu.turing.msuser.domain.entity.ProfileEntity;
import az.edu.turing.msuser.domain.entity.UserEntity;
import az.edu.turing.msuser.domain.repository.ProfileRepository;
import az.edu.turing.msuser.domain.repository.UserRepository;
import az.edu.turing.msuser.model.dto.ProfileDto;
import az.edu.turing.msuser.model.mapper.ProfileMapper;
import az.edu.turing.msuser.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ProfileDto addProfile(Long userId, ProfileDto profileDto) {
        log.info("Attempting to add profile for user with ID: {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new RuntimeException("User not found");
                });

        int age = Period.between(user.getBirthday(), LocalDate.now()).getYears();
        if (age < 18) {
            log.error("User with ID: {} is under 18. Age: {}", userId, age);
            throw new IllegalArgumentException("Age must be 18");
        }

        boolean hasEmail = profileDto.getEmail() != null && !profileDto.getEmail().trim().isEmpty();
        boolean hasPhoneNumber = profileDto.getPhoneNumber() != null && !profileDto.getPhoneNumber().trim().isEmpty();

        if (!hasEmail && !hasPhoneNumber) {
            log.error("Both email and phone number are missing in profile for user with ID: {}", userId);
            throw new IllegalArgumentException("Either email or phone number must be provided");
        }

        if (profileRepository.existsByUsername(profileDto.getUsername())) {
            log.error("A profile with username '{}' already exists", profileDto.getUsername());
            throw new IllegalArgumentException("A profile with this username already exists");
        }

        ProfileEntity profileEntity = profileMapper.toprofileEntity(profileDto);
        profileEntity.setUser(user);

        profileRepository.save(profileEntity);

        log.info("Profile successfully added for user with ID: {}", userId);
        return profileMapper.toprofileDto(profileEntity);
    }

    @Transactional
    @Override
    public ProfileDto updateProfile(Long userId, Long profileId, ProfileDto profileDto) {
        log.info("Attempting to update profile with ID: {} for user with ID: {}", profileId, userId);

        Optional<ProfileEntity> existingProfileOpt = profileRepository.findByUserIdAndId(userId, profileId);
        if (existingProfileOpt.isPresent()) {
            ProfileEntity existingProfile = existingProfileOpt.get();
            profileMapper.updateProfileEntityFromDto(profileDto, existingProfile);
            ProfileEntity updatedProfile = profileRepository.save(existingProfile);

            log.info("Profile with ID: {} for user with ID: {} successfully updated", profileId, userId);
            return profileMapper.toprofileDto(updatedProfile);
        } else {
            log.error("Profile not found with ID: {} for user with ID: {}", profileId, userId);
            throw new RuntimeException("Profile not found");
        }
    }

    @Override
    public ProfileDto getProfileById(Long userId, Long profileId) {
        log.info("Fetching profile with ID: {} for user with ID: {}", profileId, userId);

        ProfileDto profileDto = profileMapper.toprofileDto(
                profileRepository.findByUserIdAndId(userId, profileId)
                        .orElseThrow(() -> {
                            log.error("Profile not found with ID: {} for user with ID: {}", profileId, userId);
                            return new RuntimeException("Profile not found");
                        }));

        log.info("Profile with ID: {} for user with ID: {} successfully fetched", profileId, userId);
        return profileDto;
    }

    @Transactional
    @Override
    public void deleteProfileById(Long userId, Long profileId) {
        log.info("Attempting to delete profile with ID: {} for user with ID: {}", profileId, userId);

        ProfileEntity profile = profileRepository.findByUserIdAndId(userId, profileId)
                .orElseThrow(() -> {
                    log.error("Profile not found with ID: {} for user with ID: {}", profileId, userId);
                    return new RuntimeException("Profile not found");
                });

        profileRepository.delete(profile);

        log.info("Profile with ID: {} for user with ID: {} successfully deleted", profileId, userId);
    }

    @Transactional
    @Override
    public Page<ProfileDto> getAllProfiles(Long userId, Pageable pageable) {
        log.info("Fetching all profiles for user with ID: {}", userId);

        Page<ProfileDto> profilesPage = profileRepository.findAllByUserId(userId, pageable)
                .map(profileMapper::toprofileDto);

        log.info("Successfully fetched profiles for user with ID: {}. Total profiles found: {}", userId, profilesPage.getTotalElements());
        return profilesPage;
    }

//    @Transactional
//    @Override
//    public void deleteAllProfiles() {
//        log.info("Attempting to delete all profiles");
//        profileRepository.deleteAll();
//        log.info("All profiles successfully deleted");
//    }
}
