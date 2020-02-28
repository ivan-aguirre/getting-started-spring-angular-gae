package org.ivanaguirre.poc.context.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private IdentityProvider provider;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAuthentication;

    @ManyToOne
    private Profile profile;

    public User(String email, String name, IdentityProvider provider, Profile profile, Date created) {
        this.email = email;
        this.name = name;
        this.provider = provider;
        this.profile = profile;
        this.created = created;
    }
}
