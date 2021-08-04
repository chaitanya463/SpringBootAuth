package com.securitydemo.securityDemo.users;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/users")
public class UserManagementController {
    private static final List<User> users = Arrays.asList(
            new User(1, "Rohan"),
            new User(2, "Zues"),
            new User(3, "Maximus"));

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRINEE')")
    public List<User> getAllUsers() {
        return users;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public void registerNewUser(@RequestBody User user) {
        System.out.println(user);
    }

    @DeleteMapping(path="{userId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        System.out.println(userId);
    }

    @PutMapping(path = "{userId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void updateUser(@PathVariable("userId") Integer userId, @RequestBody User user) {
        System.out.println(String.format("%s %s", userId, user));
    }
}
