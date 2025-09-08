package com.mllq.base.proyect_base.core.domain.core.repo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mllq.base.proyect_base.core.domain.core.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
/* 
    boolean existsByUserName(String username);

    Optional<User> findByUserName(String username); */
}
