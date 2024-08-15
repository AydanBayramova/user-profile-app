package az.edu.turing.msuser.service.impl;

import az.edu.turing.msuser.domain.entity.UserEntity;
import az.edu.turing.msuser.domain.enums.Status;
import az.edu.turing.msuser.domain.repository.UserRepository;
import az.edu.turing.msuser.exception.ResourceNotFoundException;
import az.edu.turing.msuser.model.dto.UserDto;
import az.edu.turing.msuser.model.mapper.UserMapper;
import az.edu.turing.msuser.service.UserService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        log.info("Saving new user with details: {}", userDto);
        UserEntity savedUser = userRepository.save(userMapper.toUserEntity(userDto));
        log.info("User saved successfully with ID: {}", savedUser.getId());
        return userMapper.toUserDto(savedUser);
    }

    @Transactional
    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        log.info("Fetching all users with pagination");
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        log.info("Fetched {} users", userEntities.getTotalElements());

        return userEntities.map(userMapper::toUserDto);
    }

    @Transactional
    @Override
    public Optional<UserDto> getById(Long id) {

        log.info("Fetching user with ID: {}", id);
        Optional<UserEntity> userEntity = userRepository.findByIdWithProfiles(id);
        if (userEntity.isPresent()) {
            log.info("User found with ID: {}", id);
        } else {
            log.warn("User not found with ID: {}", id);
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        return userEntity.map(userMapper::toUserDto);
    }

    @Transactional
    @Override
    public void deleteAll() {
        log.info("Deactivating and deleting all users");
        List<UserEntity> users = userRepository.findAll();
        users.forEach(user -> {
            user.getProfiles().forEach(profileEntity -> {
                profileEntity.setStatus(Status.DEACTIVATED);
                log.info("Profile ID: {} deactivated", profileEntity.getId());
            });
        });
        userRepository.saveAll(users);
        userRepository.deleteAll();
        log.info("All users and their profiles have been deleted");
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        log.info("Attempting to delete user with ID: {}", id);
        UserEntity userEntity = userRepository.findByIdWithProfiles(id)
                .orElseThrow(() -> {
                    log.error("User with ID: {} not found", id);
                    return new ResourceNotFoundException("User with id " + id + " not found");
                });

        if (!userEntity.getProfiles().isEmpty()) {
            userEntity.getProfiles().forEach(profile -> {
                profile.setStatus(Status.DEACTIVATED);
                log.info("Profile ID: {} deactivated", profile.getId());
            });
            userRepository.save(userEntity);
        }
        userRepository.deleteById(id);
        log.info("User with ID: {} deleted successfully", id);
    }

    @Transactional
    @Override
    public UserDto update(Long id, UserDto userDto) {
        log.info("Attempting to update user with ID: {}", id);
        Optional<UserEntity> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            UserEntity updatedUser = userRepository.save(userMapper.toUserEntity(userDto));
            log.info("User with ID: {} updated successfully", id);
            return userMapper.toUserDto(updatedUser);
        } else {
            log.error("User with ID: {} not found", id);
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }

}
