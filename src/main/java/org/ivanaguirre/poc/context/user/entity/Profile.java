package org.ivanaguirre.poc.context.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Profile {

    public static enum PreLoaded {
        Viewer
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @ManyToMany
    private Set<Role> roles;

    public Profile(Set<Role> roles, String name, String description) {
        this.roles = roles;
        this.name = name;
        this.description = description;
    }
}
