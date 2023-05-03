package com.jivaco.ebusbackend.controllers;

import com.jivaco.ebusbackend.schemas.users.AuthenticationResponse;
import com.jivaco.ebusbackend.schemas.users.LoginUser;
import com.jivaco.ebusbackend.schemas.users.UserDTO;
import com.jivaco.ebusbackend.schemas.users.UserReadDTO;
import com.jivaco.ebusbackend.services.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
public class UserController {
    private final UserManagerService service;

    public UserController(@Autowired UserManagerService service) {
        this.service = service;
    }

    @PostMapping(path = "/signup")
    public UserReadDTO saveUser(@RequestBody UserDTO userDTO) {
        return service.confirmAndSaveUser(userDTO);
    }

    @PostMapping(path = "/authenticate")
    public AuthenticationResponse authenticate(@RequestBody LoginUser loginUser) {
        String status = service.verifyUserCredentials(loginUser.username(), loginUser.password());
        return new AuthenticationResponse(status);
    }
}

@ControllerAdvice
class DuplicateUserExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                "Duplicate user!",
                new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY,
                request
        );
    }
}
