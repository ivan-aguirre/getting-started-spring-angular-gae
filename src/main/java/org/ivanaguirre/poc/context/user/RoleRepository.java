package org.ivanaguirre.poc.context.user;

import org.ivanaguirre.poc.context.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
