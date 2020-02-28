package org.ivanaguirre.poc.context.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String key;

    private String name;

    private String description;

    public Role(String key, String name, String description) {
        this.key = key;
        this.name = name;
        this.description = description;
    }
}
