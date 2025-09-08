package com.mllq.base.proyect_base.core.domain.app.bussines.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mllq.base.proyect_base.core.domain.core.entities.User;
import com.mllq.base.proyect_base.core.domain.core.repo.user.UserRepository;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@NoArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired //inyeccion de dependencias
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // Si tu User tiene un campo Role (entity o enum), ajusta el getter
        String roleName = (user.getRole() != null) ? user.getRole().toString() : "ROLE_USER";

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(roleName))
        );
    }  

    public boolean existsByUsername(String email) {
        return repository.existsByEmail(email);
    }

    public User save(User user) {
        return repository.save(user);
    }
}
