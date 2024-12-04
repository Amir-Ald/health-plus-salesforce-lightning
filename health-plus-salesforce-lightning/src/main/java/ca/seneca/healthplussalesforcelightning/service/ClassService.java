package ca.seneca.healthplussalesforcelightning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.seneca.healthplussalesforcelightning.model.Bookings;
import ca.seneca.healthplussalesforcelightning.model.Classes;
import ca.seneca.healthplussalesforcelightning.repository.BookingsRepository;
import ca.seneca.healthplussalesforcelightning.repository.ClassesRepository;

@Service
public class ClassService {
    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private BookingsRepository bookingsRepository;

    public List<Classes> getAllClasses() {
        return classesRepository.findAll();
    }

    public Classes getClassById(Long id) {
        return classesRepository.findById(id).orElse(null);
    }

    public Classes addClass(Classes classEntity) {
        return classesRepository.save(classEntity);
    }

    public Classes updateClass(Long id, Classes classEntity) {
        classEntity.setId(id);
        return classesRepository.save(classEntity);
    }

    public void deleteClass(Long id) {
        classesRepository.deleteById(id);
    }

    @Transactional
    public Bookings bookClass(Long classId, Bookings booking) {
        Classes classEntity = classesRepository.findById(classId)
            .orElseThrow(() -> new RuntimeException("Class not found"));

        // Check if class is full
        if (classEntity.getCapacity() <= 0) {
            throw new RuntimeException("Class is full");
        }

        // Decrease capacity by 1
        classEntity.setCapacity(classEntity.getCapacity() - 1);
        classesRepository.save(classEntity);

        // Set the class reference in the booking
        booking.setClassEntity(classEntity);
        
        // Save and return the booking
        return bookingsRepository.save(booking);
    }

    @Transactional
    public Bookings cancelBooking(Long bookingId) {
        Bookings booking = bookingsRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Increase class capacity by 1 when cancelling
        Classes classEntity = booking.getClassEntity();
        classEntity.setCapacity(classEntity.getCapacity() + 1);
        classesRepository.save(classEntity);

        // Update booking status
        booking.setStatus("CANCELLED");
        return bookingsRepository.save(booking);
    }

    public Bookings getBookingById(Long bookingId) {
        return bookingsRepository.findById(bookingId).orElse(null);
    }

    public List<Classes> getUpcomingClasses() {
        LocalDateTime now = LocalDateTime.now();
        return classesRepository.findByStartTimeGreaterThanOrderByStartTime(now);
    }

    public List<Bookings> getAllBookings() {
        return bookingsRepository.findAll();
    }
}