package az.edu.turing.msuser.service.impl;

import az.edu.turing.msuser.domain.entity.UserEntity;
import az.edu.turing.msuser.domain.repository.UserRepository;
import az.edu.turing.msuser.exception.ResourceNotFoundException;
import az.edu.turing.msuser.model.dto.UserDto;
import az.edu.turing.msuser.model.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(userMapper.toUserEntity(any(UserDto.class))).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto result = userService.save(userDto);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testGetAll() {
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        Page<UserEntity> userPage = new PageImpl<>(Arrays.asList(user1, user2));
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toUserDto(user1)).thenReturn(new UserDto());
        when(userMapper.toUserDto(user2)).thenReturn(new UserDto());

        Page<UserDto> result = userService.getAll(pageable);

        assertEquals(2, result.getTotalElements());
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetById() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        UserDto userDto = new UserDto();

        when(userRepository.findByIdWithProfiles(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        Optional<UserDto> result = userService.getById(1L);

        assertTrue(result.isPresent());
        verify(userRepository, times(1)).findByIdWithProfiles(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(userRepository.findByIdWithProfiles(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getById(1L));
        verify(userRepository, times(1)).findByIdWithProfiles(1L);
    }

    @Test
    void testDeleteAll() {
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        user1.setProfiles(Collections.emptyList()); // Mock profiles
        user2.setProfiles(Collections.emptyList());

        List<UserEntity> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        userService.deleteAll();

        verify(userRepository, times(1)).findAll();
        verify(userRepository, times(1)).saveAll(users);
        verify(userRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteById() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setProfiles(Collections.emptyList()); // Mock profiles

        when(userRepository.findByIdWithProfiles(1L)).thenReturn(Optional.of(userEntity));

        userService.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(userRepository.findByIdWithProfiles(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteById(1L));
        verify(userRepository, times(0)).deleteById(1L);
    }

    @Test
    void testUpdate() {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.toUserEntity(any(UserDto.class))).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto result = userService.update(1L, userDto);

        assertNotNull(result);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testUpdate_NotFound() {
        UserDto userDto = new UserDto();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.update(1L, userDto));
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }
}
