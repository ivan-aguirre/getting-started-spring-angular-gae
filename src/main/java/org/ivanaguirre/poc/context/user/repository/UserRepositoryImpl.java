package org.ivanaguirre.poc.context.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class UserRepositoryImpl implements CustomUserRepository {

    @Autowired
    private EntityManager entityManager;
}
