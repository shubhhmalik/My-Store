package com.project.my_store.service.user;

import com.project.my_store.dto.UserDto;
import com.project.my_store.exceptions.ResourceNotFoundException;
import com.project.my_store.model.User;
import com.project.my_store.repository.UserRepository;
import com.project.my_store.requests.CreateUserRequest;
import com.project.my_store.requests.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.project.my_store.exceptions.AlreadyExistsException;



@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Oops! " + request.getEmail() + " already exists!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        userRepository.delete(user);
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return mapper.map(user, UserDto.class);
    }
}
