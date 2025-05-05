package com.project.my_store.service.user;

import com.project.my_store.dto.UserDto;
import com.project.my_store.model.User;
import com.project.my_store.repository.UserRepository;
import com.project.my_store.requests.CreateUserRequest;
import com.project.my_store.requests.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return null;
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public UserDto convertUserToDto(User user) {
        return null;
    }
}
