package org.ivanaguirre.poc.context.user;

import org.ivanaguirre.poc.context.user.entity.*;
import org.ivanaguirre.poc.context.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public UserData newOrExistent(String email, String name, Date authDate, IdentityProvider provider) {
        User u = userRepository.findByEmailAndProvider(email, IdentityProvider.GOOGLE);

        if (u == null) {
            Profile viewerProfile = profileRepository.findByName(Profile.PreLoaded.Viewer.name());
            u = new User(email, name, provider, viewerProfile, authDate);
        }

        if (!u.getName().equals(name)) {
            u.setName(name);
        }
        u.setLastAuthentication(authDate);

        userRepository.save(u);
        Set<String> roles = u.getProfile().getRoles().stream().map(Role::getKey).collect(Collectors.toSet());
        return new UserData(u.getId(), u.getName(), roles);
    }

}
