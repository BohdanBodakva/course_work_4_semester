package ua.lviv.iot.course_work.authentication;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.lviv.iot.course_work.entities.UserEntity;
import ua.lviv.iot.course_work.entities.enums.UserRole;
import ua.lviv.iot.course_work.entities.enums.UserStatus;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;
import ua.lviv.iot.course_work.jwt.JwtService;
import ua.lviv.iot.course_work.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws UserNotFoundException {
        try {
            userRepository.findUserEntityByUsernameAndStatusIsActive(request.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("User with username = " + request.getUsername() + " already exists"));
        } catch (UserNotFoundException e) {
            UserEntity user = new UserEntity();

            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(UserRole.USER);
            user.setStatus(UserStatus.ACTIVE);

            userRepository.save(user);

            String jwtToken = jwtService.generateToken(user);
            return new AuthenticationResponse(jwtToken);
        }

        throw new UserNotFoundException("User with username = " + request.getUsername() + " already exists");

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundException {
        UserEntity user = userRepository.findUserEntityByUsernameAndStatusIsActive(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User with username = " + request.getUsername() + " doesn't exist"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(user);

        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(u);

        return new AuthenticationResponse(jwtToken);
    }

}
