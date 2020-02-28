package org.ivanaguirre.poc.test.spring.repository;

import org.ivanaguirre.poc.context.user.entity.IdentityProvider;
import org.ivanaguirre.poc.context.user.entity.Profile;
import org.ivanaguirre.poc.context.user.entity.Role;
import org.ivanaguirre.poc.context.user.entity.User;
import org.ivanaguirre.poc.context.user.repository.UserRepository;
import org.ivanaguirre.poc.context.user.view.UserProfileView;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TestUserRepository {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_findAllBy() {
        Profile p = loadData();

        List<UserProfileView> list = userRepository.findAllUserProfileView();
        assertThat(list.size()).isEqualTo(1);

        UserProfileView u = list.get(0);

        assertThat(u.getId()).isGreaterThan(0);
        assertThat(u.getName()).isEqualTo("Homer Simpson");
        assertThat(u.getEmail()).isEqualTo("test@gmail.com");
        assertThat(u.getProfile().getId()).isEqualTo(p.getId());
        assertThat(u.getProfile().getId()).isGreaterThan(0);
        assertThat(u.getProfile().getName()).isEqualTo("Viewer");
        assertThat(u.getProfile().getDescription()).isEqualTo("Basic read only profile");
    }

    private Profile loadData() {
        Role r = em.persist(new Role("employee_viewer",
                "Employee Viewer", "Permission to view all employees"));

        Profile p = em.persist(new Profile(Collections.singleton(r),
                Profile.PreLoaded.Viewer.name(),
                "Basic read only profile"));

        Date now = new Date();
        em.persist(new User("test@gmail.com", "Homer Simpson", IdentityProvider.GOOGLE, p, now));

        em.flush();
        em.clear();

        return p;
    }
}
