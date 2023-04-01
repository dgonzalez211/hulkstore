package com.diegodev.hulkstore.data.service;

import com.diegodev.hulkstore.data.service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudBaseService {

    UserRepository repository;

    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
