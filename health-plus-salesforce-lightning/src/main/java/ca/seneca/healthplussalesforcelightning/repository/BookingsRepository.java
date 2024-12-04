package ca.seneca.healthplussalesforcelightning.repository;

import ca.seneca.healthplussalesforcelightning.model.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long> {
}
