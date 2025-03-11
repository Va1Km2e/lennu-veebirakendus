package com.mvaikm.lennu_veebirakendus.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO registerUser(SignUpRequest signUpRequest) {
        userRepository.findByUsername(signUpRequest.getUsername())
                .ifPresent(existingUser -> {
                    throw new RuntimeException("Username already in use");
                });

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signUpRequest.getUsername());
        userEntity.setFirstName(signUpRequest.getFirstName());
        userEntity.setLastName(signUpRequest.getLastName());
        userEntity.setPassword(signUpRequest.getPassword());

        UserEntity savedUser = userRepository.save(userEntity);

        return userMapper.toDto(savedUser);
    }

    public UserDTO getUserByID(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        return userMapper.toDto(userEntity);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }
}
