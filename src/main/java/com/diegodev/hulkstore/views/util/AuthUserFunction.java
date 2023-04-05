package com.diegodev.hulkstore.views.util;

@FunctionalInterface
public interface AuthUserFunction<User> {
    void apply(User user);
}
