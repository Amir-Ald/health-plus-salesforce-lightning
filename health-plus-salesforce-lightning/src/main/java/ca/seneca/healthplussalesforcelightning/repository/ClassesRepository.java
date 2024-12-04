package ca.seneca.healthplussalesforcelightning.repository;

import ca.seneca.healthplussalesforcelightning.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {
    List<Classes> findByStartTimeGreaterThanOrderByStartTime(LocalDateTime dateTime);
}
