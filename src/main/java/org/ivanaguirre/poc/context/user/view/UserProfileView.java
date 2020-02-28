package org.ivanaguirre.poc.context.user.view;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class UserProfileView {

    private Long id;

    private String name;

    private String email;

    private ProfileDetailsView profile;

    public UserProfileView(Long id, String name, String email, Long profileId,
                           String profileName, String profileDescription) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profile = new ProfileDetailsView(profileId, profileName, profileDescription);
    }

    @Data
    public static class ProfileDetailsView {
        private Long id;

        private String name;

        private String description;

        public ProfileDetailsView(Long id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
    }
}
