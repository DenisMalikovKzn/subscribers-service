package com.example.demo.service;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUserDtoById(Long id);
    User getUserEntityById(Long id);
    UserResponse updateUser(Long id, UserRequest userRequest);
    void deleteUser(Long id);
    UserResponse convertToUserResponse(User user);
    User convertToUserEntity(UserRequest userRequest);
}
