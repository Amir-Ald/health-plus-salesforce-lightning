package ca.seneca.healthplussalesforcelightning.controller;

import ca.seneca.healthplussalesforcelightning.model.Bookings;
import ca.seneca.healthplussalesforcelightning.model.Classes;
import ca.seneca.healthplussalesforcelightning.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes")
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<List<Classes>> getAllClasses() {
        List<Classes> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClassById(@PathVariable Long id) {
        Optional<Classes> classEntity = Optional.ofNullable(classService.getClassById(id));
        if (classEntity.isPresent()) {
            return ResponseEntity.ok(classEntity.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Class not found");
        }
    }

    @PostMapping
    public ResponseEntity<Classes> addClass(@RequestBody Classes classEntity) {
        Classes savedClass = classService.addClass(classEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClass);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClass(@PathVariable Long id, @RequestBody Classes classEntity) {
        Optional<Classes> existingClass = Optional.ofNullable(classService.getClassById(id));
        if (existingClass.isPresent()) {
            Classes updatedClass = classService.updateClass(id, classEntity);
            return ResponseEntity.ok(updatedClass);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Class not found");
        }
    }

    @PostMapping("/{id}/bookings")
    public ResponseEntity<?> bookClass(@PathVariable Long id, @RequestBody Bookings booking) {
        Optional<Classes> classEntity = Optional.ofNullable(classService.getClassById(id));
        if (classEntity.isPresent()) {
            Bookings savedBooking = classService.bookClass(id, booking);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Class not found");
        }
    }

    @DeleteMapping("/{id}/bookings/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        Optional<Bookings> existingBooking = Optional.ofNullable(classService.getBookingById(bookingId));
        if (existingBooking.isPresent()) {
            classService.cancelBooking(bookingId);
            return ResponseEntity.ok().body("Booking cancelled successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
    }
}