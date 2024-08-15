package az.edu.turing.msuser.service.impl;

import az.edu.turing.msuser.domain.entity.ProfileEntity;
import az.edu.turing.msuser.domain.entity.UserEntity;
import az.edu.turing.msuser.domain.repository.ProfileRepository;
import az.edu.turing.msuser.domain.repository.UserRepository;
import az.edu.turing.msuser.model.dto.ProfileDto;
import az.edu.turing.msuser.model.mapper.ProfileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProfile() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setBirthday(LocalDate.now().minusYears(20));

        ProfileDto profileDto = new ProfileDto();
        profileDto.setUsername("john_doe");
        profileDto.setEmail("john.doe@example.com");

        ProfileEntity profileEntity = new ProfileEntity();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(profileMapper.toprofileEntity(any(ProfileDto.class))).thenReturn(profileEntity);
        when(profileRepository.save(any(ProfileEntity.class))).thenReturn(profileEntity);
        when(profileMapper.toprofileDto(any(ProfileEntity.class))).thenReturn(profileDto);

        ProfileDto result = profileService.addProfile(1L, profileDto);

        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(profileRepository, times(1)).save(any(ProfileEntity.class));
    }

    @Test
    void testGetProfileById() {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(1L);

        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(1L);

        when(profileRepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.of(profileEntity));
        when(profileMapper.toprofileDto(profileEntity)).thenReturn(profileDto);

        ProfileDto result = profileService.getProfileById(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testDeleteProfileById() {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(1L);

        when(profileRepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.of(profileEntity));

        profileService.deleteProfileById(1L, 1L);

        verify(profileRepository, times(1)).delete(profileEntity);
    }

    @Test
    void testGetAllProfiles() {
        ProfileEntity profileEntity1 = new ProfileEntity();
        ProfileEntity profileEntity2 = new ProfileEntity();
        ProfileDto profileDto1 = new ProfileDto();
        ProfileDto profileDto2 = new ProfileDto();

        Page<ProfileEntity> profileEntities = new PageImpl<>(Arrays.asList(profileEntity1, profileEntity2));
        Pageable pageable = PageRequest.of(0, 10);

        when(profileRepository.findAllByUserId(1L, pageable)).thenReturn(profileEntities);
        when(profileMapper.toprofileDto(profileEntity1)).thenReturn(profileDto1);
        when(profileMapper.toprofileDto(profileEntity2)).thenReturn(profileDto2);

        Page<ProfileDto> result = profileService.getAllProfiles(1L, pageable);

        assertEquals(2, result.getTotalElements());
        verify(profileRepository, times(1)).findAllByUserId(1L, pageable);
    }
}
