package com.jivaco.ebusbackend.schemas.users;

public record UserDTO(
        String fullname,
        String email,
        long mobile,
        String password
) {
}

