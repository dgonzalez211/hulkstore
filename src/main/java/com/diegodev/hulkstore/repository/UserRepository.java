package com.diegodev.hulkstore.repository;


import com.diegodev.hulkstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsername(String username);

    @Query("select u from com.diegodev.hulkstore.model.User u " +
            "where lower(u.username) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(u.email) like lower(concat('%', :searchTerm, '%'))")
    List<User> search(@Param("searchTerm") String searchTerm);
}
