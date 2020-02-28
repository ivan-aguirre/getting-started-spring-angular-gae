package org.ivanaguirre.poc.context.user.repository;

import org.ivanaguirre.poc.context.user.entity.IdentityProvider;
import org.ivanaguirre.poc.context.user.entity.User;
import org.ivanaguirre.poc.context.user.view.UserProfileView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

    User findByEmailAndProvider(String email, IdentityProvider google);

    @Query("select new org.ivanaguirre.poc.context.user.view.UserProfileView(u.id, u.name, u.email, p.id," +
            "p.name, p.description) from User u join u.profile p order by u.name")
    List<UserProfileView> findAllUserProfileView();
}

interface CustomUserRepository {
}
