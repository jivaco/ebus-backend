package com.jivaco.ebusbackend.schemas.users;

public record UserReadDTO(
        Long id,
        String fullname,
        String email,
        Long mobile
) { }
