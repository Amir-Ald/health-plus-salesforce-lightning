package ca.seneca.healthplussalesforcelightning.controller;

import ca.seneca.healthplussalesforcelightning.model.Users;
import ca.seneca.healthplussalesforcelightning.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping
    public ResponseEntity<List<Users>> getAllStaff() {
        List<Users> staff = staffService.getAllStaff();
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/instructors")
    public ResponseEntity<List<Users>> getAllInstructors() {
        List<Users> instructors = staffService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @PostMapping
    public ResponseEntity<Users> addStaff(@RequestBody Users staff) {
        Users savedStaff = staffService.addStaff(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStaff);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody Users staff) {
        Optional<Users> existingStaff = Optional.ofNullable(staffService.getStaffById(id));
        if (existingStaff.isPresent()) {
            Users updatedStaff = staffService.updateStaff(id, staff);
            return ResponseEntity.ok(updatedStaff);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Staff member not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        Optional<Users> existingStaff = Optional.ofNullable(staffService.getStaffById(id));
        if (existingStaff.isPresent()) {
            staffService.deleteStaff(id);
            return ResponseEntity.ok().body("Staff member deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Staff member not found");
        }
    }
}