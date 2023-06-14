package ua.lviv.iot.course_work.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.course_work.authentication.AuthenticationRequest;
import ua.lviv.iot.course_work.authentication.AuthenticationResponse;
import ua.lviv.iot.course_work.authentication.AuthenticationService;
import ua.lviv.iot.course_work.authentication.RegisterRequest;
import ua.lviv.iot.course_work.entities.UserEntity;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest registerRequest
    ){

        try {
            return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        try {
            return new ResponseEntity<>(authenticationService.authenticate(authenticationRequest), HttpStatus.OK);

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
