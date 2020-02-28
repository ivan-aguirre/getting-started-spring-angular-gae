package org.ivanaguirre.poc;

import org.ivanaguirre.poc.context.user.entity.IdentityProvider;
import org.ivanaguirre.poc.context.user.entity.Profile;
import org.ivanaguirre.poc.context.user.ProfileRepository;
import org.ivanaguirre.poc.context.user.entity.Role;
import org.ivanaguirre.poc.context.user.RoleRepository;
import org.ivanaguirre.poc.context.user.entity.User;
import org.ivanaguirre.poc.context.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class PocApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocApplication.class);
	}

	@Bean
	public CommandLineRunner demo2(ProfileRepository profileRepository,
								   RoleRepository roleRepository,
								   UserRepository userRepository) {
		return (args) -> {
			Role r = roleRepository.save(new Role("employee_viewer",
					"Employee Viewer", "Permission to view all employees"));

			Profile p = new Profile(Collections.singleton(r),
					Profile.PreLoaded.Viewer.name(),
					"Basic read only profile");
			profileRepository.save(p);

			userRepository.save(new User("homer@gmail.com", "Homer Simpson",
					IdentityProvider.GOOGLE, p, new Date()));
		};
	}

}
