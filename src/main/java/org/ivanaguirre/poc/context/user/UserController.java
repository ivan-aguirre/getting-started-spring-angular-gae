package org.ivanaguirre.poc.context.user;

import org.ivanaguirre.poc.context.user.entity.Profile;
import org.ivanaguirre.poc.context.user.entity.User;
import org.ivanaguirre.poc.context.user.repository.UserRepository;
import org.ivanaguirre.poc.context.user.view.UserProfileView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/users")
    public List<UserProfileView> all() {
        return userRepository.findAllUserProfileView();
    }

    @PutMapping("/user/{userId}/profile/{profileId}")
    public void assignProfile(Long userId, Long profileId) {
        User user = userRepository.findById(userId).get();
        Profile profile = profileRepository.findById(profileId).get();
        user.setProfile(profile);
        userRepository.save(user);
    }
}
