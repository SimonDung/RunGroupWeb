package com.rungroup.web.repository;

import com.rungroup.web.models.Role;
import com.rungroup.web.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
