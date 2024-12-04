package ca.seneca.healthplussalesforcelightning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Bookings bookClass(Long classId, Bookings booking) {
        booking.setClassEntity(classesRepository.findById(classId).orElse(null));
        return bookingsRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        bookingsRepository.deleteById(bookingId);
    }

    public Bookings getBookingById(Long bookingId) {
        return bookingsRepository.findById(bookingId).orElse(null);
    }

    public List<Classes> getUpcomingClasses() {
        LocalDateTime now = LocalDateTime.now();
        return classesRepository.findByStartTimeGreaterThanOrderByStartTime(now);
    }
}