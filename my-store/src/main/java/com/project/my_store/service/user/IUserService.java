package com.project.my_store.service.user;

import com.project.my_store.dto.UserDto;
import com.project.my_store.model.User;
import com.project.my_store.requests.CreateUserRequest;
import com.project.my_store.requests.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
