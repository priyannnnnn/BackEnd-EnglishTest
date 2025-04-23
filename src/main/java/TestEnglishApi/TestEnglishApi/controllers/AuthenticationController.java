package TestEnglishApi.TestEnglishApi.controllers;

import TestEnglishApi.TestEnglishApi.dtos.LoginResponse;
import TestEnglishApi.TestEnglishApi.dtos.LoginUserDto;
import TestEnglishApi.TestEnglishApi.dtos.RegisterUserDto;
import TestEnglishApi.TestEnglishApi.entities.User;
import TestEnglishApi.TestEnglishApi.service.AuthenticationService;
import TestEnglishApi.TestEnglishApi.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://your-app-name.onrender.com"})
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        String fullname = authenticatedUser.getFullName();

        LoginResponse loginResponse = new LoginResponse()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime())
                .setFullname(authenticatedUser.getFullName())
                .setRole(authenticatedUser.getRole().name());

        return ResponseEntity.ok(loginResponse);
    }

}
