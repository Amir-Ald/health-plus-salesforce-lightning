package ca.seneca.healthplussalesforcelightning.service;

import ca.seneca.healthplussalesforcelightning.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.seneca.healthplussalesforcelightning.model.Users;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UsersRepository usersRepository;

    public Optional<Users> login(String email, String password) {
        Optional<Users> userOptional = usersRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if (password.equals(user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<List<String>> getRoles(Long userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            return Optional.of(List.of(user.getRole().name()));
        }
        return Optional.empty();
    }
}