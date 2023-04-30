package com.jivaco.ebusbackend.repositories;

import com.jivaco.ebusbackend.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findUserByEmail(String email);
}
