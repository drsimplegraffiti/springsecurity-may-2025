package com.drsimple.jwtsecurity.util;

import com.drsimple.jwtsecurity.exception.UserNotFoundException;
import com.drsimple.jwtsecurity.user.User;
import com.drsimple.jwtsecurity.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserUtil {

    private final UserRepository userRepository;

    public CurrentUserUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public User getLoggedInUser() {
        String username = getLoggedInUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
    }
}
