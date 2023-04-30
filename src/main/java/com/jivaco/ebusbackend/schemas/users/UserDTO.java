package com.jivaco.ebusbackend.schemas.users;

public record UserDTO(
        Long id,
        String fullname,
        String email,
        Long mobile,
        String password
) {
}

