package com.example.UserManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    @Transactional
    @Modifying
    @Query("delete from User u WHERE u.username = :uname")
    void delete(@Param("uname") String username);
}
