package com.diegodev.hulkstore.service;


import com.diegodev.hulkstore.enums.Role;
import com.diegodev.hulkstore.exceptions.CustomException;
import com.diegodev.hulkstore.model.User;
import com.diegodev.hulkstore.repository.UserRepository;
import com.diegodev.hulkstore.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserService extends CrudBaseService<UserRepository, User> {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public void signUp(String name, String username, String email, String password) throws CustomException {
        // Check to see if the current email address has already been registered.
        if (Helper.notNull(repository.findByEmail(email))) {
            // If the email address has been registered then throw an exception.
            throw new CustomException("User already exists");
        }
        // Check to see if the current username has already been registered.
        if (Helper.notNull(repository.findByUsername(username))) {
            // If the username has been registered then throw an exception.
            throw new CustomException("Username is already taken, please choose another one");
        }
        // first encrypt the password
        String encryptedPassword = null;
        try {
            encryptedPassword = hashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        User user = new User(name, username, email, roles, encryptedPassword);

        try {
            // save the User
            save(user);
        } catch (Exception e) {
            // handle signup error
            throw new CustomException(e.getMessage());
        }
    }

    String hashPassword(String password) throws NoSuchAlgorithmException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(password);
    }

    public List<User> findAllUsers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.search(stringFilter);
        }
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
    public long countUsers() {
        return count();
    }

    public void deleteUser(User User) {
        delete(User.getId());
    }

    public User saveUser(User User) {
        if (User == null) {
            System.err.println("User is null. Are you sure you have connected your form to the application?");
            return null;
        }
        return save(User);
    }
}
