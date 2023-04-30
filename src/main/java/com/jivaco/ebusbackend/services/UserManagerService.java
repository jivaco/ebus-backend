package com.jivaco.ebusbackend.services;

import com.jivaco.ebusbackend.models.User;
import com.jivaco.ebusbackend.repositories.UserRepo;
import com.jivaco.ebusbackend.schemas.users.UserDTO;
import com.jivaco.ebusbackend.schemas.users.UserReadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserManagerService implements UserDetailsService {
    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final Logger umsLogger = LoggerFactory.getLogger(UserManagerService.class);

    public UserManagerService(@Autowired UserRepo repo) {
        this.repo = repo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserReadDTO confirmAndSaveUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.email());
        user.setFullname(userDTO.fullname());
        user.setMobile(userDTO.mobile());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        User savedUser = repo.save(user);
        return new UserReadDTO(
                savedUser.getId(),
                savedUser.getFullname(),
                savedUser.getEmail(),
                savedUser.getMobile()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String emailID) throws UsernameNotFoundException {
        return this.repo.findUserByEmail(emailID);
    }
}
