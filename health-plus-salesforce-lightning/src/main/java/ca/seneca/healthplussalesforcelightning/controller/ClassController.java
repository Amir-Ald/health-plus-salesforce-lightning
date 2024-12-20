package ca.seneca.healthplussalesforcelightning.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.seneca.healthplussalesforcelightning.model.Bookings;
import ca.seneca.healthplussalesforcelightning.model.Classes;
import ca.seneca.healthplussalesforcelightning.service.ClassService;

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
        try {
            Optional<Classes> classEntity = Optional.ofNullable(classService.getClassById(id));
            if (classEntity.isPresent()) {
                if (classEntity.get().getCapacity() <= 0) {
                    return ResponseEntity.badRequest().body("Class is full");
                }
                
                Bookings savedBooking = classService.bookClass(id, booking);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Class not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        try {
            Optional<Bookings> existingBooking = Optional.ofNullable(classService.getBookingById(bookingId));
            if (existingBooking.isPresent()) {
                Bookings updatedBooking = classService.cancelBooking(bookingId);
                return ResponseEntity.ok(updatedBooking);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Classes>> getUpcomingClasses() {
        List<Classes> upcomingClasses = classService.getUpcomingClasses();
        return ResponseEntity.ok(upcomingClasses);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Bookings>> getAllBookings() {
        List<Bookings> bookings = classService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}