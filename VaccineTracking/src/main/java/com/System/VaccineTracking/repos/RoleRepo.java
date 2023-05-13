package com.System.VaccineTracking.repos;

import com.System.VaccineTracking.models.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional <Role> findByRoleName(String roleName);
}
