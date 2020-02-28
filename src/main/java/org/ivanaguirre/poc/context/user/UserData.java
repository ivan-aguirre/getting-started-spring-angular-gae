package org.ivanaguirre.poc.context.user;

import lombok.Getter;

import java.util.Collections;
import java.util.Set;

public class UserData {

    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private Set<String> roles;

    public UserData(Long id, String name, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.roles = Collections.unmodifiableSet(roles);
    }
}
