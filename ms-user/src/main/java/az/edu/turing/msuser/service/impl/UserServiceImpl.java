package az.edu.turing.msuser.service.impl;


import az.edu.turing.msuser.domain.entity.UserEntity;
import az.edu.turing.msuser.domain.enums.Status;
import az.edu.turing.msuser.domain.repository.UserRepository;
import az.edu.turing.msuser.exception.ResourceNotFoundException;
import az.edu.turing.msuser.model.dto.UserDto;
import az.edu.turing.msuser.model.mapper.UserMapper;
import az.edu.turing.msuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        return userMapper.toUserDto(userRepository.save(userMapper.toUserEntity(userDto)));
    }

    @Transactional
    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        return userEntities.map(userMapper::toUserDto);
    }

    @Transactional
    @Override
    public Optional<UserDto> getById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findByIdWithProfiles(id);
        return userEntity.map(userMapper::toUserDto);
    }

    @Transactional
    @Override
    public void deleteAll() {
        List<UserEntity> users = userRepository.findAll();
        users.forEach(user -> {
            user.getProfiles().forEach(profileEntity ->
                    profileEntity.setStatus(Status.DEACTIVATED));
        });
        userRepository.saveAll(users);
        userRepository.deleteAll();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        UserEntity userEntity = userRepository.findByIdWithProfiles(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        if (!userEntity.getProfiles().isEmpty()) {
            userEntity.getProfiles().forEach(profile -> profile.setStatus(Status.DEACTIVATED));
            userRepository.save(userEntity);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserDto update(Long id, UserDto userDto) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            return userMapper.toUserDto(userRepository.save(userMapper.toUserEntity(userDto)));
        } else {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }


}
