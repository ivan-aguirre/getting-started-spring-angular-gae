package org.ivanaguirre.poc.context.user;

import org.ivanaguirre.poc.context.user.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByName(String name);
}
