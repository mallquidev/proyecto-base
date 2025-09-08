package com.mllq.base.proyect_base.core.domain.core.repo.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mllq.base.proyect_base.core.commons.models.enums.Role;
import com.mllq.base.proyect_base.core.domain.core.entities.RoleEntity;

@Repository// Indica a Spring que esta interfaz es un componente de acceso a datos
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    
    Optional<RoleEntity> findByName(Role name);
    boolean existsByName(Role name);
}
