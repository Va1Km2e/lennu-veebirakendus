package com.mvaikm.lennu_veebirakendus.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserByID(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody SignUpRequest signUpRequest) {
        try {
            userService.registerUser(signUpRequest);

            return "User registered successfully";
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }
}
