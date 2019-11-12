package com.idemia.assetmanagement.repository;

import com.idemia.assetmanagement.entity.Role;
import com.idemia.assetmanagement.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
