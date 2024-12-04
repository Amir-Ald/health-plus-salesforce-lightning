package ca.seneca.healthplussalesforcelightning.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.seneca.healthplussalesforcelightning.dto.LoginResponse;
import ca.seneca.healthplussalesforcelightning.model.Users;
import ca.seneca.healthplussalesforcelightning.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<Users> userOptional = authService.login(email, password);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            String primaryRole = user.getRole().name();
            
            // Generate a simple token
            String token = UUID.randomUUID().toString();
            
            LoginResponse response = new LoginResponse(
                token,
                primaryRole
            );
            
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(@RequestParam Long userId) {
        Optional<List<String>> roles = authService.getRoles(userId);
        if (roles.isPresent()) {
            return ResponseEntity.ok(roles.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}