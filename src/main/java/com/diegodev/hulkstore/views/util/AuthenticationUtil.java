package com.diegodev.hulkstore.views.util;

import com.diegodev.hulkstore.model.User;
import com.diegodev.hulkstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {

    private static UserService userService;

    @Autowired
    public AuthenticationUtil(UserService userService) {
        AuthenticationUtil.userService = userService;
    }

    public static void doIfAuthenticated(AuthUserFunction<User> action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = userService.findByUsername(authentication.getName());
            if (user != null) {
                action.apply(user);
            }
        }
    }
}

