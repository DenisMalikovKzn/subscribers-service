package com.example.demo.service;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Creating user: {}", userRequest.email());
        User savedUser = userRepository.save(convertToUserEntity(userRequest));
        return convertToUserResponse(savedUser);
    }

    @Override
    public UserResponse getUserDtoById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return convertToUserResponse(user);
    }

    @Override
    public User getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        User updatedUser = userRepository.save(user);
        return convertToUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    public UserResponse convertToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    public User convertToUserEntity(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        return user;
    }
}
