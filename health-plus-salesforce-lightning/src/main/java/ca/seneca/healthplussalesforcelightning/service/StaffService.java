package ca.seneca.healthplussalesforcelightning.service;

import ca.seneca.healthplussalesforcelightning.model.Users;
import ca.seneca.healthplussalesforcelightning.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getAllStaff() {
        return usersRepository.findAll();
    }

    public Users getStaffById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public Users addStaff(Users staff) {
        return usersRepository.save(staff);
    }

    public Users updateStaff(Long id, Users staff) {
        staff.setId(id);
        return usersRepository.save(staff);
    }

    public void deleteStaff(Long id) {
        usersRepository.deleteById(id);
    }

    public List<Users> getAllInstructors() {
        return usersRepository.findByRoleIgnoreCase("INSTRUCTOR");
    }
}
