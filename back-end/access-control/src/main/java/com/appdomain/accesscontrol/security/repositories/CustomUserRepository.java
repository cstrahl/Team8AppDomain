package com.appdomain.accesscontrol.security.repositories;

import com.appdomain.accesscontrol.security.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(final String userName);

    Optional<User> findByEmail(final String email);

    List<User> findByAwaitingRegistration(final boolean awaitingRegistration);
}
