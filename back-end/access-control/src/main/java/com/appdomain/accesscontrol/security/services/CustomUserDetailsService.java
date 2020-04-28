package com.appdomain.accesscontrol.security.services;

import com.appdomain.accesscontrol.security.contracts.CustomUserDetails;
import com.appdomain.accesscontrol.security.domains.User;
import com.appdomain.accesscontrol.security.repositories.CustomUserRepository;
import com.appdomain.accesscontrol.security.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return new CustomUserDetails(this.getUserByUsername(username));
    }

    public User getUserByUsername(final String username) throws UsernameNotFoundException {
        return this.customUserRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("No User found with userName: " + username));
    }

    //TODO: Make custom error for this
    public User loadUserByEmail(final String email) throws UsernameNotFoundException {
        return this.getCustomUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No User found with email: " + email));
    }

    public boolean userExistByEmail(final String email) {
        return this.getCustomUserByEmail(email).isPresent();
    }

    public User saveCustomUser(final User user) {
        return this.customUserRepository.save(user);
    }

    public List<CustomUserDetails> loadAllUsersAwaitingRegistration() {
        return this.customUserRepository.findByAwaitingRegistration(true)
                .stream().map(CustomUserDetails::new)
                .collect(Collectors.toList());
    }

    public List<CustomUserDetails> loadAllUsers() {
        return this.customUserRepository.findAll()
                .stream().map(CustomUserDetails::new)
                .collect(Collectors.toList());
    }

    private Optional<User> getCustomUserByEmail(final String email) {
        return this.customUserRepository.findByEmail(email);
    }

    public void deleteUser(final User user) {
        this.customUserRepository.delete(user);
    }

    public List<User> getAdministrators() {
        return this.customUserRepository.findAll().stream()
                .filter(customUser -> customUser.getRole().contentEquals("ROLE_" + Roles.ADMIN.name())
                        && customUser.getLockoutEnd().isBefore(Instant.now()))
                .collect(Collectors.toList());
    }
}
